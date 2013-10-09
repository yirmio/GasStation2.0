package BL.Exceptions;

@SuppressWarnings("serial")
public abstract class AbsException extends Exception {
	/* Abstract exception class for the all project 
	 * all exceptions will inherit from this class
	 */
	public AbsException(String msg){
		super(msg);
	}
}
