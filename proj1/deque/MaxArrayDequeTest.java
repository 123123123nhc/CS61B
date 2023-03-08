package deque;

import org.junit.Assert.*;
import org.junit.Test;

import java.util.Comparator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MaxArrayDequeTest {

    public class integerComparator implements Comparator<Integer>{
        @Override
        public int compare(Integer o1, Integer o2) {
            return o1-o2;
        }
    }

    public class StringComparator implements Comparator<String>{

        @Override
        public int compare(String o1, String o2) {
            return o1.compareTo(o2);
        }
    }

    @Test
    public void maxItemTest1(){

        integerComparator ic = new integerComparator();

        MaxArrayDeque<Integer>  mad1 = new MaxArrayDeque(ic);
        MaxArrayDeque<Integer>  mad2 = new MaxArrayDeque<Integer>();
        mad1.addFirst(1);
        mad1.addFirst(10);
        mad1.addLast(8);
        mad1.addFirst(7);
        assertEquals(10, (int) mad1.max());

        mad2.addFirst(1);
        mad2.addFirst(10);
        mad2.addLast(8);
        mad2.addFirst(7);
        assertEquals(10, (int) mad2.max(ic));

    }

    @Test
    public void maxItemTest2(){

        StringComparator sc = new StringComparator();

        MaxArrayDeque<String>  mad1 = new MaxArrayDeque(sc);
        MaxArrayDeque<String>  mad2 = new MaxArrayDeque<String>();
        mad1.addFirst("cat");
        mad1.addFirst("dog");
        mad1.addLast("pig");
        mad1.addFirst("elephant");
        assertEquals("pig", (String) mad1.max());

        mad2.addFirst("cat");
        mad2.addFirst("dog");
        mad2.addLast("pig");
        mad2.addFirst("elephant");
        assertEquals("pig", (String) mad1.max(sc));

    }
}
