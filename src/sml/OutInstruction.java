package sml;

/**
 * This class prints the value of a register
 * 
 * @author Julian Fenner
 */


public class OutInstruction extends Instruction {

	private int op1;
	
	public OutInstruction(String l, String op) {
		super(l, op);
		// TODO Auto-generated constructor stub
	}

	public OutInstruction(String label, int op1) {
		this(label, "out");
		this.op1 = op1;
	}
	
	@Override
	public void execute(Machine m) {
		int regVal = m.getRegisters().getRegister(op1);
		System.out.println("Register " + op1 + " contents is " + regVal);
	}
	
	@Override
	public String toString() {
		return super.toString() + " print register " + op1; 
	}
}
