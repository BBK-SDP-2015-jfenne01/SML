package test;

/**
 * 
 * @author Julian Fenner
 */


import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;



import sml.Instruction;
import sml.LinInstruction;
import sml.Machine;
import sml.Registers;

public class LinInstuctionTest {
	Machine testMachine;
	Registers testResisters;
	String label;
	
	int register;
	int value;
		
	
	@Before
	public void setUp() throws Exception {
		testMachine = new Machine();
		testResisters = new Registers();
		testMachine.setRegisters(testResisters);
		label = "f1"; 
		register = 7;
		value = 5;
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testExecute() {
		LinInstruction test = new LinInstruction(label, register, value);
		test.execute(testMachine);
        int expectedValue = testMachine.registers.getRegister(register);
		assertEquals(value, expectedValue );
		
	}
	
	@Test
	public void testToString(){
		Instruction test = new LinInstruction(label, register, value);
		String output = test.toString();
		String expected = "f1: lin register " + register + " value is " + value;
		assertEquals(output,expected);
	}

}
