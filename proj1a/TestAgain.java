public class TestAgain {
    public static void main(String[] args){
        dude();
    }

    public static void dude(){
        ArrayDeque ad = new ArrayDeque();
        ad.addFirst(0);
        ad.removeLast();
        ad.addLast(2);
        ad.removeFirst();
        ad.addLast(4);
        ad.removeFirst();
        ad.addLast(6);
        ad.addLast(8);
        ad.addFirst(9);
        ad.addLast(10);
        ad.addLast(11);
        ad.addLast(12);
        ad.addLast(13);
        ad.addLast(14);
        ad.get(5);
        ad.removeFirst();
        ad.get(4);

        System.out.println(ad.removeLast() + " " + ad.removeLast());
        ad.printDeque();
    }

    public static void yeet(){
        int[] ya = new int[5];
        int[] yeet = new int[8];
        ya[0] = 2;
        ya[1] = 3;
        ya[2] = 4;
        ya[3] = 0;
        ya[4] = 1;

        System.arraycopy(ya, 3, yeet, 0, 2);
        System.arraycopy(ya, 0, yeet, 2, 3);

        for(int i : yeet){
            System.out.println(i);
        }

    }
}
