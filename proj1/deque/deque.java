package deque;

import java.util.Iterator;

/** It is the API of the deque, The input can be every data type T and it should be implemented by linkedList Deque and ArrayList Deque
 *  Author : Haocheng Ni
 */

public interface deque<T> extends Iterable<T> {

    void addFirst(T item);

    void addLast(T item);

    default boolean isEmpty(){ return size() == 0; };

    int size();

    void printDeque();

    T removeFirst();

    T removeLast();

    T get(int index);

    boolean equals(Object o);

}
