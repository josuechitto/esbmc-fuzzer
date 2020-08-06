package pkg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.CSmithFileCommand;

public class CSmith extends Executor {

	public static List<CSmithFileCommand> CreateCSmithFiles(HashMap<String, String> parameters, List<String> csmithParameters) {
		
		List<CSmithFileCommand> commands = new ArrayList<CSmithFileCommand>();
		
		int size = Integer.parseInt(parameters.get(Options.csmith_files_qty));
		for (int i = 0; i < size; i++) {
			
			System.out.println("CSmith execution: " + (i+1) + "/" + size);
			
			CSmithFileCommand fileCommand = CommandMaker.CSmithCommandMaker(parameters, csmithParameters);
			
			HashMap<Integer, List<String>> result = RunCommand(fileCommand.getCommand());
			
			if (result.size() > 0) {
				Map.Entry<Integer, List<String>> entry = result.entrySet().iterator().next();
				if (entry.getKey() == 0) {
					commands.add(fileCommand);
				}
			}
		}
		
		return commands;
		
	}
	
}
