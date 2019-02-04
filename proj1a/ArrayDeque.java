public class ArrayDeque<T>{
    private T[] deque;
    private int size, first, last;

    public ArrayDeque(){
        deque = (T[]) new Object[8];
        first = 0; last = 7; size = 0;
    }
    
    public ArrayDeque(ArrayDeque other){
        deque = (T[]) new Object[other.size()];
        for(int index = 0; index < other.size(); index++){
            deque[index] = (T) other.get(index);
        }
        first = 0; last = other.size() - 1; size = other.size();
    }

    private void resize(){
        if(size > deque.length || (size/deque.length < 0.25 && deque.length > 8)){
            int newsize = size * 2;
            T[] newdeque = (T[]) new Object[newsize];
            if(first >= last){
                System.arraycopy(deque, first, newdeque, 0, deque.length - first);
                System.arraycopy(deque, 0, newdeque, deque.length - first, last + 1);
            }
            else{
                System.arraycopy(deque, first, newdeque, 0, last - first);
            }
            deque = newdeque;
            first = 0; last = deque.length - 1;
        }
    }

    /** Loops index around if not in range */
    private int move(int index){
        if(index < 0){return deque.length - 1;}
        if(index > deque.length - 1){return 0;}
        return index;
    }

    public void addFirst(T item){
        size += 1;
        resize();
        first = move(first-1);
        deque[first] = item;
    }

    public void addLast(T item){
        size += 1;
        resize();
        last = move(last+1);
        deque[last] = item;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque(){
       for(int i = first; i != last; i = move(i + 1)){
           System.out.print(deque[i] + " ");
       }
       System.out.print(deque[last]);
    }

    public T removeFirst(){
        if(isEmpty()){ return null; }
        size -= 1;
        resize();
        T item = deque[first];
        deque[first] = null;
        first = move(first + 1);
        return item;
    }

    public T removeLast(){
        if(isEmpty()){ return null; }
        size -= 1;
        resize();
        T item = deque[last];
        deque[last] = null;
        last = move(last - 1);
        return item;
    }

    public T get(int index){
        if(this.isEmpty() || index < 0 || index > deque.length - 1){ return null; }
        return deque[move(first + index)];
    }
}
