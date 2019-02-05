public class ArrayDeque<T> {

    private T[] deque;
    private int size, first, last;

    public ArrayDeque() {
        deque = (T[]) new Object[8];
        first = 0; last = 7; size = 0;
    }
    
    public ArrayDeque(ArrayDeque other) {
        deque = (T[]) new Object[other.size()];
        for (int index = 0; index < other.size(); index++) {
            deque[index] = (T) other.get(index);
        }
        first = 0;
        last = other.size() - 1;
        size = other.size();
    }

    private void resize() {
        int newsize = size * 2;
        T[] newdeque = (T[]) new Object[newsize];

        int newindex = 0;
        for (int i = first; i != last; i = move(i + 1)) {
            newdeque[newindex] = deque[i];
            newindex++;
        }
        newdeque[newindex] = deque[last];

        this.first = 0;
        this.last = newindex;
        this.deque = newdeque;
    }

    private int move(int index) {
        return Math.floorMod(index, deque.length);
    }

    public void addFirst(T item) {
        size += 1;
        if (size == deque.length) {
            resize();
        }
        first = move(first - 1);
        deque[first] = item;
    }

    public void addLast(T item) {
        size += 1;
        if (size == deque.length) {
            resize();
        }
        last = move(last + 1);
        deque[last] = item;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        for (int i = first; i != last; i = move(i + 1)) {
            System.out.print(deque[i] + " ");
        }
        System.out.print(deque[last]);
    }

    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        size -= 1;
        if ((size / deque.length) < 0.25) {
            resize();
        }

        T item = deque[first];
        deque[first] = null;
        first = move(first + 1);
        return item;
    }

    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        size -= 1;
        if ((size / deque.length) < 0.25) {
            resize();
        }
        T item = deque[last];
        deque[last] = null;
        last = move(last - 1);
        return item;
    }

    public T get(int index) {
        if (this.isEmpty() || index < 0 || index > deque.length - 1) {
            return null;
        }
        return deque[move(first + index)];
    }
}
