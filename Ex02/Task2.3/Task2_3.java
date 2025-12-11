import java.util.*;

// Class for thread synchronisation
class GlobalSychronizer{
    public static volatile boolean foundPassword = false;
}

// Class to guess the passowrd of a file
class PasswordGuesser implements Runnable {

    // The target file to decrypt
    String targetFile;
    // The output file used to write the decrypted contens
    String outputFile;
    // Start of the number range that this instance should analyze (inclusive)
    int pwRangeStart;
    // End of the number range that this instance should analyze (exclusive)
    int pwRangeStop;
    // The maximum number of digits the password can have
    int maxDigits;

    public PasswordGuesser(String targetFile, String outputFile, int pwRangeStart, int pwRangeStop, int maxDigits){
        this.targetFile = targetFile;
        this.outputFile = outputFile;
        this.pwRangeStart = pwRangeStart;
        this.pwRangeStop = pwRangeStop;
        this.maxDigits = maxDigits;
    }

    public void run(){
        // Loop over all numbers in the current number range, as long as the password has not yet been found
        for(int currentGuess = pwRangeStart; currentGuess < pwRangeStop && !GlobalSychronizer.foundPassword; currentGuess++){
            
            // Get the number of digits the current guess has
            int currentGuessDigits = currentGuess == 0 ? 1 : (int)Math.floor(Math.log10(currentGuess)) + 1;
            
            // Loop over all digit numbers from the current guess to the max digit number provided
            for(int digits = currentGuessDigits; digits <= maxDigits; digits++){

                // Guess the password.
                // Fill in leading '0's depending on the current digit count
                String password = String.format("%0" + digits + "d", currentGuess);
                
                try{
                    // Check if the password was correct
                    boolean passwordCorrect = ex02_fileCrypto.Decrypt(targetFile, outputFile, password);

                    if(passwordCorrect){
                        // If so, notify other threads and print result
                        GlobalSychronizer.foundPassword = true;
                        System.out.printf("The password for file '%s' is '%s'\n", targetFile, password);
                    }

                } catch (Exception e){
                    // Catch possible exceptions in the decrypt function
                    System.out.println("Error while decrypting: ");
                    System.out.println(e);
                }
            }
        }
    }
}

class Task2_3{
    public static void main(String[] args){
        int numberOfThreads = 4;

        String target = args[0];
        String output = args[1];
        
        // The largest number that is a potential password is 99999
        // Since the passowrd guesser excludes the end of the range specified, the number is increased by one
        int max = 99999 + 1;

        int maxDigits = 5;

        // Calculate the size of the chunks each thread has to process
        // Round up if the result is not a integer
        int chunkSize = (int)Math.ceil(max / (float)numberOfThreads);

        for(int i = 0; i < numberOfThreads; i++){
            int start = i * chunkSize;
            int end = (i + 1) * chunkSize;
            
            // Because uneven results are rounded up, check that the end of the last chunk does no go beyond the maximum possible number
            end = Math.min(end, max);
            
            System.out.printf("Start: %d; End: %d\n", start, end);
            
            // Start guessing the password
            Thread t = new Thread(new PasswordGuesser(target, output, start, end, maxDigits));
            t.start();
        }
    }
}