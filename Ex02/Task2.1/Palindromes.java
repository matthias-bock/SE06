import java.util.*;
import java.io.*;

class Global
{
    public static volatile Vector<String> palindromes = new Vector<String>();
}
 
class MyThread implements Runnable
{
    private String filename;
    
    public MyThread(String filename_) { filename = filename_; }
     
    public void run()
    {
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) // read file line by line
            {
                // reverse the next line
                String revline = (new StringBuilder(line)).reverse().toString();
                
                // if a new palindrome is found
                if(line.equals(revline) && !Global.palindromes.contains(line))
                {
                    System.out.println(line);
                    Global.palindromes.add(line);                    
                }
            }
        }
        catch(IOException e)
        {
        }
    }
}
                    
class Palindromes
{
    static public void main(final String args[]) 
    {
        new Thread(new MyThread("words1.txt")).start();
        new Thread(new MyThread("words2.txt")).start();
    }
}