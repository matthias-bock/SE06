import java.util.*;
import java.util.concurrent.CountDownLatch;

class GameCoordinator{
    public static CountDownLatch CDL;
}

class GameParticipant implements Runnable{
    
    public int ID;
    public int choice;
    public int maxChoice;
    public int score;

    Random r;

    public GameParticipant(int ID, int maxChoice){
        this.ID = ID;
        this.maxChoice = maxChoice;
        r = new Random();
    }

    public void run(){
        this.choice = r.nextInt(maxChoice + 1);
        GameCoordinator.CDL.countDown();
    }
}

class Task3_3{
    public static void main(String[] args){
        if(args.length < 1){
            System.out.println("Missing argument.");
        }
        
        int P;

        try{
            P = Integer.parseInt(args[0]);
        } catch (Exception e){
            System.out.println("Error: ");
            System.out.println(e);
            return;
        }

        GameParticipant participants[] = new GameParticipant[P];
        
        for(int i = 0; i < P; i++){
            participants[i] = new GameParticipant(i + 1, 9);
        }

        boolean gameOver = false;
        int round = 1;
        
        Vector<GameParticipant> winners = new Vector<GameParticipant>();

        while(!gameOver){
            GameCoordinator.CDL = new CountDownLatch(P);
            for(GameParticipant p : participants){
                new Thread(p).start();
            }

            try{ GameCoordinator.CDL.await(); } catch (Exception e) {return;}

            winners.clear();
            int bestScore = participants[0].score;

            for(GameParticipant p : participants){
                if(p.choice > bestScore){
                    winners.clear();
                    winners.add(p);
                    bestScore = p.choice;
                } else if(p.choice == bestScore){
                    winners.add(p);
                }
            }

            System.out.printf("Round %d winner(s):\n", round++);

            for(int i = 0; i < winners.size(); i++){
                System.out.printf("%d ", winners.get(i).ID);
                if((++winners.get(i).score) < 3){
                    winners.removeElementAt(i--);
                } else{
                    gameOver = true;
                }
            }
            System.out.println("\n");

        }

        System.out.printf("Winner(s): ");
        for(GameParticipant winner : winners) System.out.printf("%d ", winner.ID);
        System.out.println();
    }
}