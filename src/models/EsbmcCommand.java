package models;

import java.util.List;

public class EsbmcCommand {

	private String cFile;
	private String parameter;
	private String error;
	private String command;
	private List<String> exceptions;
	
	public String getcFile() {
		return cFile;
	}
	public void setcFile(String cFile) {
		this.cFile = cFile;
	}
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public List<String> getExceptions() {
		return exceptions;
	}
	public void setExceptions(List<String> exceptions) {
		this.exceptions = exceptions;
	}
	
	
	
}
