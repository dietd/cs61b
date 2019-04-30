package byow.proj3.ai;

import java.util.HashMap;
import java.util.NoSuchElementException;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {

    private T[] heap;
    private HashMap<T, Double> pmap;
    private HashMap<T, Integer> imap;
    private int size;

    public ArrayHeapMinPQ() {
        heap =  (T[]) new Object[16];
        pmap = new HashMap<>();
        imap = new HashMap<>();
        size = 0;
    }

    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException();
        }
        resize();
        heap[size] = item;
        pmap.put(item, priority);
        imap.put(item, size);
        swim(size);
        size += 1;
    }

    public boolean contains(T item) {
        return pmap.containsKey(item);
    }

    public T getSmallest() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        return heap[0];
    }

    public T removeSmallest() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        T item = heap[0];
        heap[0] = null;
        pmap.remove(item);
        imap.remove(item);
        swap(size - 1, 0);
        size -= 1;
        sink(0);
        resize();
        return item;
    }

    public int size() {
        return size;
    }

    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException();
        }
        pmap.put(item, priority);
        swim(index(item));
        sink(index(item));
    }

    /** UTILITIES */

    private void resize() {
        if (size == heap.length || size < heap.length / 4 && heap.length > 16) {
            T[] newHeap = (T[]) new Object[size * 2];
            System.arraycopy(heap, 0, newHeap, 0, size);
            heap = newHeap;
        }
    }

    private int parent(int key) {
        if (key == 0) {
            return 0;
        }
        return (key - 1) / 2;
    }

    private int left(int key) {
        return key * 2 + 1;
    }

    private int right(int key) {
        return key * 2 + 2;
    }

    private double priority(int i) {
        return pmap.get(heap[i]);
    }

    private int index(T item) {
        return imap.get(item);
    }

    private void swap(int j, int k) {
        imap.put(heap[j], k);
        imap.put(heap[k], j);
        T temp = heap[j];
        heap[j] = heap[k];
        heap[k] = temp;
    }

    private void swim(int i) {
        if (priority(i) < priority(parent(i))) {
            swap(i, parent(i));
            swim(parent(i));
        }
    }

    private void sink(int i) {
        if (right(i) < size) {
            if (priority(left(i)) < priority(i)
                    && priority(right(i)) < priority(i)) {
                if (priority(left(i)) < priority(right(i))) {
                    sinkLeft(i);
                } else {
                    sinkRight(i);
                }
            } else if (priority(left(i)) < priority(i)) {
                sinkLeft(i);
            } else if (priority(right(i)) < priority(i)) {
                sinkRight(i);
            }
        } else if (left(i) < size) {
            if (priority(left(i)) < priority(i)) {
                sinkLeft(i);
            }
        }
    }

    private void sinkLeft(int i) {
        swap(i, left(i));
        sink(left(i));
    }

    private void sinkRight(int i) {
        swap(i, right(i));
        sink(right(i));
    }
}
