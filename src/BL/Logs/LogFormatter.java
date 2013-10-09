package BL.Logs;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;


public class LogFormatter extends Formatter {

	@Override
	public String format(LogRecord rec) {
		StringBuffer buf = new StringBuffer(1500);
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		buf.append("[" + dateFormat.format(date) + "] ");
		buf.append("[" + rec.getLevel() + "]\t");
		buf.append(formatMessage(rec) + "\r\n");
		return buf.toString();
	}

}
