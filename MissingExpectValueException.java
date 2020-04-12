
public class MissingExpectValueException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public MissingExpectValueException(String valor) {
		super("O valor esperado n�o foi denifinido para "+valor);
	}
	
}
