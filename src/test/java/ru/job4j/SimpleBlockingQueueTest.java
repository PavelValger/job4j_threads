package ru.job4j;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.assertj.core.api.Assertions.*;

class SimpleBlockingQueueTest {

    @Test
    void whenBlockSizeEqualToOneAndAddFourElementsThenNotNull() throws InterruptedException {
        SimpleBlockingQueue<Integer> sbq = new SimpleBlockingQueue<>(1);
        CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        Thread producer = new Thread(
                () -> {
                    for (int i = 0; i < 4; i++) {
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
                    while (!sbq.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(sbq.poll());
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
        consumer.join();
        assertThat(buffer).isNotNull()
                .isEqualTo(List.of(0, 1, 2, 3));
    }
}