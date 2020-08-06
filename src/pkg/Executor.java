package pkg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Executor {

	public static final int executionError = -9999;
	
	protected static HashMap<Integer, List<String>> RunCommand(String command) {
		int ret = -1;
		List<String> exceptions = new ArrayList<>();
		EsbmcFuzzer.Log.Write("--------------------------------------------------------------");
		try {
			ProcessBuilder pb = new ProcessBuilder("sh", "-c", command);
			
			pb.redirectErrorStream(true);
			
			Process p = pb.start();
			
			String s;
			BufferedReader br = new BufferedReader(
	                new InputStreamReader(p.getInputStream()));
			
			while ((s = br.readLine()) != null) {
				String temp = s.toLowerCase();
				
				if (temp.contains("exception") ||
						temp.contains("fail") ||
						temp.contains("warning") ||
						temp.contains("abort") ||
						temp.contains("crash") ||
						temp.contains("erro")) {
					exceptions.add(s);
				}
				
				if (s.length() > 0) {
					EsbmcFuzzer.Log.Write(s);
				}

				System.out.println("FUZZER: " + s);
	        }
			
			br.close();
			
			p.waitFor();
	        
	        ret = p.exitValue();
	        
	        p.destroyForcibly();
	        
	    } catch (IOException e) {
			e.printStackTrace();
			ret = executionError;
		} catch (InterruptedException e) {
			e.printStackTrace();
			ret = executionError;
		}
		
		HashMap<Integer, List<String>> result = new HashMap<>();
		result.put(ret, exceptions);
		
		return result;
	}
	
}
