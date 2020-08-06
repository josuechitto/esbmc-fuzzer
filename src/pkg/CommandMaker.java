package pkg;

import java.util.HashMap;
import java.util.List;

import models.CSmithFileCommand;
import models.EsbmcCommand;

public class CommandMaker {

	public static CSmithFileCommand CSmithCommandMaker(HashMap<String, String> parameters, List<String> csmithParameters) {
		
		CSmithFileCommand ret = new CSmithFileCommand();
		ret.setFilePath(parameters.get(Options.csmith_output_path) + "/esbmc-fuzzer-" + System.currentTimeMillis() + ".c ");
		
		String csmithParam = Command.CreateCommandParameters(csmithParameters, parameters.get(Options.csmith_parameters_qty));
		
		StringBuilder cmd = new StringBuilder();
		cmd.append(parameters.get(Options.csmith_path));
		cmd.append(" --output ");
		cmd.append(ret.getFilePath());
		cmd.append(csmithParam);
		
		ret.setCommand(cmd.toString());
		
		return ret;
	}
	
	
	public static EsbmcCommand ESBMCCommandMaker(HashMap<String, String> parameters, List<String> esbmcParameters, String cFile) {
		
		String esbmcParam = Command.CreateCommandParameters(esbmcParameters, parameters.get(Options.esbmc_parameters_qty));
		
		StringBuilder params = new StringBuilder();
		params.append(" --no-unwinding-assertions");
		params.append(" --unwind " + parameters.get(Options.esbmc_loops_qty));
		params.append(esbmcParam);
		
		EsbmcCommand command = new EsbmcCommand();
		command.setParameter(params.toString());
		
		StringBuilder cmd = new StringBuilder();
		cmd.append(parameters.get(Options.esbmc_path));
		cmd.append(params.toString());
		cmd.append(" " + cFile);
		command.setCommand(cmd.toString());
		
		return command;
	}
	
}
