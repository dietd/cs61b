
public class ADtest {
    public static void main(String args[]){
        getRecursiveTest();
    }

    public static void getRecursiveTest(){
        LinkedListDeque ll = new LinkedListDeque();
        ll.addFirst(1);
        ll.addFirst(2);
        ll.addFirst(3);
        ll.addFirst(4);
        System.out.println(ll.getRecursive(3));
    }

    public static void addRandom() {
        ArrayDeque ad = new ArrayDeque();
        ad.addFirst(0);

        ad.get(0);
        ad.get(0);
        ad.get(0);
        ad.get(0);
        ad.addLast(5);
        ad.removeFirst();
        ad.removeFirst();
        ad.addFirst(8);

        ad.get(0);
        ad.get(0);
        ad.addLast(11);

        ad.addFirst(12);
        ad.addLast(13);

        System.out.println( (int) ad.get(2) == 11 );
    }



    public static void checkSize(){
        ArrayDeque ad = new ArrayDeque();
        for(int i = 0; i < 10; i++){
            ad.addFirst(1);
            ad.addLast(2);
        }
        for(int i = 0; i < 5; i++){
            ad.removeFirst();
        }
        ad.printDeque();
    }
}
