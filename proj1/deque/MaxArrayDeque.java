package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T>{

    private Comparator<T> comparator;

    public MaxArrayDeque(){
        super();
    }

    public MaxArrayDeque(Comparator<T> c){
        super();
        comparator = c;
    }

    public T max(){
        T max = get(0);
        for(T item : this){
            if (comparator.compare(max,item) < 0){
                max = item;
            }
        }
        return max;
    }

    public T max(Comparator<T> c){
        T max = get(0);
        for(T item : this){
            if (c.compare(max,item) < 0){
                max = item;
            }
        }
        return max;
    }
}
