package pkg;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Options {
	
	private HashMap<String, String> parameters;
	
	public static final String csmith_files_qty = "-csmith_files_qty";
	public static final String csmith_parameters_qty = "-csmith_parameters_qty";
	public static final String esbmc_parameters_qty = "-esbmc_parameters_qty";
	public static final String esbmc_loops_qty = "-esbmc_loops_qty";
	public static final String esbmc_path = "-esbmc_path";
	public static final String csmith_path = "-csmith_path";
	public static final String csmith_output_path = "-csmith_output_path";
	
	
	public Options() {
		
		parameters = new HashMap<String, String>();
		parameters.put(csmith_files_qty, null);
		parameters.put(csmith_parameters_qty, null);
		parameters.put(esbmc_parameters_qty, null);
		parameters.put(esbmc_loops_qty, null);
		parameters.put(esbmc_path, null);
		parameters.put(csmith_path, null);
		parameters.put(csmith_output_path, null);
	}
	
	public HashMap<String, String> ReadFuzzerOptions(String[] args) throws IllegalArgumentException {
		
		String lastParam = "";
		
		for (int i = 0; i < args.length; i++) {
	        switch (args[i].charAt(0)) {
	        case '-':
	            
	        	if (args[i].length() < 2) {
	                throw new IllegalArgumentException("Not a valid argument: " + args[i]);
	            }
	            
	            if (args[i].charAt(1) == '-') {
	                throw new IllegalArgumentException("Not a valid argument: " + args[i]);
                }
	                
	            if (args.length - 1 == i) {
	            	throw new IllegalArgumentException("Expected arg after: " + args[i]);
	            }
	            
	            if (!parameters.containsKey(args[i])) {
	            	throw new IllegalArgumentException("Not a valid argument: " + args[i]);
	            }
	            
	            lastParam = args[i];
	            
	            break;
	        default:
	            
	        	String value = ValidateParameterValue(lastParam, args[i]);
	        	
	        	parameters.put(lastParam, value);
	        	
	            break;
	        }
	    }
		
		for (Map.Entry<String, String> entry : parameters.entrySet()) {
			
			if ((entry.getKey() == esbmc_path || 
				entry.getKey() == csmith_path ||
				entry.getKey() == csmith_output_path) &&
					entry.getValue() == null) {
					
				throw new IllegalArgumentException("Some mandatory argument is missing!");
					
			} else if (entry.getValue() == null) {
				entry.setValue("10");
			}
		}
		
		return parameters;
	}
	
	
	private String ValidateParameterValue(String param, String paramValue) throws IllegalArgumentException {
		
		int value;
		
		switch (param) {
		case csmith_files_qty:
			
			value = CheckIntegerValue(param, paramValue);
			if (value >= 1 && value <= 1000) {
				return paramValue;
			} else {
				throw new IllegalArgumentException("Invalid value to csmith_files_qty. Value must be between 1 and 1000");
			}
			//break;
		case csmith_parameters_qty:
			
			value = CheckIntegerValue(param, paramValue);
			if (value >= 1 && value <= 25) {
				return paramValue;
			} else {
				throw new IllegalArgumentException("Invalid value to csmith_parameters_qty. Value must be between 1 and 25");
			}
			//break;
		case esbmc_parameters_qty:
			
			value = CheckIntegerValue(param, paramValue);
			if (value >= 1 && value <= 50) {
				return paramValue;
			} else {
				throw new IllegalArgumentException("Invalid value to esbmc_parameters_qty. Value must be between 1 and 10");
			}
			//break;
		case esbmc_loops_qty:
			
			value = CheckIntegerValue(param, paramValue);
			if (value >= 1 && value <= 1000) {
				return paramValue;
			} else {
				throw new IllegalArgumentException("Invalid value to esbmc_loops_qty. Value must be between 1 and 1000");
			}
			//break;
		case esbmc_path:
			File fileEsbmc = new File(paramValue);
			if (fileEsbmc.exists()) {
				return paramValue;
			} else {
				throw new IllegalArgumentException("The path " + paramValue + " is invalid to ESBMC!");
			}
			//break;
		case csmith_path:
			File fileCsmith = new File(paramValue);
			if (fileCsmith.exists()) {
				return paramValue;
			} else {
				throw new IllegalArgumentException("The path " + paramValue + " is invalid to CSmith!");
			}
			//break;
		case csmith_output_path:
			if (Files.isDirectory(Paths.get(paramValue))) {
				return paramValue;
			} else {
				throw new IllegalArgumentException("The path " + paramValue + " is invalid to CSmith output!");
			}
			//break;
		default:
			throw new IllegalArgumentException("Not a valid argument: " + param);
			
		}
	}
	
	
	private int CheckIntegerValue(String param, String sValue) throws NumberFormatException {
		
		try {
			return Integer.parseInt(sValue);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Invalid value to " + param + ". Value must be an integer");
		}
	}
	
}
