package ru.job4j;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class SimpleBlockingQueueTest {

    @Test
    void whenBlockSizeEqualToOneAndAddFourElementsThenNotNull() throws InterruptedException {
        SimpleBlockingQueue<Integer> sbq = new SimpleBlockingQueue<>(1);
        List<Integer> list = new ArrayList<>();
        Thread producer = new Thread(
                () -> {
                    for (int i = 0; i < 4; i++) {
                        sbq.offer(i);
                    }
                },
                "Producer"
        );
        Thread consumer = new Thread(
                () -> {
                    while (!Thread.currentThread().isInterrupted()) {
                        list.add(sbq.poll());
                    }
                },
                "Consumer"
        );
        producer.start();
        consumer.start();
        producer.join();
        consumer.interrupt();
        assertThat(list).hasSize(4)
                .isNotNull()
                .isEqualTo(List.of(0, 1, 2, 3));
    }
}