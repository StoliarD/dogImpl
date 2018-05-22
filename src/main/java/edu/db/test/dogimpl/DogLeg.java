package edu.db.test.dogimpl;

public class DogLeg implements Leg {

	private String name;

	DogLeg(String name) {
		this.name = name;
	}

	@Override
	public void step() {
		System.out.println(name + " stepping");
	}

}
