package BL.Exceptions;

@SuppressWarnings("serial")
public class NoFuelLeft extends AbsException {
	
	private float leftover;
	
	public NoFuelLeft(String msg, float leftover){
		super(msg);
		this.leftover = leftover;
	}
	
	public float getLeftover(){
		return this.leftover;
	}
}
