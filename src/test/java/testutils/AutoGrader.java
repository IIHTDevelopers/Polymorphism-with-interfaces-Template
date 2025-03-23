package testutils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;

public class AutoGrader {

	// Test if the code implements polymorphism with interfaces correctly
	public boolean testPolymorphismWithInterfaces(String filePath) throws IOException {
		System.out.println("Starting testPolymorphismWithInterfaces with file: " + filePath);

		File participantFile = new File(filePath); // Path to participant's file
		if (!participantFile.exists()) {
			System.out.println("File does not exist at path: " + filePath);
			return false;
		}

		FileInputStream fileInputStream = new FileInputStream(participantFile);
		JavaParser javaParser = new JavaParser();
		CompilationUnit cu;
		try {
			cu = javaParser.parse(fileInputStream).getResult()
					.orElseThrow(() -> new IOException("Failed to parse the Java file"));
		} catch (IOException e) {
			System.out.println("Error parsing the file: " + e.getMessage());
			throw e;
		}

		System.out.println("Parsed the Java file successfully.");

		// Use AtomicBoolean to allow modifications inside lambda expressions
		AtomicBoolean animalClassFound = new AtomicBoolean(false);
		AtomicBoolean dogClassFound = new AtomicBoolean(false);
		AtomicBoolean catClassFound = new AtomicBoolean(false);
		AtomicBoolean speakableInterfaceFound = new AtomicBoolean(false);
		AtomicBoolean runnableInterfaceFound = new AtomicBoolean(false);
		AtomicBoolean animalImplementsSpeakable = new AtomicBoolean(false);
		AtomicBoolean animalImplementsRunnable = new AtomicBoolean(false);
		AtomicBoolean dogImplementsSpeakable = new AtomicBoolean(false);
		AtomicBoolean dogImplementsRunnable = new AtomicBoolean(false);
		AtomicBoolean catImplementsSpeakable = new AtomicBoolean(false);
		AtomicBoolean catImplementsRunnable = new AtomicBoolean(false);
		AtomicBoolean methodsExecutedInMain = new AtomicBoolean(false);

		// Check for class implementation and interface implementation
		System.out.println("------ Class and Interface Check ------");
		for (TypeDeclaration<?> typeDecl : cu.findAll(TypeDeclaration.class)) {
			if (typeDecl instanceof ClassOrInterfaceDeclaration) {
				ClassOrInterfaceDeclaration classDecl = (ClassOrInterfaceDeclaration) typeDecl;

				if (classDecl.getNameAsString().equals("Animal")) {
					System.out.println("Class 'Animal' found.");
					animalClassFound.set(true);
					// Check if Animal implements Speakable and Runnable
					classDecl.getImplementedTypes().forEach(impl -> {
						if (impl.getNameAsString().equals("Speakable")) {
							animalImplementsSpeakable.set(true);
							System.out.println("Animal class implements 'Speakable'.");
						}
						if (impl.getNameAsString().equals("Runnable")) {
							animalImplementsRunnable.set(true);
							System.out.println("Animal class implements 'Runnable'.");
						}
					});
				}

				if (classDecl.getNameAsString().equals("Dog")) {
					System.out.println("Class 'Dog' found.");
					dogClassFound.set(true);
					// Check if Dog implements Speakable and Runnable
					classDecl.getImplementedTypes().forEach(impl -> {
						if (impl.getNameAsString().equals("Speakable")) {
							dogImplementsSpeakable.set(true);
							System.out.println("Dog class implements 'Speakable'.");
						}
						if (impl.getNameAsString().equals("Runnable")) {
							dogImplementsRunnable.set(true);
							System.out.println("Dog class implements 'Runnable'.");
						}
					});
				}

				if (classDecl.getNameAsString().equals("Cat")) {
					System.out.println("Class 'Cat' found.");
					catClassFound.set(true);
					// Check if Cat implements Speakable and Runnable
					classDecl.getImplementedTypes().forEach(impl -> {
						if (impl.getNameAsString().equals("Speakable")) {
							catImplementsSpeakable.set(true);
							System.out.println("Cat class implements 'Speakable'.");
						}
						if (impl.getNameAsString().equals("Runnable")) {
							catImplementsRunnable.set(true);
							System.out.println("Cat class implements 'Runnable'.");
						}
					});
				}

				if (classDecl.getNameAsString().equals("Speakable")) {
					System.out.println("Interface 'Speakable' found.");
					speakableInterfaceFound.set(true);
				}

				if (classDecl.getNameAsString().equals("Runnable")) {
					System.out.println("Interface 'Runnable' found.");
					runnableInterfaceFound.set(true);
				}
			}
		}

		// Ensure all classes and interfaces exist
		if (!animalClassFound.get() || !dogClassFound.get() || !catClassFound.get() || !speakableInterfaceFound.get()
				|| !runnableInterfaceFound.get()) {
			System.out.println(
					"Error: One or more classes (Animal, Dog, Cat) or interfaces (Speakable, Runnable) are missing.");
			return false;
		}

		// Check if the classes implement the interfaces correctly
		System.out.println("------ Interface Implementation Check ------");

		if (!animalImplementsSpeakable.get()) {
			System.out.println("Error: 'Animal' class does not implement 'Speakable'.");
			return false;
		}
		if (!animalImplementsRunnable.get()) {
			System.out.println("Error: 'Animal' class does not implement 'Runnable'.");
			return false;
		}

		if (!dogImplementsSpeakable.get()) {
			System.out.println("Error: 'Dog' class does not implement 'Speakable'.");
			return false;
		}
		if (!dogImplementsRunnable.get()) {
			System.out.println("Error: 'Dog' class does not implement 'Runnable'.");
			return false;
		}

		if (!catImplementsSpeakable.get()) {
			System.out.println("Error: 'Cat' class does not implement 'Speakable'.");
			return false;
		}
		if (!catImplementsRunnable.get()) {
			System.out.println("Error: 'Cat' class does not implement 'Runnable'.");
			return false;
		}

		// Check if methods are executed in the main method
		System.out.println("------ Method Execution Check in Main ------");
		for (MethodDeclaration method : cu.findAll(MethodDeclaration.class)) {
			if (method.getNameAsString().equals("main")) {
				if (method.getBody().isPresent()) {
					method.getBody().get().findAll(MethodCallExpr.class).forEach(callExpr -> {
						if (callExpr.getNameAsString().equals("speak") || callExpr.getNameAsString().equals("run")) {
							methodsExecutedInMain.set(true);
							System.out.println("Methods 'speak' or 'run' are executed in the main method.");
						}
					});
				}
			}
		}

		if (!methodsExecutedInMain.get()) {
			System.out.println("Error: 'speak' or 'run' method not executed in the main method.");
			return false;
		}

		// If interface implementation and methods execution in main are correct
		System.out.println("Test passed: Polymorphism with interfaces is correctly implemented.");
		return true;
	}
}
