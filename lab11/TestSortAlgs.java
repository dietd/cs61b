import edu.princeton.cs.algs4.Queue;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class TestSortAlgs {

    @Test
    public void testQuickSort() {
        Queue<Integer> q = new Queue<>();

        for (int i = 0; i < 100000; i += 1) {
            q.enqueue((int) (Math.random() * 100000));
        }

        Queue<Integer> test = QuickSort.quickSort(q);
        assertTrue(test.size() == 100000);
        assertTrue(isSorted(test));
    }

    @Test
    public void testMergeSort() {
        Queue<Integer> q = new Queue<>();

        for (int i = 0; i < 100000; i += 1) {
            q.enqueue((int) (Math.random() * 100000));
        }

        Queue<Integer> test = MergeSort.mergeSort(q);
        assertTrue(test.size() == 100000);
        assertTrue(isSorted(test));
    }

    /**
     * Returns whether a Queue is sorted or not.
     *
     * @param items  A Queue of items
     * @return       true/false - whether "items" is sorted
     */
    private <Item extends Comparable> boolean isSorted(Queue<Item> items) {
        if (items.size() <= 1) {
            return true;
        }
        Item curr = items.dequeue();
        Item prev = curr;
        while (!items.isEmpty()) {
            prev = curr;
            curr = items.dequeue();
            if (curr.compareTo(prev) < 0) {
                return false;
            }
        }
        return true;
    }
}
