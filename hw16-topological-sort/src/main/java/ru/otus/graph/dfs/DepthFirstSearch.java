package ru.otus.graph.dfs;

import ru.otus.common.Queue;
import ru.otus.common.Stack;
import ru.otus.graph.Graph;

/**
 * Поиск в глубину с сохранением порядка обработки вершин, определением цикла
 */
public class DepthFirstSearch {

    /**
     * вершины в порядке выхода из DFS
     */
    private final Queue<Integer> postorder;

    /**
     * есть ли цикл в графе
     */
    private boolean cycle;

    public DepthFirstSearch(Graph graph) {
        this(graph, graph.vertices());
    }

    public DepthFirstSearch(Graph graph, Iterable<Integer> verticesOrder) {
        this.postorder = new Queue<>();
        dfsRecursive(graph, verticesOrder);
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
     * есть ли цикл в графе
     */
    public boolean isCycle() {
        return cycle;
    }

    /**
     * Поиск в глубину, рекурсивный вариант
     */
    private void dfsRecursive(Graph graph, Iterable<Integer> verticesOrder) {
        int[] visited = new int[graph.verticesCount()];
        for (int v : verticesOrder) {
            if (visited[v] != 2) {
                dfs(graph, v, visited);
            }
        }
    }

    /**
     * Поиск в глубину, рекурсивный вариант
     */
    private void dfs(Graph graph, int v, int[] visited) {
        if (visited[v] == 1) { // в процессе обработки => есть цикл => выходим
            cycle = true;
            return;
        }
        if (visited[v] == 2) { // уже посетили => пропускаем
            return;
        }
        // в процессе посещения
        visited[v] = 1;
        for (int w : graph.adj(v)) {
            // в процессе обработки и посещённые будут отсеяны в начале рекурсии
            dfs(graph, w, visited);
        }
        // посетили
        visited[v] = 2;
        // пост-порядок
        postorder.enqueue(v);
    }

}
