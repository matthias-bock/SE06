import java.util.*;
import java.util.concurrent.CountDownLatch;


class ex03_sort2Proc
{
    static final int N = 100000;
    static int A[] = new int[N];
    static CountDownLatch cdl = new CountDownLatch(2);
    
    static class SortThread implements Runnable
    {
        int Left, Right;
        
        public SortThread(int left, int right) 
        { 
            Left = left; Right = right;   
        }
                        
        public void run()
        {
            Arrays.sort(A, Left, Right);
            cdl.countDown();
        }
    }
                    
    static public void main(String args[]) 
    {
        Random rand = new Random();
        for(int i = 0; i < N; ++i)
            A[i] = rand.nextInt(1000);
        
        new Thread(new SortThread(0, N / 2)).start();
        new Thread(new SortThread(N / 2, N)).start();
        
        try
        {
            cdl.await();
        }
        catch(Exception e){}
            
        // here add merging code

        // Initialize counter variables
        int iL = 0;
        int iR = N / 2;
        int i = 0;

        // Allocate new array for out of place array merging algorithm
        int merged[] = new int[N];

        // While either the left or right half of the array are not yet empty
        while(iL < N / 2 && iR < N){
            
            // Merge the smaller value and increase the index
            if(A[iL] < A[iR]){
                merged[i++] = A[iL++];
            } else{
                merged[i++] = A[iR++];
            }
        }

        // Loop over both arrays and merge remaining elements
        while(iL < N / 2){
            merged[i++] = A[iL++];
        }

        while(iR < N){
            merged[i++] = A[iR++];
        }

        // Replace array with now sorted array
        A = merged;

        boolean sorted = true;
        for(int j = 0; j < N - 1 && sorted; j++){
            if(A[j] > A[j + 1]) sorted = false;
        }

        System.out.printf("Sorted: %b\n", sorted);
    }
}