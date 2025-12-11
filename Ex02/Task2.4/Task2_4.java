import java.util.*;

class ArmstrongSearcher implements Runnable{

	int rangeStart;
	int rangeStop;

	public ArmstrongSearcher(int rangeStart, int rangeStop){
		this.rangeStart = rangeStart;
		this.rangeStop = rangeStop;
	}

	private LinkedList<Integer> getDigits(int number){
		LinkedList<Integer> digits = new LinkedList<Integer>();

		while(number > 0){
			digits.add(number % 10);
			number = number / 10;
		}

		return digits;
	}

	public void run(){
		for(int currentNumber = rangeStart; currentNumber < 
rangeStop; currentNumber++){
			LinkedList<Integer> digits = 
getDigits(currentNumber);
			int m = digits.size();
			int test = 0;
			for(int digit : digits){
				test += Math.pow(digit, m);
			}
			if(test == currentNumber){
				System.out.println(currentNumber);
			}
		}
	}
}

class Task2_4{
	public static void main(String[] args){
		if(args.length < 2){
			System.out.println("Missing arguments.");
			return;
		}
		int max = 0;
		int P = 0;

		try{
			max = Integer.parseInt(args[0]) + 1;
			P = Integer.parseInt(args[1]);
		} catch(NumberFormatException e){
			System.out.println("Invalid input:");
			System.out.println(e);
		}
	
		int chunkSize = max / P;

		for(int i = 0; i < P - 1; i++){
			int start = i * chunkSize;
			int end = (i + 1) * chunkSize;

			Thread t = new Thread(new ArmstrongSearcher(start, 
end));
			t.start();
		}
		int start = (P - 1) * chunkSize;

		Thread t = new Thread(new ArmstrongSearcher(start, max));
		t.start();
	}
}
