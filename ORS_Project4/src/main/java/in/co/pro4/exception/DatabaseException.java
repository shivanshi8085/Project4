package in.co.pro4.exception;

/**
 * DatabaseException is propogated by DAO classes when an unhandled Database exception occurred.
 * @author Shivanshi Gupta
 *
 */
public class DatabaseException extends Exception {

	public DatabaseException(String msg) {
		super(msg);
	}
}
