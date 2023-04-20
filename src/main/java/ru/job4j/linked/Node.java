package main.java.ru.job4j.linked;

public final class Node<T> {
    private final Node<T> next;
    private final T value;

    public Node(final Node<T> next, final T value) {
        this.next = next;
        this.value = value;
    }

    public Node<T> getNext() {
        return this.next;
    }

    public T getValue() {
        return this.value;
    }
}
