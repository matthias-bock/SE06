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
        // choose a random number and signal that this participant is done
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
            // Get the number of processes
            P = Integer.parseInt(args[0]);
        } catch (Exception e){
            System.out.println("Error: ");
            System.out.println(e);
            return;
        }

        GameParticipant participants[] = new GameParticipant[P];
        
        // Initialize the game participants
        for(int i = 0; i < P; i++){
            participants[i] = new GameParticipant(i + 1, 9);
        }

        boolean gameOver = false;
        int round = 1;
        
        // This list tracks all the winning pariticipants
        Vector<GameParticipant> winners = new Vector<GameParticipant>();

        while(!gameOver){

            GameCoordinator.CDL = new CountDownLatch(P);
            for(GameParticipant p : participants){
                new Thread(p).start();
            }

            // Wait until all participants made a choice
            try{ GameCoordinator.CDL.await(); } catch (Exception e) {return;}

            // Reset the winners array
            winners.clear();
            int bestScore = participants[0].score;

            // Find this rounds winners
            for(GameParticipant p : participants){
                if(p.choice > bestScore){
                    // If the participants choice is better than the current winners score
                    // Clear the current winners and add the current participant
                    winners.clear();
                    winners.add(p);
                    bestScore = p.choice;
                } else if(p.choice == bestScore){
                    // If the participant has the same choice as the current winner
                    // Add him to the list of winners
                    winners.add(p);
                }
            }

            System.out.printf("Round %d winner(s):\n", round++);

            // Loop over all winners
            for(int i = 0; i < winners.size(); i++){
                System.out.printf("%d ", winners.get(i).ID);
                // Increment the score
                if((++winners.get(i).score) < 3){
                    // If the score is less than 3, remove from winner array
                    winners.removeElementAt(i--);
                } else{
                    // Score of one winner == 3 -> Game is over
                    gameOver = true;
                }
            }
            System.out.println("\n");
        }
        // The winner array only contains participants with a score of 3

        System.out.printf("Winner(s): ");
        for(GameParticipant winner : winners) System.out.printf("%d ", winner.ID);
        System.out.println();
    }
}