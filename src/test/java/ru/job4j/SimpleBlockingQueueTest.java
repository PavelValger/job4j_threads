package ru.job4j;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class SimpleBlockingQueueTest {

    @Test
    void whenBlockSizeEqualToOneAndAddFourElementsThenNotNull() throws InterruptedException {
        int number = 4;
        SimpleBlockingQueue<Integer> sbq = new SimpleBlockingQueue<>(1);
        List<Integer> list = new ArrayList<>();
        Thread producer = new Thread(
                () -> {
                    for (int i = 0; i < number; i++) {
                        try {
                            sbq.offer(i);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                "Producer"
        );
        Thread consumer = new Thread(
                () -> {
                    while (list.size() != number || !Thread.currentThread().isInterrupted()) {
                        try {
                            list.add(sbq.poll());
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                },
                "Consumer"
        );
        producer.start();
        consumer.start();
        producer.join();
        consumer.interrupt();
        assertThat(list).isNotNull()
                .isEqualTo(List.of(0, 1, 2, 3));
    }
}