import static org.junit.Assert.*;
import org.junit.Test;
public class TestArrayDequeGold {

    StudentArrayDeque<Integer> sad = new StudentArrayDeque<>();
    ArrayDequeSolution<Integer> ads = new ArrayDequeSolution<>();

    @Test
    public void test() {
        String sequence = "";
        while(true) {
            int randnum = StdRandom.uniform(3);
            int addnum = StdRandom.uniform(100);
            assertEquals(sequence + '\n' + "size()", ads.size(), ads.size());
            if(ads.size() == 0) {
                if (randnum >= 2) {
                    ads.addFirst(addnum);
                    sad.addFirst(addnum);
                } else {
                    ads.addLast(addnum);
                    sad.addFirst(addnum);
                }
            } else {
                if (randnum == 0) {
                    ads.addFirst(addnum);
                    sad.addFirst(addnum);
                    sequence += "addFirst(" + addnum + ")" + '\n';
                } else if (randnum == 1) {
                    ads.addLast(addnum);
                    sad.addLast(addnum);
                    sequence += "addLast(" + addnum + ")" + '\n';
                } else if (randnum == 2) {

                    Integer adsnum = ads.removeFirst();
                    Integer sadnum = sad.removeFirst();
                    sequence += "removeFirst()" + '\n';

                    assertEquals(sequence, adsnum, sadnum);

                } else if (randnum == 3) {

                    Integer adsnum = ads.removeLast();
                    Integer sadnum = sad.removeLast();
                    sequence += "removeLast()" + '\n';

                    assertEquals(sequence, adsnum, sadnum);
                }
            }
        }
    }
}
