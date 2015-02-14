package test;

/**
 * 
 * @author Julian Fenner
 */

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sml.BnzInstruction;
import sml.Instruction;
import sml.Machine;
import sml.Registers;

public class BnzInstructionTest {

	Machine testMachine;
	Registers testResisters;

	String bnzLabel;
	String testlabelToJumpTo;
	int register1;
	
	@Before
	public void setUp() throws Exception {
		testMachine = new Machine();
		testResisters = new Registers();
		testMachine.setRegisters(testResisters);
		
		bnzLabel = "f1";
		testlabelToJumpTo = "f2";
		register1 = 1;
		
		
		testMachine.getRegisters().setRegister(register1, 100);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testExecute() {
		Instruction test = new BnzInstruction(bnzLabel, register1, testlabelToJumpTo);
		
		testMachine.getLabels().addLabel(bnzLabel);
		int expected = testMachine.getLabels().addLabel(testlabelToJumpTo);
		
		test.execute(testMachine);
		int output = testMachine.getPc();
		
		assertEquals(expected,output);
	}

}
