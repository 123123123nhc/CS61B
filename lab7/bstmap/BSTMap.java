package bstmap;
import java.util.*;

public class BSTMap<K extends Comparable<K>,V> implements Map61B<K, V>{
    private class BSTNode{
        private K key;
        private V val;
        private BSTNode left, right;
        private int size;

        public BSTNode(K key, V val, int size){
            this.key = key;
            this.val = val;
            this.size = size;
        }
    }
    private BSTNode node;
    private HashSet<K> keyset = new HashSet();
    public BSTMap(){ }

    @Override
    public boolean containsKey(K key) {
        return containsKey(node, key);
    }

    private boolean containsKey(BSTNode node, K key){
        if (node == null) return false;
        if (key.compareTo(node.key) == 0) return true;
        if (key.compareTo(node.key) > 0) return containsKey(node.right, key);
        return containsKey(node.left, key);
    }

    @Override
    public int size() {
        return size(node);
    }

    private int size(BSTNode node){
        if (node == null) return 0;
        return node.size;
    }

    @Override
    public Set<K> keySet() {
        return keyset;
    }

    @Override
    public V get(K key) {
        return get(node, key);
    }

    private V get(BSTNode node, K key){
        if (node == null) return null;
        if (key.compareTo(node.key) == 0) return node.val;
        if (key.compareTo(node.key) > 0) return get(node.right, key);
        return get(node.left, key);
    }

    @Override
    public V remove(K key) {
        if (!containsKey(key)) throw new NoSuchElementException();
        V originalValue = get(key);
        node = remove(node,key);
        keyset.remove(key);
        return originalValue;
    }

    private BSTNode remove(BSTNode node, K key){
        if (key.compareTo(node.key) > 0) node.right = remove(node.right, key);
        else if (key.compareTo(node.key) < 0) node.left = remove(node.left, key);
        else{
            if (node.left == null) return node.right;
            else if (node.right == null) return node.left;
            else{
                BSTNode temp = node.right;
                remove(minK(node.right));
                node.key = minK(temp);
                node.val = get(minK(temp));
            }
        }
        node.size = size(node.left) + size(node.right) + 1;
        return node;
    }

    @Override
    public V remove(K key, V value) {
        if (!containsKey(key)) throw new NoSuchElementException();
        V originalValue = get(key);
        node = remove(node,key);
        keyset.remove(key);
        return originalValue;
    }

    @Override
    public void clear() {
        node = null;
    }

    @Override
    public void put(K key, V value) {
        node = put(node,key,value);
        keyset.add(key);
    }

    public K minK(BSTNode node){
        if (node.left != null) return minK(node.left);
        return node.key;
    }

    private BSTNode put(BSTNode node, K key, V value){
        if (node == null) return new BSTNode(key, value, 1);
        if (key.compareTo(node.key) > 0) node.right = put(node.right, key, value);
        else if (key.compareTo(node.key) < 0) node.left = put(node.left, key, value);
        else node.val = value;
        node.size = size(node.left) + size(node.right) + 1;
        return node;
    }

    @Override
    public Iterator<K> iterator() {
        return new IteratorOfK(node);
    }

    private class IteratorOfK implements Iterator<K>{
        private BSTNode node;
        private int pos = 0;
        public IteratorOfK(BSTNode node){
            this.node = node;
        }

        @Override
        public boolean hasNext() {
            return node != null;
        }

        @Override
        public K next() {
            if(!hasNext()){
                throw new NoSuchElementException();
            }else{
                K minKey = minK(node);
                node = BSTMap.this.remove(node,minKey);
                return minKey;
            }
        }
    }

    public void printInOrder(){
        printInOrderByMidTravel(node);
    }

    private void printInOrderByMidTravel(BSTNode node){
        if (node == null) return;
        printInOrderByMidTravel(node.left);
        System.out.println("key: " + node.key + " value: " + node.val);
        printInOrderByMidTravel(node.right);
    }

}
