import edu.princeton.cs.algs4.Queue;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class TestSortAlgs {

    @Test
    public void testQuickSort() {
        Queue<Integer> q = new Queue<>();
        q.enqueue(1);
        q.enqueue(5);
        q.enqueue(4);
        q.enqueue(2);
        q.enqueue(3);
        q.enqueue(10);
        q.enqueue(12);
        q.enqueue(15);
        q.enqueue(19);
        q.enqueue(1231);

        assertTrue(isSorted(QuickSort.quickSort(q)));
    }

    @Test
    public void testMergeSort() {
        Queue<Integer> q = new Queue<>();
        q.enqueue(1);
        q.enqueue(5);
        q.enqueue(4);
        q.enqueue(2);
        q.enqueue(3);
        q.enqueue(10);
        q.enqueue(12);
        q.enqueue(15);
        assertTrue(isSorted(MergeSort.mergeSort(q)));
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
