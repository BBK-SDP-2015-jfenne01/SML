package test;

/**
 * 
 * @author Julian Fenner
 */


import static org.junit.Assert.*;
import java.lang.reflect.Field;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import sml.Instruction;
import sml.Labels;
import sml.Translator;


public class TranslatorTest {
	private Labels labels;
	private ArrayList<Instruction> prog;
	
	@Before
	public void setUp() throws Exception {
		labels = new Labels();
		prog = new ArrayList<>();
	}
	
	
	
	@Test
	public void testExecute() {
		Translator testTranslator = new Translator("program1.txt");
		testTranslator.readAndTranslate(labels, prog);
		Field privateField = null;
		ArrayList<Instruction> programPrivateField = null;
		
		try {
			privateField = Translator.class.getDeclaredField("program");
			privateField.setAccessible(true);
			programPrivateField = (ArrayList<Instruction>) privateField.get(testTranslator);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		int expected = 12; // amount of line in program1.txt
		int output = programPrivateField.size();
		assertEquals(expected, output);
	}

}
