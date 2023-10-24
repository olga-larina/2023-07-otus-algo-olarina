package ru.otus.graph.dfs;

import ru.otus.common.Queue;
import ru.otus.common.Stack;
import ru.otus.graph.Graph;

/**
 * Поиск в глубину с сохранением порядка обработки вершин и компонента сильной связности (корректно только при правильном обходе вершин!)
 * Рекурсивный и итеративный вариант (в зависимости от переданного в конструкторе параметра)
 */
public class DepthFirstSearch {

    /**
     * вершины в порядке выхода из DFS
     */
    private final Queue<Integer> postorder;

    /**
     * компоненты сильной связности (корректно только при правильном обходе вершин!)
     */
    private final int[] components;

    public DepthFirstSearch(Graph graph) {
        this(graph, false);
    }

    public DepthFirstSearch(Graph graph, boolean recursive) {
        this(graph, recursive, graph.vertices());
    }

    public DepthFirstSearch(Graph graph, Iterable<Integer> verticesOrder) {
        this(graph, false, verticesOrder);
    }

    public DepthFirstSearch(Graph graph, boolean recursive, Iterable<Integer> verticesOrder) {
        this.postorder = new Queue<>();
        this.components = new int[graph.verticesCount()];
        if (recursive) {
            dfsRecursive(graph, verticesOrder);
        } else {
            dfsIterative(graph, verticesOrder);
        }
    }

    /**
     * вершины в порядке, обратном порядку выхода
     */
    public Iterable<Integer> reversePostorder() {
        Stack<Integer> reverse = new Stack<>();
        for (int v : postorder) {
            reverse.push(v);
        }
        return reverse;
    }

    /**
     * компоненты сильной связности (корректно только при правильном обходе вершин!)
     */
    public int[] components() {
        return components;
    }

    /**
     * Поиск в глубину, рекурсивный вариант
     */
    private void dfsRecursive(Graph graph, Iterable<Integer> verticesOrder) {
        boolean[] visited = new boolean[graph.verticesCount()];
        int curComponent = 0;
        for (int v : verticesOrder) {
            if (!visited[v]) { // пропускаем посещённые
                dfs(graph, v, visited, curComponent);
                curComponent++; // компонент меняется
            }
        }
    }

    /**
     * Поиск в глубину, итеративный вариант
     */
    private void dfsIterative(Graph graph, Iterable<Integer> verticesOrder) {
        boolean[] visited = new boolean[graph.verticesCount()];
        Stack<Integer> stack = new Stack<>();
        int curComponent = 0;
        for (int v : verticesOrder) {
            if (!visited[v]) { // пропускаем посещённые
                dfs(graph, v, visited, curComponent, stack);
                curComponent++; // компонент меняется
            }
        }
    }

    /**
     * Поиск в глубину, рекурсивный вариант
     */
    private void dfs(Graph graph, int v, boolean[] visited, int curComponent) {
        if (visited[v]) { // уже посетили или в процессе => пропускаем
            return;
        }
        // посещена
        visited[v] = true;
        // номер компонента
        components[v] = curComponent;
        for (int w : graph.adj(v)) {
            // для непосещённых запускаем рекурсию, компонент тот же (т.к. связаны)
            if (!visited[w]) {
                dfs(graph, w, visited, curComponent);
            }
        }
        // пост-порядок
        postorder.enqueue(v);
    }

    /**
     * Поиск в глубину, итеративный вариант
     */
    private void dfs(Graph graph, int vertex, boolean[] visited, int curComponent, Stack<Integer> stack) {
        stack.push(vertex);
        visited[vertex] = true;
        Stack<Integer> post = new Stack<>();
        while (!stack.isEmpty()) {
            int v = stack.pop();
            // компонент
            components[v] = curComponent;
            for (int w : graph.adj(v)) {
                if (visited[w]) { // обработана или в процессе обработки (в стеке) => пропускаем
                    continue;
                }
                stack.push(w);
                visited[w] = true;
            }
            // пост-порядок; кладём в стек, т.к. иначе получим preorder вместо postorder
            post.push(v);
        }
        // добавляем в очередь из стека в нужном порядке
        for (int v : post) {
            postorder.enqueue(v);
        }
    }
}
