package com.yaksha.assignment;

// Interface representing an ability to speak
interface Speakable {
	void speak(); // Method to speak
}

// Interface representing an ability to run
interface Runnable {
	void run(); // Method to run
}

// Animal class - Base class for polymorphism demonstration
class Animal implements Speakable, Runnable {

	@Override
	public void speak() {
		System.out.println("The animal makes a sound.");
	}

	@Override
	public void run() {
		System.out.println("The animal runs.");
	}
}

// Dog class - Inherits from Animal and implements Speakable and Runnable
class Dog implements Speakable, Runnable {

	@Override
	public void speak() {
		System.out.println("The dog barks.");
	}

	@Override
	public void run() {
		System.out.println("The dog runs fast.");
	}
}

// Cat class - Implements Speakable and Runnable
class Cat implements Speakable, Runnable {

	@Override
	public void speak() {
		System.out.println("The cat meows.");
	}

	@Override
	public void run() {
		System.out.println("The cat runs gracefully.");
	}
}

public class PolymorphismWithInterfacesAssignment {
	public static void main(String[] args) {
		Speakable animal = new Animal(); // Using the Speakable interface to reference an Animal object
		animal.speak(); // Should print "The animal makes a sound."

		Runnable dog = new Dog(); // Using the Runnable interface to reference a Dog object
		dog.run(); // Should print "The dog runs fast."

		Speakable cat = new Cat(); // Using the Speakable interface to reference a Cat object
		cat.speak(); // Should print "The cat meows."

		Runnable animalRun = new Animal(); // Using Runnable interface for Animal object
		animalRun.run(); // Should print "The animal runs."
	}
}
