package edu.db.test.dogimpl;

public class Main {

	private static final long DURATION = 5000L;

	public static void main(String[] args) throws InterruptedException {
		Dog dog = new Dog(
				new DogLeg("left1"),
				new DogLeg("left2"),
				new DogLeg("right1"),
				new DogLeg("right2"));
		dog.start();
		Thread.sleep(DURATION);
		dog.stop();
	}

}
