package deque;
import java.util.Iterator;

public class ArrayDeque<T> implements deque<T>{

    int arraySize;
    int size;
    int nextFirst;
    int nextLast;
    T[] array;

    public ArrayDeque(){
        arraySize = 8;
        size = 0;
        nextFirst = 4;
        nextLast = 5;
        array = (T[]) new Object[arraySize];
    }

    private void resize(){
        T[] newArray = (T[]) new Object[arraySize * 2];
        for (int i = 0; i < arraySize; i++){
            if (i < nextLast){
                newArray[i] = array[i];
            }else{
                newArray[i + arraySize] = array[i];
            }
        }
        nextFirst += arraySize;
        arraySize *= 2;
        array = newArray;
    }

    @Override
    public void addFirst(T item) {
        if (size == arraySize){
            resize();
        }
        array[nextFirst] = item;
        size ++;
        nextFirst = nextFirst(nextFirst);
    }

    @Override
    public void addLast(T item) {
        if (size == arraySize){
            resize();
        }
        array[nextLast] = item;
        size ++;
        nextLast = nextLast(nextLast);
    }

    @Override
    public int size() {
        return size;
    }

    private int nextFirst(int newFirst){
        if (newFirst - 1 < 0){
            newFirst = arraySize - 1;
        }else{
            newFirst --;
        }
        return newFirst;
    }

    private int nextLast(int newLast){
        if (newLast + 1 >= arraySize){
            newLast = 0;
        }else{
            newLast ++;
        }
        return newLast;
    }

    @Override
    public void printDeque() {
        for (int i = nextFirst + 1; i < arraySize; i++){
            if (array[i] != null){
                System.out.print(array[i] + " ");
            }
        }
        for (int i = 0; i < nextLast; i++){
            if (array[i] != null){
                System.out.print(array[i] + " ");
            }
        }
    }

    @Override
    public T removeFirst() {
        if(isEmpty()) return null;
        nextFirst = nextLast(nextFirst);
        T removedItem = array[nextFirst];
        array[nextFirst] = null;
        size --;
        return removedItem;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) return null;
        nextLast = nextFirst(nextLast);
        T removedItem = array[nextLast];
        array[nextLast] = null;
        size --;
        return removedItem;
    }

    @Override
    public T get(int index) {
        int pos = index + nextFirst + 1;
        if (pos >= arraySize) pos -= arraySize;
        return array[pos];
    }

    @Override
    public Iterator iterator() {
        return new ArrayDequeIterator();
    }

    private class ArrayDequeIterator implements Iterator<T>{

        private int remaining = size;
        private int position = nextFirst;

        @Override
        public boolean hasNext() {
            return remaining != 0;
        }

        @Override
        public T next() {
            position = nextLast(position);
            remaining --;
            return array[position];
        }
    }
}
