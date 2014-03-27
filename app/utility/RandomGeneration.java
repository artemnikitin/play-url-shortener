package utility;

import java.util.Random;

public class RandomGeneration {

	public static String randomString(int len){
	   String AB = "abcdefghijklmnopqrstuvxwyzABCDEFGHIJKLMNOPQRSTUWVXYZ1234567890";
	   return randomizer(len, AB);
	}

	private static String randomizer(int len, String AB){
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder(len);
		for( int i = 0; i < len; i++ ) 
		    sb.append( AB.charAt(rnd.nextInt(AB.length())) );
		return sb.toString();
	}
	
}