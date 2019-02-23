package es.datastructur.synthesizer;
import java.util.Iterator;
import java.util.NoSuchElementException;

//TODO: Make sure to that this class and all of its methods are public
//TODO: Make sure to add the override tag for all overridden methods
//TODO: Make sure to make this class implement BoundedQueue<T>

public class ArrayRingBuffer<T> implements BoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;
    /* Index for the next enqueue. */
    private int last;
    /* Variable for the fillCount. */
    private int fillCount;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        rb = (T[]) new Object[capacity];
        first = 0;
        last = 0;
        fillCount = 0;

        // TODO: Create new array with capacity elements.
        //       first, last, and fillCount should all be set to 0.
    }

    @Override
    public int capacity() {
        return rb.length;
    }

    @Override
    public int fillCount() {
        return fillCount;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow").
     */
    public void enqueue(T x) {
        // TODO: Enqueue the item. Don't forget to increase fillCount and update
        //       last.
        if (isFull()) {
            throw new RuntimeException("Ring buffer overflow");
        }
        rb[last] = x;
        last = Math.floorMod(last + 1, capacity());
        fillCount += 1;
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    public T dequeue() {
        // TODO: Dequeue the first item. Don't forget to decrease fillCount and
        //       update first.
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        }

        T item = rb[first];
        rb[first] = null;
        first = Math.floorMod(first + 1, capacity());
        fillCount -= 1;
        return item;
    }

    /**
     * Return oldest item, but don't remove it. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    public T peek() {
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        }
        return rb[first];
    }

    private class arbIterator<T> implements Iterator<T> {
        private T[] objects;
        private int index;
        public arbIterator(ArrayRingBuffer arb) {
            objects = (T[]) new Object[arb.fillCount()];
            for (int i = 0; i < objects.length; i += 1) {
                objects[i] = (T) arb.dequeue();
                arb.enqueue(objects[i]);
            }
            index = 0;
        }

        @Override
        public boolean hasNext() {
            return !(index >= objects.length);
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            index += 1;
            return objects[index - 1];
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new arbIterator(this);
    }

    @Override
    public boolean equals(Object o){
        if (!o.getClass().equals(this.getClass())) {
            return false;
        }
        if (((ArrayRingBuffer) o).capacity() != this.capacity()) {
            return false;
        }
        for (int i = 0; i < capacity(); i += 1) {
            T a = dequeue();
            Object b = ((ArrayRingBuffer) o).dequeue();
            if (!a.equals(b)) {
                return false;
            }
            this.enqueue(a);
            ((ArrayRingBuffer) o).enqueue(b);
        }
        return true;
    }
}
