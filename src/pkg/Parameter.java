package pkg;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Parameter {
	
	public static List<String> ReadParameters(String tool) throws FileNotFoundException {
		
		File file = new File(tool);
		Scanner scanner = new Scanner(file);
		
		List<String> parameters = new ArrayList<String>();
		while (scanner.hasNext()) {
			String parameter = scanner.next();
			parameters.add(parameter);
		}
		
		scanner.close();
		
		return parameters;
	}
	
}
