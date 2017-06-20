package encryption_project;

import java.util.Random;

public class Main {
	


	public static void main (String[] args){
		int[] deck = new int[3];
		deck[0] = 23;
		deck[1] = 12;
		deck[2] = 35;
		System.out.println(deck[0]);
		
		Random random = new Random();
		System.out.print(random.nextInt(50) + 1);
	}
	

	


	
}
