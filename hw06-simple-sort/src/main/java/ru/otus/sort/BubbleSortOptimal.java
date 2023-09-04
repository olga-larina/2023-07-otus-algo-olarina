package ru.otus.sort;

/**
 * Сортировка пузырьком (оптимизированная).
 * Оптимизация - если нет ни одного присваивания, то завершаем работу
 */
public class BubbleSortOptimal extends SortIntAlgo {

    public BubbleSortOptimal(int[] arr) {
        super(arr);
    }

    @Override
    protected void sort0() throws InterruptedException {
        boolean changed;
        for (int j = n - 1; j > 0; j--) {
            changed = false;
            for (int i = 0; i < j; i++) {
                if (more(arr[i], arr[i + 1])) {
                    swap(i, i + 1);
                    changed = true;
                }

                if (Thread.currentThread().isInterrupted()) {
                    throw new InterruptedException("Too long execution");
                }
            }
            if (!changed) {
                break;
            }
        }
    }
}
