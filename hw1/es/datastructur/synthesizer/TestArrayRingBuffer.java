package es.datastructur.synthesizer;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer<Double> arb = new ArrayRingBuffer(4);
        assertTrue(arb.isEmpty());       //                       (returns true)
        arb.enqueue(9.3);    // 9.3
        arb.enqueue(15.1);   // 9.3  15.1
        arb.enqueue(31.2);  // 9.3  15.1  31.2
        assertFalse(arb.isFull());        // 9.3  15.1  31.2       (returns false)
        arb.enqueue(-3.1);   // 9.3  15.1  31.2  -3.1
        assertTrue(arb.isFull());        // 9.3  15.1  31.2  -3.1 (returns true)
        assertEquals(9.3, arb.dequeue(), 0.0001);       // 15.1 31.2  -3.1       (returns 9.3)
        assertEquals(15.1, arb.peek(), 0.0001);         // 15.1 31.2  -3.1       (returns 15.1)

        ArrayRingBuffer<Integer> a = new ArrayRingBuffer<>(4);
        ArrayRingBuffer<Integer> b = new ArrayRingBuffer<>(4);

        a.enqueue(1);
        a.enqueue(2);
        a.enqueue(3);
        a.enqueue(4);

        b.enqueue(1);
        b.enqueue(2);
        b.enqueue(3);
        b.enqueue(4);

        assertTrue(a.equals(b));

        a.dequeue();

        assertFalse(a.equals(b));

        assertFalse(a.equals(new GuitarString(440)));
    }

    @Test
    public void anotherTest() {
        ArrayRingBuffer<Integer> a = new ArrayRingBuffer<>(4);
        a.enqueue(1);
        a.enqueue(2);
        a.enqueue(3);
        a.enqueue(4);

        Iterator<Integer> aiter = a.iterator();
        assertEquals(1, aiter.next(), 0.0001);
        assertEquals(2, aiter.next(), 0.0001);
        assertEquals(3, aiter.next(), 0.0001);
        assertEquals(4, aiter.next(), 0.0001);
    }
}
