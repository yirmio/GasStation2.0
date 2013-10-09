package Logs;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Filter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class InstanceFilter implements Filter{

	private Object instance;
	
	public InstanceFilter(Object toFilter){
		this.instance = toFilter;
	}
	
	@Override
	public boolean isLoggable(LogRecord rec) {
		if(rec.getParameters() != null){
			Object temp = rec.getParameters()[0];
			return this.instance == temp;
		}
		return false;
	}
	
	public static void initLog(Object instance, String filefullpath, Logger theLogger) throws SecurityException, IOException{
		//creating instance logger
		FileHandler theFileHandler = new FileHandler(filefullpath, false);
		theFileHandler.setFormatter(new LogFormatter());
		theFileHandler.setFilter(new InstanceFilter(instance));
		theLogger.addHandler(theFileHandler);	
	}
}
