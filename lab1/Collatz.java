/** Class that prints the Collatz sequence starting from a given number.
 *  @author Haocheng Ni
 */
public class Collatz {


    /** If n is not equals to zero, then it will be go to the next number!
     * @param n
     * @return
     */
    public static int nextNumber(int n) {
        if (n%2 == 1){
            n = n*3+1;
        }else{
            n = n/2;
        }
        return n;
    }

    public static void main(String[] args) {
        int n = 5;
        System.out.print(n + " ");
        while (n != 1) {
            n = nextNumber(n);
            System.out.print(n + " ");
        }
        System.out.println();
    }
}

