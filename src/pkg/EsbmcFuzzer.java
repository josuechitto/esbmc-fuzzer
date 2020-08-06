package pkg;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;

import models.CSmithFileCommand;
import models.EsbmcCommand;

public class EsbmcFuzzer {

	public static void main(String[] args) {
		
		try {
			
			Options options = new Options();
			HashMap<String, String> parameters = options.ReadFuzzerOptions(args);
			
			List<String> esbmcParameters = Parameter.ReadParameters("src/resources/esbmc-parameters");
			List<String> csmithParameters = Parameter.ReadParameters("src/resources/csmith-parameters");
			
			List<CSmithFileCommand> csmithFileCommands = CSmith.CreateCSmithFiles(parameters, csmithParameters);
			
			List<EsbmcCommand> errors = Esbmc.RunEsbmc(parameters, esbmcParameters, csmithFileCommands);
			
			PrintFuzzingResult(errors);
			
			return;
			
		} catch (FileNotFoundException e) {
			PrintError(e);
		} catch (IllegalArgumentException e) {
			PrintError(e);
		}
		
	}
	
	private static void PrintFuzzingResult(List<EsbmcCommand> errors) {
		
		errors.forEach(error -> {
			System.out.println(".C File Path: " + error.getcFile());
			System.out.println("Esbmc Parameters: " + error.getParameter());
			System.out.println("Esbmc Exit Code: " + error.getError());
			System.out.println("Esbmc Exceptions: ");
			error.getExceptions().forEach(exception -> System.out.println(exception));
		});
		
	}
	
	private static void PrintError(Exception e) {
		
		System.out.println(e.getMessage());
		System.out.println(" ");
		System.out.println("----- ESBMC-FUZZER PARAMETERS -----");
		System.out.println(Options.csmith_files_qty + ": 		value between 1 and 1000. Optional (default 10).");
		System.out.println(Options.csmith_parameters_qty + ": 	value between 1 and 25. Optional (default 10).");
		System.out.println(Options.esbmc_loops_qty + ": 		value between 1 and 1000. Optional (default 10).");
		System.out.println(Options.esbmc_parameters_qty + ": 		value between 1 and 10. Optional (default 10).");
		System.out.println(Options.esbmc_path + ": 			ESBMC executable file. Mandatory.");
		System.out.println(Options.csmith_path + ": 			CSmith executable file. Mandatory.");
		System.out.println(Options.csmith_output_path + ": 		CSmith output path. Mandatory.");
		
	}
	
	
	
}
