import java.util.*;
import java.util.concurrent.CountDownLatch;

class ThreadSynchronizer{
    public static CountDownLatch CDL;
}

class NumberSearcher implements Runnable{

    public int largest;

    int[] array;
    int start;
    int end;

    public NumberSearcher(int[] array, int start, int end){
        this.array = array;
        this.start = start;
        this.end = end;
    }

    public void run(){
        largest = array[start];
        for(int i = start; i < end; i++){
            if(array[i] > largest) largest = array[i];
        }

        ThreadSynchronizer.CDL.countDown();
    }
}

class Task3_2{
    public static void main(String[] args){

        int N = 100;
        int array[] = new int[N];
        Random r = new Random();

        for(int i = 0; i < N; i++){
            array[i] = r.nextInt(1000);
        }

        ThreadSynchronizer.CDL = new CountDownLatch(4);

        NumberSearcher searcher1 = new NumberSearcher(array, 0, N / 4);
        NumberSearcher searcher2 = new NumberSearcher(array, N / 4, N / 2);
        NumberSearcher searcher3 = new NumberSearcher(array, N / 2, 3 * N / 4);
        NumberSearcher searcher4 = new NumberSearcher(array, 3 * N / 4, N);

        new Thread(searcher1).start();
        new Thread(searcher2).start();
        new Thread(searcher3).start();
        new Thread(searcher4).start();

        try{
            ThreadSynchronizer.CDL.await();
        } catch (Exception e){
            System.out.println(e);
            return;
        }

        int largest_1_2 = searcher1.largest > searcher2.largest ? searcher1.largest : searcher2.largest;
        int largest_3_4 = searcher3.largest > searcher4.largest ? searcher3.largest : searcher4.largest;
        int largest = largest_1_2 > largest_3_4 ? largest_1_2 : largest_3_4;

        System.out.printf("The largest value is: %d\n", largest);
    }
}