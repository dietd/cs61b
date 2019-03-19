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
    public void testChangePriority2() {
        NaiveMinPQ<String> nm = new NaiveMinPQ<>();
        ArrayHeapMinPQ<String> ahm = new ArrayHeapMinPQ<>();
        int iterations = 10000;

        for (int i = 0; i < iterations; i += 1) {
            double initp = Math.random() * 1000;
            nm.add(Integer.toString(i), initp);
            ahm.add(Integer.toString(i), initp);
            assertEquals(nm.getSmallest(), ahm.getSmallest());
        }

        for (int j = 0; j < iterations; j += 1) {
            double finalp = Math.random() * 1000;
            nm.changePriority(Integer.toString(j), finalp);
            ahm.changePriority(Integer.toString(j), finalp);
            assertEquals(nm.getSmallest(), ahm.getSmallest());
        }

        for (int j = 0; j < iterations; j += 1) {
            assertEquals(nm.removeSmallest(), ahm.removeSmallest());
        }
    }

    @Test
    public void randomTest() {
        NaiveMinPQ<String> nm = new NaiveMinPQ<>();
        ArrayHeapMinPQ<String> ahm = new ArrayHeapMinPQ<>();
        int iterations = 1000000;
        for (int i = 0; i < iterations; i += 1) {
            int act = (int) Math.floor(Math.random() * 2);
            double p = Math.random() * 1000;
            String item = Integer.toString((int) Math.floor(Math.random() * 200));
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

    /** private String randString(int length) {

        String c = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
            "abcdefghijklmnopqrstuvwxyz0123456789";
        String s = "";
        for (int i = 0; i < length; i += 1) {
            s = s + c.charAt((int) Math.floor(Math.random() * c.length()));
        }
        return s;
    }

    private String[] randStringArray(int arrayLength, int strLength) {
        String[] sa = new String[arrayLength];
        Set<String> strSet = new HashSet<>();
        for (int i = 0; i < arrayLength; i += 1) {
            String s = randString(strLength);
            while (strSet.contains(s)) {
                s = randString(strLength);
            }
            strSet.add(s);
            sa[i] = s;
        }
        return sa;
    } */
}
