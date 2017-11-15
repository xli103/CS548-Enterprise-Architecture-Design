package test;

import java.util.Random;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Random generator = new Random();
		float amount = generator.nextFloat() * 500;
		System.out.println(amount);
	}

}
