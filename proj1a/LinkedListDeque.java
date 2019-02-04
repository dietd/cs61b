public class LinkedListDeque<T> {
    private class dll{
        public dll prev, next;
        public T item;
        public dll(dll prev, T item, dll next){
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    private dll sentFront, sentBack;
    private int size;
    private dll getpointer;

    public LinkedListDeque(){
        sentFront = new dll(null, null, null);
        sentBack = new dll(sentFront, null, null);
        sentFront.next = sentBack;
        size = 0;
    }

    public LinkedListDeque(LinkedListDeque other){
        sentFront = new dll(null, null, null);
        sentBack = new dll(sentFront, null, null);
        sentFront.next = sentBack;
        size = 0;
        int index = 0;
        while(index < other.size()){
            this.addFirst((T) other.get(index));
            index += 1;
        }
    }

    public void addFirst(T item){
        dll first = new dll(sentFront, item, sentFront.next);
        sentFront.next.prev = first;
        sentFront.next = first;
        size = size + 1;
    }

    public void addLast(T item){
        dll last = new dll(sentBack.prev, item, sentBack);
        sentBack.prev.next = last;
        sentBack.prev = last;
        size = size + 1;
    }

    public boolean isEmpty(){
        return sentFront.next.equals(sentBack) && sentBack.prev.equals(sentFront);
    }

    public int size(){
        return size;
    }

    public void printDeque(){
        dll p = sentFront.next;
        while(!p.equals(sentBack)){
            System.out.print(p.item);
            System.out.print(" ");
            p = p.next;
        }
    }

    public T removeFirst(){
        if(this.isEmpty()){
            return null;
        }
        T first = sentFront.next.item;
        sentFront.next.next.prev = sentFront;
        sentFront.next = sentFront.next.next;
        size = size - 1;
        return first;
    }

    public T removeLast(){
        if(this.isEmpty()){
            return null;
        }
        T last = sentBack.prev.item;
        sentBack.prev.prev.next = sentBack;
        sentBack.prev = sentBack.prev.prev;
        size = size - 1;
        return last;
    }

    public T get(int index){
        if(this.isEmpty() || index < 0 || index > size - 1){
            return null;
        }
        dll p = sentFront.next;
        int i = 0;
        while(i< index){
            p = p.next;
            if(p.equals(sentBack)){
                return null;
            }
            i = i + 1;
        }
        return p.item;
    }

    public T getRecursive(int index){
        if(isEmpty() || index < 0 || index > size - 1){ return null;}
        if(index == 0){
            T item = getpointer.item;
            getpointer = sentFront.next;
            return item;
        }
        getpointer = getpointer.next;
        return getRecursive(index - 1);
    }
}
