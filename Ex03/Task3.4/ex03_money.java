import java.util.concurrent.CyclicBarrier;

// Globaly accessable cyclic barrier
class ThreadSynchronizer{
    public static CyclicBarrier barrier;
}

class AccountType{

    int balance;

    public AccountType(int initialBalance){
        this.balance = initialBalance;
    }

    public int GetValue(){
        return balance;
    }

    // Synchronized method to increase balance
    public synchronized void AddOneUnit(){
        balance++;
    }
}

class ex03_money
{
    static AccountType Account = new AccountType(0);

    static class Spouse implements Runnable
    {
        private int Sum;
        public Spouse(int sum) { Sum = sum; }
                        
        public void run()
        {
            for (int i = 0; i < Sum; i++)
            {
                Account.AddOneUnit();
            }

            // Each Spouse now waits on the barrier
            try{
                ThreadSynchronizer.barrier.await();
            } catch (Exception e){}
        }
    }
                    
    static public void main(String args[]) 
    {
        Spouse husband = new Spouse(500000);
        Spouse wife = new Spouse(500000);
        
        // Initialize the barrier to wait for 3 threads
        ThreadSynchronizer.barrier = new CyclicBarrier(3);

        new Thread(husband).start();
        new Thread(wife).start();
        
        try{
            // The main thread also waits on the barrier
            ThreadSynchronizer.barrier.await();
        }
        catch(Exception e){}
     
        System.out.println(Account.balance);
    }
}
