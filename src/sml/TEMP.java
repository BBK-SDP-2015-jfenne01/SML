package sml;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.ArrayList;

public class TEMP {

	public static void main(String[] args) {
		new TEMP().launch();

	}
	
	private void launch(){
		TranslatorTest t = new TranslatorTest();
		System.out.println(t.getInstruction("div"));
		
	}

}

class TranslatorTest {
	
	public Instruction getInstruction(String label){
		String classPrefix= "sml.";
		String classSuffix = "Instruction";
		String fullLabel = classPrefix + label.substring(0,1).toUpperCase() + label.substring(1,label.length()) + classSuffix ;
		
		
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
				obj = cons.newInstance(new Object[] { label, 1});
			} else if(param.length == 3){
				cons = c.getConstructor(new Class[]{String.class, int.class, int.class});
				obj = cons.newInstance(new Object[] { label, 1, 1});
			} else if(param.length == 4){
				cons = c.getConstructor(new Class[]{String.class, int.class, int.class, int.class});
				obj = cons.newInstance(new Object[] { label, 1, 1, 1});
			} else{
				cons = c.getConstructor(new Class[]{String.class, int.class, int.class, int.class, int.class});
				obj = cons.newInstance(new Object[] { label, 1, 1, 1, 1});
			}
			
			
			
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return (Instruction) obj;
	}
}

