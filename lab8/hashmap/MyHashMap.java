package hashmap;
import java.util.*;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author Haocheng Ni
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    int initialSize;
    double maxLoad;
    private int DEFAULT_INITIAL_SIZE = 16;
    private double DEFAULT_MAXLOAD = 0.75;
    private int size = 0;
    // You should probably define some more!

    /** Constructors */
    public MyHashMap() {
        this.initialSize = DEFAULT_INITIAL_SIZE;
        this.maxLoad = DEFAULT_MAXLOAD;
        buckets = createTable(initialSize);
    }

    public MyHashMap(int initialSize) {
        this.maxLoad = DEFAULT_INITIAL_SIZE;
        buckets = createTable(initialSize);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        this.initialSize = initialSize;
        this.maxLoad = maxLoad;
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key,value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        Collection<Node>[] table = new Collection[tableSize];
        for(int i = 0; i < tableSize; i++){
            table[i] = createBucket();
        }
        return table;
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!

    public int getIndex(K key){
        return getIndex(key,buckets);
    }

    private int getIndex(K key, Collection<Node>[] table){
        int index = key.hashCode();
        return Math.floorMod(index, table.length);
    }
    @Override
    public void clear(){
        buckets = createTable(initialSize);
        size = 0;
    }

    @Override
    public boolean containsKey(K key){
        return getNode(key) != null;
    }

    @Override
    public V get(K key){
        Node node = getNode(key);
        if (node != null) return node.value;
        return null;
    }

    private Node getNode(K key){
        return getNode(key,buckets);
    }

    private Node getNode(K key,Collection<Node>[] table){
        int index =  getIndex(key);
        for(Node node : table[index]){
            if (node.key.equals(key)) return node;
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override

    public void put(K key, V value){
        if (getNode(key) != null) {
            getNode(key).value = value;
            return;
        }
        int index = getIndex(key);
        Node node = createNode(key, value);
        buckets[index].add(node);
        size ++;
        if ((double) (size/buckets.length) > maxLoad) resize(buckets.length);
    }

    @Override
    public Set<K> keySet() {
        HashSet<K> set = new HashSet<>();
        for (K key : this) {
            set.add(key);
        }
        return set;
    }

    @Override
    public V remove(K key){
        V removedValue = get(key);
        remove(key,buckets);
        size --;
        return removedValue;
    }

    private void remove(K key, Collection<Node>[] table){
        if (getNode(key) != null){
            int index = getIndex(key);
            for(Node node : table[index]){
                if (node.key.equals(key)){
                    table[index].remove(node);
                }
            }
        }
    }

    @Override
    public V remove(K key, V value){
        V removedValue = get(key);
        remove(key, value, buckets);
        size --;
        return removedValue;

    }

    private void remove(K key, V value, Collection<Node>[] table){
        if (getNode(key) != null){
            int index = getIndex(key);
            for(Node node : table[index]){
                if (node.key.equals(key) && node.value == value){
                    table[index].remove(node);
                }
            }
        }
    }

    private void resize(int tableSize){
        Collection<Node>[] newBuckets = createTable(tableSize*2);
        Iterator<Node> iterator = iteratorNode();
        Node currentNode;
        while (iterator.hasNext()){
            currentNode = iterator.next();
            int index = getIndex(currentNode.key, newBuckets);
            newBuckets[index].add(currentNode);
        }
        buckets = newBuckets;
    }

    public Iterator<K> iterator(){
        return new myHashMapIterator();
    }

    public Iterator<Node> iteratorNode(){
        return new myHashMapNodeIterator();
    }

    private class myHashMapIterator implements Iterator<K>{
        private Iterator<Node> nodeIterator = new myHashMapNodeIterator();
        @Override
        public boolean hasNext() {
            return nodeIterator.hasNext();
        }

        @Override
        public K next() {
            return nodeIterator.next().key;
        }
    }

    private class myHashMapNodeIterator implements Iterator<Node>{
        private int nodeLeft = size;
        Iterator<Collection<Node>> iterator = Arrays.stream(buckets).iterator();
        private Collection<Node> currentBucket;
        private Iterator<Node> currentBucketIterator;
        @Override
        public boolean hasNext() {
            return (nodeLeft != 0);
        }
        @Override
        public Node next() {
            if(!hasNext()){
                throw new NoSuchElementException();
            }
            if (currentBucketIterator == null || !currentBucketIterator.hasNext()){
                currentBucket = iterator.next();
                while (currentBucket.size() == 0){
                    currentBucket = iterator.next();
                }
                currentBucketIterator = currentBucket.iterator();
            }

            nodeLeft -= 1;
            return currentBucketIterator.next();
        }
    }

}
