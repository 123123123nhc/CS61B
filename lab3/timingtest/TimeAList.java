package timingtest;
import edu.princeton.cs.algs4.Stopwatch;
import org.checkerframework.checker.units.qual.A;

/**
 * Created by hug.
 */
public class TimeAList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeAListConstruction(128000);
    }

    public static void timeAListConstruction(int n) {
        AList Ns = new AList();
        AList times = new AList();
        AList Timedarray = new AList();
        int end = 1000;

        // Addlast in timedarray from 1 to 128000;
        while (end <= n){
            Ns.addLast(end);
            times.addLast(getWatch(end,Timedarray));
            end = end * 2;
        }
        printTimingTable(Ns,times,Ns);
    }
    private static double getWatch(int end,AList Timedarray){
        Stopwatch sw = new Stopwatch();
        for (int i = 0; i < end; i++){
            Timedarray.addLast(1);
        }
        double timeInSeconds = sw.elapsedTime();
        return timeInSeconds;
    }
}
