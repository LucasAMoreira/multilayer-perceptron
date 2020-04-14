
public class ValorEsperado {
	
	private final static double[][] valoresEsperados = {
			
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

	public static double[] getValorEsperado(String valor) {
		
		switch (valor) {
		case "A" : 
			return valoresEsperados[0];
		case "B" : 
			return valoresEsperados[1];
		case "C" : 
			return valoresEsperados[2];
		case "D" : 
			return valoresEsperados[3];
		case "E" : 
			return valoresEsperados[4];
		case "J" : 
			return valoresEsperados[5];
		case "K" : 
			return valoresEsperados[6];
		case "0" : 
			return valoresEsperados[7];
		case "1" : 
			return valoresEsperados[8];
		default : 
			return null;
		}
	}

}
