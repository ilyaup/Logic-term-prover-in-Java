package utils;

public class Pair <T, E> {
    private final T key;
    private final E value;

    public Pair (T key, E value) {
        this.key = key;
        this.value = value;
    }

    public T getKey() {
        return key;
    }

    public E getValue() {
        return value;
    }
}
