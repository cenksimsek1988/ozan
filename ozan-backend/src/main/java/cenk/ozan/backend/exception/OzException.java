package cenk.ozan.backend.exception;

public abstract class OzException extends Exception{
	private static final long serialVersionUID = -2365281008119447247L;
	abstract public int errorCode();
}
