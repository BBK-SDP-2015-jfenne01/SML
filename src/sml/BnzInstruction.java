package sml;
/*
 * If the contents of register s1 is not zero, then make the statement labeled L2 the next one to execute
 */

public class BnzInstruction extends Instruction {
	private int register;
	private int pc;
	
	public BnzInstruction(String label, String opcode) {
		super(label, opcode);
	}
	
	public BnzInstruction(String label, int register, int pc) {
		super(label, "bnz");
		this.register = register;
		this.pc = pc;

	}
	
	@Override
	public void execute(Machine m) {
		if(m.getRegisters().getRegister(register) != 0) m.setPc(pc);

	}
	
	@Override
	public String toString() {
		//use reflection here to return label value rather than line no
		return super.toString() + " if register " + register + " is not empty go to line  " + pc;
	}

}
