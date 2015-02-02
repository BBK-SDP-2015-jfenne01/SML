package sml;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;



/*
 * The translator of a <b>S</b><b>M</b>al<b>L</b> program.
 */
public class Translator {

	// word + line is the part of the current line that's not yet processed
	// word has no whitespace
	// If word and line are not empty, line begins with whitespace
	private String line = "";
	private Labels labels; // The labels of the program being translated
	private ArrayList<Instruction> program; // The program to be created
	private String fileName; // source file of SML code

	private static final String SRC = "src";

	public Translator(String fileName) {
		this.fileName = SRC + "/" + fileName;
	}

	// translate the small program in the file into lab (the labels) and
	// prog (the program)
	// return "no errors were detected"
	public boolean readAndTranslate(Labels lab, ArrayList<Instruction> prog) {

		try (Scanner sc = new Scanner(new File(fileName))) {
			// Scanner attached to the file chosen by the user
			labels = lab;
			labels.reset();
			program = prog;
			program.clear();

			try {
				line = sc.nextLine();
			} catch (NoSuchElementException ioE) {
				return false;
			}

			// Each iteration processes line and reads the next line into line
			while (line != null) {
				// Store the label in label
				String label = scan();

				if (label.length() > 0) {
					Instruction ins = getInstruction(label);
					if (ins != null) {
						labels.addLabel(label);
						program.add(ins);
					}
				}

				try {
					line = sc.nextLine();
				} catch (NoSuchElementException ioE) {
					return false;
				}
			}
		} catch (IOException ioE) {
			System.out.println("File: IO error " + ioE.getMessage());
			return false;
		}
		return true;
	}

	
	public Instruction getInstruction(String label){			
		//Using the instruction label, create the class name string from it
		String ins = scan();
		String classPrefix= "sml.";
		String classSuffix = "Instruction";
		String fullLabel = classPrefix + ins.substring(0,1).toUpperCase() + ins.substring(1,ins.length()) + classSuffix ;
		
		Instruction instruction = null;		
		try {
			Class<?> targetClass = Class.forName(fullLabel);
			
			//An array of the targetClass's constructors
			Constructor<?>[] constuctors = targetClass.getConstructors();
			
			//The constructor we're after
			//TODO how do I just get the constructor I'm after, just go for the bigger constructor i.e with more params??
			Constructor<?> constructor = constuctors[1];
			
			//The class constructor's parameters
			Parameter [] param = constructor.getParameters();
			
			//Object array to collect arguments for the constructor
			Object[] arguments = new Object[param.length]; 
			
			//First argument is the label of the instruction
			arguments[0] = label;
			
			// Loop to add the rest of the arguments to the array.  
			//TODO needs to be able to handle exceptions e.g for data validation!
			for(int i = 1; i < param.length; i++){
				if(param[i].getType().equals(java.lang.String.class)) 
					arguments[i] = (scan());
				 else if (param[i].getType().equals(int.class)) 
					arguments[i] = (scanInt());
			}
			
			//creates new instance of instruction
			instruction = (Instruction) constructor.newInstance(arguments);

		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return instruction;
			
	}
	

	private String scan() {
		line = line.trim();
		if (line.length() == 0)
			return "";

		int i = 0;
		while (i < line.length() && line.charAt(i) != ' ' && line.charAt(i) != '\t') {
			i = i + 1;
		}
		String word = line.substring(0, i);
		line = line.substring(i);
		return word;
	}

	// Return the first word of line as an integer. If there is
	// any error, return the maximum int
	private int scanInt() {
		String word = scan();
		if (word.length() == 0) {
			return Integer.MAX_VALUE;
		}

		try {
			return Integer.parseInt(word);
		} catch (NumberFormatException e) {
			return Integer.MAX_VALUE;
		}
	}
}