import java.util.*;

class GlobalSychronizer{
    public static volatile boolean foundPassword = false;
}

class PasswordGuesser implements Runnable {

    String targetFile;
    String outputFile;
    int pwRangeStart;
    int pwRangeStop;

    public PasswordGuesser(String targetFile, String outputFile, int pwRangeStart, int pwRangeStop){
        this.targetFile = targetFile;
        this.outputFile = outputFile;
        this.pwRangeStart = pwRangeStart;
        this.pwRangeStop = pwRangeStop;
    }

    public void run(){
        for(int currentGuess = pwRangeStart; pwRangeStart < pwRangeStop && !GlobalSychronizer.foundPassword; currentGuess++){
            String password = String.valueOf(currentGuess);
            try{
                boolean passwordCorrect = ex02_fileCrypto.Decrypt(targetFile, outputFile, password);

                if(passwordCorrect){
                    GlobalSychronizer.foundPassword = true;
                    System.out.printf("The password for file '%s' is '%s'\n", targetFile, password);
                }

            } catch (Exception e){
                System.out.println("Unexpected Error: ");
                System.out.println(e);
            }
        }
    }
}

class Task2_3{
    public static void main(String[] args){
        int numberOfThreads = 4;

        String target = args[0];
        String output = args[1];

        // 99999
        // 100000

        int fraction = 100000 / numberOfThreads;

        for(int i = 0; i < numberOfThreads; i++){
            int start = i * fraction;
            int end = (i + 1) * fraction;
            System.out.printf("Start: %d; End: %d\n", start, end);
            Thread t = new Thread(new PasswordGuesser(target, output, start, end));
            t.start();
        }
    }
}