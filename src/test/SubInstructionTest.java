package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;



import sml.Machine;
import sml.Registers;
import sml.SubInstruction;

public class SubInstructionTest {

	Machine testMachine;
	Registers testResisters;

	String label;
	int resultRegister;
	int op1;
	int op2;
	
	int value1;
	int value2;	
	
	@Before
	public void setUp() throws Exception {
		testMachine = new Machine();
		testResisters = new Registers();
		testMachine.setRegisters(testResisters);
		label = "f1";
		resultRegister = 0;
		op1 = 1;
		op2 = 2;
		
		value1 = 3;
		value2 = 2;
		
		testMachine.getRegisters().setRegister(op1, value1);
		testMachine.getRegisters().setRegister(op2, value2);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testExecute() {
		SubInstruction test = new SubInstruction(label, resultRegister, op1, op2);
		test.execute(testMachine);
		int expected = value1 - value2;
		int output = testMachine.registers.getRegister(resultRegister);
		assertEquals(expected, output);
	}

}
