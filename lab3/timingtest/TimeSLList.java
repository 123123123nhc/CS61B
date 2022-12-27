package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

import java.sql.Time;
/**
 * Created by hug.
 */
public class TimeSLList {
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
        timeGetLast(128000,10000);
    }

    public static void timeGetLast(int finalsize, int getimes) {
        AList Ns = new AList();
        AList opCounts = new AList();
        AList times = new AList();

        int startsize = 1000;
        while (startsize <= finalsize){
            Ns.addLast(startsize);
            opCounts.addLast(getimes);
            SLList timedarray = new SLList();
            for (int i = 0; i < startsize; i++){
                timedarray.addLast(i);
            }
            times.addLast(getWatch(getimes,timedarray));
            startsize *= 2;
        }
        printTimingTable(Ns,times,opCounts);

    }

    private static double getWatch(int getimes,SLList Timedarray){
        Stopwatch sw = new Stopwatch();
        for (int i = 0; i < getimes; i++){
            Timedarray.getLast();
        }
        double timeInSeconds = sw.elapsedTime();
        return timeInSeconds;
    }

}
