package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sml.LinInstruction;
import sml.Machine;

public class TestLinInstuction {
	Machine testMachine;
	String label;
	int register;
	int value;
		
	
	@Before
	public void setUp() throws Exception {
		testMachine = new Machine();	
		label = "f1"; 
		register = 1;
		value = 5;
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
//		NEED TO TEST THIS CLASS DIRECTLY
//		LinInstruction test = new LinInstruction(label, register, value);
//		test.execute(testMachine);
//		//int x = testMachine.registers.getRegister(register);
//		assertEquals(1, 1 );
		
	}

}
