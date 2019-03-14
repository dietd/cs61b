package bearmaps;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ArrayHeapMinPQTest {

    @Test
    public void randomTest() {
        NaiveMinPQ<Integer> nm = new NaiveMinPQ<>();
        ArrayHeapMinPQ<Integer> ahm = new ArrayHeapMinPQ<>();
        int i = 0;
        ArrayList<Integer> nums = new ArrayList<>();

        while (i < 1000) {

            int num = (int) Math.floor(Math.random() * 100);
            double weight = Math.floor(Math.random() * 1000);
            int action = (int) Math.floor(Math.random() * 5);

            if (action == 0) {
                nm.add(num, weight);
                ahm.add(num, weight);
                nums.add(num);
            }

            if (action == 1) {
                if (nums.size() > 0) {
                    int actual = nm.removeSmallest();
                    assertEquals(nm.removeSmallest(), ahm.removeSmallest());
                    nums.remove((Object) actual);
                }
            }

            if (action == 2) {
                if (nums.size() > 0) {
                    assertEquals(nm.getSmallest(), ahm.getSmallest());
                }
            }

            if (action == 3) {
                if (!nums.isEmpty()) {
                    int randNum = (int) Math.floor(Math.random() * nums.size());
                    int item = nums.get(randNum);
                    nm.changePriority(item, weight);
                    ahm.changePriority(item, weight);
                }
            }

            if (action == 4) {
                assertEquals(nm.size(), ahm.size());
            }
        }
    }

    @Test
    public void sanityRemove() {
        NaiveMinPQ<Integer> nm = new NaiveMinPQ<>();
        ArrayHeapMinPQ<Integer> ahm = new ArrayHeapMinPQ<>();

        nm.add(1, 5);
        nm.add(2, 1);
        nm.add(3, 2);
        nm.add(4, 4);
        nm.add(5, 3);

        ahm.add(1, 5);
        ahm.add(2, 1);
        ahm.add(3, 2);
        ahm.add(4, 4);
        ahm.add(5, 3);

        assertEquals(nm.removeSmallest(), ahm.removeSmallest());
        assertEquals(nm.removeSmallest(), ahm.removeSmallest());
        assertEquals(nm.removeSmallest(), ahm.removeSmallest());
        assertEquals(nm.removeSmallest(), ahm.removeSmallest());
        assertEquals(nm.removeSmallest(), ahm.removeSmallest());


        /** Add Random Stuff */
        nm = new NaiveMinPQ<>();
        ahm = new ArrayHeapMinPQ<>();

        Set<Double> numSet = new HashSet<>();


            for (int i = 0; i < 1000; i += 1) {
                double weight = Math.random() * 1000;

                /**while (numSet.contains(weight)) {
                    weight = Math.random() * 1000;
                } */

                nm.add(i, weight);
                ahm.add(i, weight);
                numSet.add(weight);
            }

            for (int j = 0; j < 1000; j += 1) {
                assertEquals("Iteration: " + j, nm.removeSmallest(), ahm.removeSmallest());
            }
    }
}