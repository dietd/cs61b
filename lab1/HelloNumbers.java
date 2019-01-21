public class HelloNumbers {
    public static void main(String[] args) {
        int x = 0;
        int last = 0;
        while (x <= 9) {
            System.out.print(last + " ");
            x = x + 1;
            last += x;
        }
        System.out.print('\n');
    }
}
