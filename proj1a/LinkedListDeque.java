public class LinkedListDeque<T> {

    private class Dll {
        private Dll prev, next;
        private T item;
        Dll(Dll prev, T item, Dll next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    private Dll sentFront, sentBack;
    private int size;

    public LinkedListDeque() {
        sentFront = new Dll(null, null, null);
        sentBack = new Dll(sentFront, null, null);
        sentFront.next = sentBack;
        size = 0;
    }
    public LinkedListDeque(LinkedListDeque other) {
        sentFront = new Dll(null, null, null);
        sentBack = new Dll(sentFront, null, null);
        sentFront.next = sentBack;
        size = 0;
        int index = 0;
        while(index < other.size()){
            this.addFirst((T) other.get(index));
            index += 1;
        }
    }

    public void addFirst(T item) {
        Dll first = new Dll(sentFront, item, sentFront.next);
        sentFront.next.prev = first;
        sentFront.next = first;
        size = size + 1;
    }

    public void addLast(T item) {
        Dll last = new Dll(sentBack.prev, item, sentBack);
        sentBack.prev.next = last;
        sentBack.prev = last;
        size = size + 1;
    }

    public boolean isEmpty() {
        return sentFront.next.equals(sentBack) && sentBack.prev.equals(sentFront);
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        Dll p = sentFront.next;
        while(!p.equals(sentBack)){
            System.out.print(p.item);
            System.out.print(" ");
            p = p.next;
        }
    }

    public T removeFirst() {
        if (this.isEmpty()) {
            return null;
        }
        T first = sentFront.next.item;
        sentFront.next.next.prev = sentFront;
        sentFront.next = sentFront.next.next;
        size = size - 1;
        return first;
    }

    public T removeLast() {
        if (this.isEmpty()) {
            return null;
        }
        T last = sentBack.prev.item;
        sentBack.prev.prev.next = sentBack;
        sentBack.prev = sentBack.prev.prev;
        size = size - 1;
        return last;
    }

    public T get(int index) {
        if(this.isEmpty() || index < 0 || index > size - 1){
            return null;
        }
        Dll p = sentFront.next;
        int i = 0;
        while(i< index){
            p = p.next;
            if(p.equals(sentBack)) {
                return null;
            }
            i = i + 1;
        }
        return p.item;
    }

    public T getRecursive(int index) {
        if(isEmpty() || index < 0 || index > size - 1){ return null;}
        Dll pointer = sentFront.next;
        return recursiveHelper(index, pointer);
    }

    public T recursiveHelper(int index, Dll pointer){
        if (index == 0){
            return pointer.item;
        }
        return recursiveHelper(index - 1, pointer.next);
    }
}
