package pkg;

import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class Command {
	
	public static String CreateCommandParameters(List<String> parameters, String qtyParameters) {
		
		int qty = Integer.parseInt(qtyParameters);
		
		Random rParam = new Random();
		
		HashSet<Integer> addedParameters = new HashSet<Integer>(); 
		String cmd = "";
		for (int i = 0; i < qty; i++) {
			int index = -1;
			
			while (true) {
				index = rParam.nextInt(parameters.size());
				
				if (!addedParameters.contains(index)) {
					addedParameters.add(index);
					break;
				}
				
				if (parameters.size() == addedParameters.size()) {
					index = -1;
					break;
				}
			}
			
			if (index > 0) {
				cmd += " --" + parameters.get(index);
			}
		}
		
		return cmd;
	}
	
	
	
	
}
