package sml;

/**
 * This class checks whether a register's value is zero and if not it changes the program counter to load the specified instruction
 * 
 * @author Julian Fenner
 */

public class BnzInstruction extends Instruction {
	private int register;
	private int pc;
	private String nextInstruction;
	
	public BnzInstruction(String label, String opcode) {
		super(label, opcode);
	}
	
	public BnzInstruction(String label, int register, String nextInstruction) {
		super(label, "bnz");
		this.register = register;
		this.nextInstruction = nextInstruction;		
	}
	
	@Override
	public void execute(Machine m) {
		int pc = m.getLabels().indexOf(nextInstruction);
		if(m.getRegisters().getRegister(register) != 0) m.setPc(pc);

	}
	
	@Override
	public String toString() {
		//use reflection here to return label value rather than line no
		return super.toString() + " if register " + register + " is not empty go to line  " + pc;
	}

}
