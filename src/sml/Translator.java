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

	
	
	/*
	 * Version of getInstruction using reflection
	 */
	public Instruction getInstruction(String label) {
		String ins = scan();
		String classPrefix= "sml.";
		String classSuffix = "Instruction";
		String fullLabel = classPrefix + ins.substring(0,1).toUpperCase() + label.substring(1,label.length()) + classSuffix ;
		
		
		Class<?> c = null;
		Object obj = null;
		Constructor<?> cons = null;
		
		try {
			c = Class.forName(fullLabel);
			//obj = c.newInstance();
		
			Constructor<?>[] a = c.getConstructors();
			//THIS NEEDS CHANGING, ASSUMES THAT CORRECT CONSTUCTOR IS ALWAYS SECOND ONE
			Parameter [] param = a[1].getParameters();
			
			//get correct constructor.  All constructors start with a string  args and have between 1 and 4 int args
			if(param.length == 2){
				cons = c.getConstructor(new Class[]{String.class, int.class});
				int arg1 = scanInt();
				obj = cons.newInstance(new Object[] { label, arg1});
			} else if(param.length == 3){
				cons = c.getConstructor(new Class[]{String.class, int.class, int.class});
				int arg1 = scanInt();
				int arg2 = scanInt();
				obj = cons.newInstance(new Object[] { label, arg1, arg2});
				
			} else if(param.length == 4){
				cons = c.getConstructor(new Class[]{String.class, int.class, int.class, int.class});
				int arg1 = scanInt();
				int arg2 = scanInt();
				int arg3 = scanInt();
				obj = cons.newInstance(new Object[] { label, arg1, arg2, arg3});
			} else{
				cons = c.getConstructor(new Class[]{String.class, int.class, int.class, int.class, int.class});
				int arg1 = scanInt();
				int arg2 = scanInt();
				int arg3 = scanInt();
				int arg4 = scanInt();
				obj = cons.newInstance(new Object[] {label, arg1, arg2, arg3, arg4});
			}
			
			
			
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return (Instruction) obj;
		
		
	}	
	
	
	
	
    //Switch case version of of getInstruction
	// line should consist of an MML instruction, with its label already
	// removed. Translate line into an instruction with label label
	// and return the instruction
//	public Instruction getInstruction(String label) {
//	
//		int s1; // Possible operands of the instruction
//		int s2;
//		int r;
//		int x;
//
//		if (line.equals(""))
//			return null;
//
//		String ins = scan();
//		switch (ins) {
//		case "add":
//			r = scanInt();
//			s1 = scanInt();
//			s2 = scanInt();
//			return new AddInstruction(label, r, s1, s2);
//		case "lin":
//			r = scanInt();
//			s1 = scanInt();
//			return new LinInstruction(label, r, s1);
//		case "sub":
//			r = scanInt();
//			s1 = scanInt();
//			s2 = scanInt();
//			return new SubInstruction(label, r, s1, s2);
//		case "mul":
//			r = scanInt();
//			s1 = scanInt();
//			s2 = scanInt();
//			return new MulInstruction(label, r, s1, s2);
//		case "div":
//			r = scanInt();
//			s1 = scanInt();
//			s2 = scanInt();
//			return new DivInstruction(label, r, s1, s2);
//		case "out":
//			r = scanInt();
//			s1 = scanInt();
//			s2 = scanInt();
//			return new OutInstruction(label, r);
//		case "bnz":
//			//register to check
//			r = scanInt();
//			//x is register to jump to, as its a label need to obtain index which represents the pc to jump to
//			String labelTemp = scan();
//			s1 = labels.indexOf(labelTemp);
//			return new BnzInstruction(label, r, s1);	
//		}
//		return null;
//	}

	/*
	 * Return the first word of line and remove it from line. If there is no
	 * word, return ""
	 */
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