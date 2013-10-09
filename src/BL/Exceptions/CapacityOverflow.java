package BL.Exceptions;

@SuppressWarnings("serial")
public class CapacityOverflow extends AbsException {
	
	float leftover;
	
	public CapacityOverflow(String msg, float leftover){
		super(msg);
		this.leftover = leftover;
	}
}
