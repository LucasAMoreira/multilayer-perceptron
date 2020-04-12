
public class MissingExpectValueException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public MissingExpectValueException(String valor) {
		super("O valor esperado não foi denifinido para "+valor);
	}
	
}
