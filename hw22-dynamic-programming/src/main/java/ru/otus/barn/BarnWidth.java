package ru.otus.barn;

import ru.otus.common.Stack;

/**
 * Предрасчёт ширины сарая (с учётом рассчитанной высоты)
 */
public class BarnWidth {

    /**
     * стек (монотонный) для расчётов ширины
     */
    private final Stack<StackItem> stack;

    /**
     * предрассчитанные высоты
     */
    private final BarnHeight barnHeight;

    /**
     * левые границы
     */
    private final int[] lefts;

    /**
     * правые границы
     */
    private final int[] rights;

    public BarnWidth(BarnHeight barnHeight, int size) {
        this.stack = new Stack<>();
        this.barnHeight = barnHeight;
        this.lefts = new int[size];
        this.rights = new int[size];
    }

    /**
     * Расчёт ширин для текущей строки (с учётом того, что данные в barnHeight уже обновлены для текущей строки).
     */
    public void fillWidths() {
        fillLefts();
        fillRights();
    }

    /**
     * Получить ширину для последней обработанной строки и столбца c
     * @param c текущий столбец
     * @return ширина
     */
    public int width(int c) {
        return rights[c] - lefts[c] + 1;
    }

    /**
     * Заполнение левых границ
     */
    private void fillLefts() {
        for (int i = lefts.length - 1; i >= 0; i--) { // справа налево
            // если высота элемента в стеке больше текущей, то убираем элемент из стека, ограничиваем его предыдущим элементом
            while (!stack.isEmpty() && stack.peek().height > barnHeight.height(i)) {
                lefts[stack.pop().c] = i + 1;
            }
            stack.push(new StackItem(i, barnHeight.height(i))); // кладём текущий элемент в стек
        }
        while (!stack.isEmpty()) { // оставшимся проставляем крайнюю левую границы
            lefts[stack.pop().c] = 0;
        }
    }

    /**
     * Заполнение правых границ
     */
    private void fillRights() {
        for (int i = 0; i < rights.length; i++) { // слева направо
            // если высота элемента в стеке больше текущей, то убираем элемент из стека, ограничиваем его предыдущим элементом
            while (!stack.isEmpty() && stack.peek().height > barnHeight.height(i)) {
                rights[stack.pop().c] = i - 1;
            }
            stack.push(new StackItem(i, barnHeight.height(i))); // кладём текущий элемент в стек
        }
        while (!stack.isEmpty()) { // оставшимся проставляем крайнюю правую границу
            rights[stack.pop().c] = rights.length - 1;
        }
    }

    /**
     * Для хранения данных в стеке
     * @param c         номер столбца
     * @param height    высота
     */
    private record StackItem(int c, int height) {
    }
}
