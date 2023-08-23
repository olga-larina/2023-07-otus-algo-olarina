package ru.otus.queue;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PriorityQueueImplTest {

    private final PriorityQueueImpl<Integer> queue = new PriorityQueueImpl<>();

    @Test
    void shouldProcessElements() {
        List<Value> expected = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            int priority = random.nextInt(0, 30);
            int val = random.nextInt(-100000, 100000);
            expected.add(new Value(priority, val));
            queue.enqueue(priority, val);
        }
        expected.sort(Comparator.comparingInt(o -> o.priority));

        for (int i = 0; i < 10000; i++) {
            assertEquals(expected.get(i).val, queue.dequeue());
        }
    }

    private static class Value {
        int priority;
        int val;

        Value(int priority, int val) {
            this.priority = priority;
            this.val = val;
        }
    }
}