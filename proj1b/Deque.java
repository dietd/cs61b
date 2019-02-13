public interface Deque<T> {
    void addFirst(T item);
    void addLast(T item);
    T removeFirst();
    T removeLast();
    void printDeque();
    int size();
    boolean isEmpty();
    T get(int index);
}
