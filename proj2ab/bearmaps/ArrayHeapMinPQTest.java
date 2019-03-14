package bearmaps;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class ArrayHeapMinPQTest {

    @Test
    public void testAdd() {
        NaiveMinPQ<String> nm = new NaiveMinPQ<>();
        ArrayHeapMinPQ<String> ahm = new ArrayHeapMinPQ<>();

        nm.add("t", 10);
        ahm.add("t", 10);

        nm.add("e", 10);
        ahm.add("e", 10);

        assertEquals(nm.getSmallest(), ahm.getSmallest());

        nm.add("s", 0.01);
        ahm.add("s", 0.01);

        assertEquals(nm.getSmallest(), ahm.getSmallest());
    }

    @Test
    public void testRemove() {

        NaiveMinPQ<String> nm = new NaiveMinPQ<>();
        ArrayHeapMinPQ<String> ahm = new ArrayHeapMinPQ<>();

        Set<String> numSet = new HashSet<>();

        for (int i = 0; i < 1000; i += 1) {
            double weight = Math.random() * 1000;
            String item = Integer.toString((int) Math.floor(Math.random() * 1000));

            if (!numSet.contains(item)) {
                nm.add(item, weight);
                ahm.add(item, weight);
                numSet.add(item);
            }
        }

        for (int j = 0; j < ahm.size(); j += 1) {
            assertEquals(nm.removeSmallest(), ahm.removeSmallest());
        }
    }

    @Test
    public void testChangePriority() {
        NaiveMinPQ<String> nm = new NaiveMinPQ<>();
        ArrayHeapMinPQ<String> ahm = new ArrayHeapMinPQ<>();

        nm.add("Rem", 1);
        ahm.add("Rem", 1);
        nm.add("Emilia", 10);
        ahm.add("Emilia", 10);

        assertEquals(nm.getSmallest(), ahm.getSmallest());
        nm.changePriority("Rem", 100);
        ahm.changePriority("Rem", 100);

        assertEquals(nm.getSmallest(), ahm.getSmallest());
    }

    @Test
    public void randomTest() {
        NaiveMinPQ<String> nm = new NaiveMinPQ<>();
        ArrayHeapMinPQ<String> ahm = new ArrayHeapMinPQ<>();
        int iterations = 1000000;
        for (int i = 0; i < iterations; i += 1) {
            int act = (int) Math.floor(Math.random() * 2);
            double p = Math.random() * 1000;
            String item = Integer.toString((int) Math.floor(Math.random() * 100));
            if (act == 0 && !nm.contains(item)) {
                nm.add(item, p);
                ahm.add(item, p);
                assertEquals(nm.getSmallest(), ahm.getSmallest());
            } else if (act == 0 && nm.contains(item)) {
                nm.changePriority(item, p);
                ahm.changePriority(item, p);
                assertEquals(nm.getSmallest(), ahm.getSmallest());
            } else if (act == 1 && nm.size() > 0) {
                assertEquals(nm.removeSmallest(), ahm.removeSmallest());
            }
        }
    }
}
