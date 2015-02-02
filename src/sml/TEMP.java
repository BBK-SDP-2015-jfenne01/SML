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
		t.getInstruction("div");
		
	}

}

class TranslatorTest {
	
	public void getInstruction(String label){
		String classPrefix= "sml.";
		String classSuffix = "Instruction";
		String fullLabel = classPrefix + label.substring(0,1).toUpperCase() + label.substring(1,label.length()) + classSuffix ;
		
		Class<?> targetClass = null;
		Constructor<?>[] all = targetClass.getConstructors();
		Constructor<?> constructor = all[1];
		
		
		Instruction instruction = null;
		
		
				
		try {
			targetClass = Class.forName(fullLabel);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		

	}
	
	
}