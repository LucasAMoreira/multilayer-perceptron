
public class ExpectedValue {

	private final static double[][] expectedValue = {
			
			{1, 0, 0, 0, 0, 0, 0},
			{0, 1, 0, 0, 0, 0, 0},
			{0, 0, 1, 0, 0, 0, 0},
			{0, 0, 0, 1, 0, 0, 0},
			{0, 0, 0, 0, 1, 0, 0},
			{0, 0, 0, 0, 0, 1, 0},
			{0, 0, 0, 0, 0, 0, 1},
			{0},
			{1},
	};
	
	public static double[] getExpectedValue(String value) throws MissingExpectValueException  {
		
		switch (value) {
		case "A" : 
			return expectedValue[0];
		case "B" : 
			return expectedValue[1];
		case "C" : 
			return expectedValue[2];
		case "D" : 
			return expectedValue[3];
		case "E" : 
			return expectedValue[4];
		case "J" : 
			return expectedValue[5];
		case "K" : 
			return expectedValue[6];
		case "0" : 
			return expectedValue[7];
		case "1" : 
			return expectedValue[8];
		default :
			throw new MissingExpectValueException(value);
		}		
	}
	
}
