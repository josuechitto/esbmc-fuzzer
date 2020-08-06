package pkg;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;

import models.CSmithFileCommand;
import models.EsbmcCommand;

public class EsbmcFuzzer {

	public static Log Log = new Log();
	
	public static void main(String[] args) {
		
		try {
			
			Options options = new Options();
			HashMap<String, String> parameters = options.ReadFuzzerOptions(args);
			
			List<String> esbmcParameters = Parameter.ReadParameters("src/resources/esbmc-parameters");
			List<String> csmithParameters = Parameter.ReadParameters("src/resources/csmith-parameters");
			
			List<CSmithFileCommand> csmithFileCommands = CSmith.CreateCSmithFiles(parameters, csmithParameters);
			
			List<EsbmcCommand> errors = Esbmc.RunEsbmc(parameters, esbmcParameters, csmithFileCommands);
			
			PrintFuzzingResult(errors);
			
			EsbmcFuzzer.Log.Close();
			
			return;
			
		} catch (FileNotFoundException e) {
			PrintError(e);
		} catch (IllegalArgumentException e) {
			PrintError(e);
		}
		
	}
	
	private static void PrintFuzzingResult(List<EsbmcCommand> errors) {
		
		errors.forEach(error -> {
			
			String ex = "";
			
			for (String e : error.getExceptions()) {
				ex += e + "\n";
			}
			
			EsbmcFuzzer.Log.Write(".C File Path: " + error.getcFile());
			EsbmcFuzzer.Log.Write("Esbmc Parameters: " + error.getParameter());
			EsbmcFuzzer.Log.Write("Esbmc Exit Code: " + error.getError());
			EsbmcFuzzer.Log.Write("Esbmc Exceptions: ");
			EsbmcFuzzer.Log.Write(ex);
			EsbmcFuzzer.Log.Write("------------------------------------------------------------------");
			
			System.out.println(".C File Path: " + error.getcFile());
			System.out.println("Esbmc Parameters: " + error.getParameter());
			System.out.println("Esbmc Exit Code: " + error.getError());
			System.out.println("Esbmc Exceptions: ");
			System.out.println(ex);
			System.out.println("------------------------------------------------------------------");
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
