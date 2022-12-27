package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Assert;
import org.junit.Test;
import timingtest.AList;

import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
  // YOUR TESTS HERE
  @Test
  public void testThreeAddThreeRemove() {
      AListNoResizing<Integer> correct = new AListNoResizing<>();
      BuggyAList<Integer> broken = new BuggyAList<>();

      correct.addLast(5);
      correct.addLast(10);
      correct.addLast(15);

      broken.addLast(5);
      broken.addLast(10);
      broken.addLast(15);

      assertEquals(correct.size(), broken.size());

      assertEquals(correct.removeLast(), broken.removeLast());
      assertEquals(correct.removeLast(), broken.removeLast());
      assertEquals(correct.removeLast(), broken.removeLast());
  }

    @Test
    public void randomizedTest(){
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> B = new BuggyAList<>();

        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                B.addLast(randVal);
            }else if (operationNumber == 1) {
                // getLast
                if (L.size() != 0){
                    int last_L = L.getLast();
                    int last_B = B.getLast();
                    assertEquals(last_L,last_B);
                }

            }else if (operationNumber == 2) {
                //removeLast
                if (L.size() != 0) {
                    int removed_L = L.removeLast();
                    int removed_B = B.removeLast();
                    assertEquals(removed_L, removed_B);
                }
            }else if (operationNumber == 3) {
                // size
                int size = L.size();
            }
        }
    }
}

