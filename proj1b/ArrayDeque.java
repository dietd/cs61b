public class ArrayDeque<T> implements Deque<T> {

    private T[] deque;
    private int size, first, last;

    public ArrayDeque() {
        deque = (T[]) new Object[8];
        first = 0;
        last = 7;
        size = 0;
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

    private void resize(int newsize) {
        T[] newdeque = (T[]) new Object[newsize];

        if (first > last) {
            System.arraycopy(deque, first, newdeque, 0, deque.length - first);
            System.arraycopy(deque, 0, newdeque, deque.length - first, last + 1);
            last = deque.length - first + last;
        } else {
            System.arraycopy(deque, first, newdeque, 0, last - first + 1);
            last = last - first;
        }

        first = 0;
        deque = newdeque;
    }

    private int move(int index) {
        return Math.floorMod(index, deque.length);
    }

    @Override
    public void addFirst(T item) {
        size += 1;
        if (size == deque.length) {
            resize(size * 2);
        }
        first = move(first - 1);
        deque[first] = item;
    }

    @Override
    public void addLast(T item) {
        size += 1;
        if (size == deque.length) {
            resize(size * 2);
        }
        last = move(last + 1);
        deque[last] = item;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        for (int i = first; i != last; i = move(i + 1)) {
            System.out.print(deque[i] + " ");
        }
        System.out.print(deque[last]);
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        size -= 1;

        T item = deque[first];
        deque[first] = null;
        first = move(first + 1);
        if (size  < deque.length / 4  && deque.length >= 16) {
            resize(size + 1);
        }
        return item;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        size -= 1;
        T item = deque[last];
        deque[last] = null;
        last = move(last - 1);

        if (size < deque.length / 4 && deque.length >= 16) {
            resize(size * 2);
        }
        return item;
    }

    @Override
    public T get(int index) {
        if (this.isEmpty() || index < 0 || index > deque.length - 1) {
            return null;
        }
        return deque[move(first + index)];
    }
}
