package com.dictionary.api;

enum LogType{
	DEBUG, INFO, NONE
}

public class Logger {
	
	private LogType type = LogType.DEBUG;
	
	private static Logger logger = new Logger();
	
	private Logger(){}
	
	public static Logger getLogger(){
		return logger;
	}
	
	public void setType(LogType type){
		this.type = type;
	}
	
	public void info(String text){
		if(type.equals(LogType.DEBUG) || type.equals(LogType.INFO)){
			this.log(text);
		}
	}
	
	public void debug(String text){
		if(type.equals(LogType.DEBUG)){
			this.log(text);
		}
	}
	
	private void log(String text){
		System.out.println(text);
	}
}
