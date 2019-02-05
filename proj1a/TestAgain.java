public class TestAgain {
    public static void main(String[] args){
        for(int i = 1; i < 15; i++){
            randomCalls(Math.pow(2, i));
        }
    }

    public static void randomCalls(double num){

        long start = System.nanoTime();

        ArrayDeque ad = new ArrayDeque();

        while(num > 0){
            int call = (int) Math.floor(Math.random() * 4);
            switch(call){
                case(0):
                    ad.addLast(1);
                case(1):
                    ad.addFirst(1);
                case(2):
                    ad.removeFirst();
                case(3):
                    ad.removeLast();
                case(4):
                    ad.get((int) Math.floor(Math.random() * ad.size()));
                case(5):
                    ad.isEmpty();
            }
            num -= 1;
        }

        long end = System.nanoTime();

        System.out.println((double) end - start / 1000000000.0);

    }

    public static void trytoFuckItUp(double num){
        double yeet = num;
        ArrayDeque ad = new ArrayDeque();

        long start = System.nanoTime();

        while(num > 0){
            ad.addFirst(1);
            num -= 1;
        }
        while(num < yeet){
            ad.removeFirst();
            num += 1;
        }

        while(num > 0){
            ad.addLast(0);
            num -= 1;
        }

        while(num < yeet){
            ad.removeLast();
            num += 1;
        }

        long end = System.nanoTime();

        System.out.println(end - start / 1000000000.0);
    }
}
