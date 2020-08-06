package pkg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.CSmithFileCommand;
import models.EsbmcCommand;

public class Esbmc extends Executor {

	public static List<EsbmcCommand> RunEsbmc(HashMap<String, String> parameters, List<String> esbmcParameters, List<CSmithFileCommand> csmithFileCommands) {
		
		List<EsbmcCommand> errors = new ArrayList<>();
		
		for (int i = 0; i < csmithFileCommands.size(); i++) {
			
			System.out.println("Esbmc execution: " + (i+1) + "/" + csmithFileCommands.size());
			
			EsbmcCommand esbmcCmd = CommandMaker.ESBMCCommandMaker(parameters, esbmcParameters, csmithFileCommands.get(i).getFilePath());
			HashMap<Integer, List<String>> esbmcResult = RunCommand(esbmcCmd.getCommand());
			
			if (esbmcResult.size() > 0) {
				
				Map.Entry<Integer, List<String>> result = esbmcResult.entrySet().iterator().next();
				
				if (result.getKey() != 0 && result.getKey() != 1 && result.getKey() != Executor.executionError) {
					esbmcCmd.setError("" + result.getKey());
					esbmcCmd.setcFile(csmithFileCommands.get(i).getFilePath());
					esbmcCmd.setExceptions(result.getValue());
					errors.add(esbmcCmd);
				}	
			}
			
		}
		
		return errors;
	}
	
	
	
}
