package deque;

import java.util.Iterator;


public class LinkedListDeque<T> implements deque<T> {

    private class Node{
        private Node prev;
        private Node next;
        private T item;

        private Node(T item){
            this.item = item;
        }
    }

    private int size;
    private Node sentinel;

    public LinkedListDeque(){
        size = 0;
        sentinel = new Node(null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    @Override
    public void addFirst(T item) {
        Node node = new Node(item);
        node.next = sentinel.next;
        sentinel.next.prev = node;
        sentinel.next = node;
        node.prev = sentinel;
        size ++;
    }

    @Override
    public void addLast(T item) {
        Node node = new Node(item);
        node.prev = sentinel.prev;
        sentinel.prev.next = node;
        sentinel.prev = node;
        node.next = sentinel;
        size ++;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        Node temp = sentinel;
        if (temp.item == null){
        }else{
            while (temp.next.item != null){
                temp = temp.next;
                System.out.print(temp.next.item);
            }
            System.out.println("");
        }


    }

    @Override
    public T removeFirst() {
        if (size == 0){
            return null;
        }
        T removedItem = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        size --;
        return removedItem;
    }

    @Override
    public T removeLast() {
        if (size == 0){
            return null;
        }
        T removedItem = sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        size --;
        return removedItem;
    }

    @Override
    public T get(int index) {
        if (index > size){
            return null;
        }
        int remaining = index;
        Node node = sentinel;
        while(remaining >= 0){
            node = node.next;
            remaining --;
        }
        return node.item;
    }

    public T getRecursive(int index){
        if (index > size){
            return null;
        }
        return getRecursive(index, sentinel);
    }

    private T getRecursive(int index, Node node){
        if (index == 0){
            return node.item;
        }
        return getRecursive(index-1, node.next);
    }

    public Iterator<T> iterator() {
        return new LinkedListDequeIterator();
    }

    private class LinkedListDequeIterator implements Iterator<T>{

        private int remained = size;

        @Override
        public boolean hasNext() {
            return remained != 0;
        }

        @Override
        public T next() {
            Node node = sentinel;
            node = node.next;
            remained --;
            return node.item;
        }
    }
}
