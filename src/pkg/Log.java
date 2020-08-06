package pkg;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Log {

	private String filePath;
	private FileWriter fileWriter;
	private File file;
	
	public Log() {
		CreateLogFile();
	}
	
	public void Close() {
		try {
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void Write(String message) {
		try {
			fileWriter.write(message + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void CreateLogFile() {
		try {
			String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
			filePath = "log-" + timeStamp + ".txt";
			
			file = new File(filePath);
			
			if (file.createNewFile()) {
				System.out.println("File created: " + file.getName());
			} else {
				System.out.println("File already exists.");
			}
			
			fileWriter = new FileWriter(filePath);
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
	
}
