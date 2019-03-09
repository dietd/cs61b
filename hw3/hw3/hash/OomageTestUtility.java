package hw3.hash;

import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {

        int[] bucket = new int[M];
        for (int i = 0; i < bucket.length; i += 1) {
            bucket[i] = 0;
        }
        for (Oomage o : oomages) {
            int bucketNum = (o.hashCode() & 0x7FFFFFFF) % M;
            bucket[bucketNum] += 1;
        }

        for (int i : bucket) {
            double N = oomages.size();
            if (i < N / 50 || i >  N / 2.5) {
                return false;
            }
        }
        return true;
    }
}
