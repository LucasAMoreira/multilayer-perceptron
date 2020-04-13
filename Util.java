public class Util {

	private static final String FORMATO_NOTACAO_CIENTIFICA = "%3.2E";
	private static final String FORMATO_DECIMAL = "%.4f";
	private static final String FORMATO_INT = "%02d";
	private static final String NOTACAO_CIENTIFICA = "NS";


	
	public static void printMatriz(String frase, double[][] matrizPesos, String format) {
		System.out.println("\n".concat(frase).concat(":").concat("\n"));
		String formato = format.equals(NOTACAO_CIENTIFICA) ? FORMATO_NOTACAO_CIENTIFICA : FORMATO_DECIMAL;
		String formatAux = format.equalsIgnoreCase(NOTACAO_CIENTIFICA) ? "---------+" :  "--------+";
		String spaceAuxI =  format.equalsIgnoreCase(NOTACAO_CIENTIFICA) ? "     " :  "    ";
		String spaceAuxF =  format.equalsIgnoreCase(NOTACAO_CIENTIFICA) ? "   " :  "   ";
		int numeroColunas = matrizPesos[0].length;
		
		if(numeroColunas <= 16) {
			String line = "│+".concat(formatAux.repeat(numeroColunas)).concat("│");
			String trac = "┌".concat("─".repeat(line.length()-2)).concat("┐");
			System.out.println(trac);
			System.out.print("│");
			for (int y = 0; y < numeroColunas; y++) 
				System.out.print(spaceAuxI.concat(String.format(FORMATO_INT,(y))).concat(spaceAuxF));
			System.out.println(" │");
			System.out.println(line);
			for (int i = 0; i < matrizPesos.length; i++) {
				System.out.print("││");
				for (int y = 0; y < matrizPesos[i].length; y++) {
					String sinal = matrizPesos[i][y] >=0 ? " " : "";
					System.out.print(sinal+String.format(formato, matrizPesos[i][y]+0.0)+ (formato.equalsIgnoreCase(FORMATO_NOTACAO_CIENTIFICA) ? "│" : " |"));
				}
				System.out.println("│");
			}
			System.out.println(line);
			System.out.println("└".concat("─".repeat(line.length()-2)).concat("┘"));
			System.out.println();
		} else {
			String line = "│+".concat(formatAux.repeat(16)).concat("│");
			System.out.println("┌".concat("─".repeat(line.length()-2)).concat("┐"));
			int aux = 0;
			String space = format.equalsIgnoreCase(NOTACAO_CIENTIFICA) ? "           " :  "         ";
			while (aux < numeroColunas)  {
				System.out.print("│");
				int y;	
				for (y = aux; y < aux+16 && y < numeroColunas; y++)
					System.out.print(spaceAuxI.concat(String.format(FORMATO_INT,(y))).concat(spaceAuxF));			
				String sobra = space.repeat(aux+16 - y);
				System.out.println(sobra+" │");
				System.out.println(line);
			
				for (int i = 0; i < matrizPesos.length; i++) {
					System.out.print("││");
					for ( y = aux; y < aux+16 && y < matrizPesos[i].length; y++) {
						String sinal = matrizPesos[i][y] >= 0 ? " " : "";
						System.out.print(sinal+String.format(formato, matrizPesos[i][y]+0)+ (formato.equalsIgnoreCase(FORMATO_NOTACAO_CIENTIFICA) ? "│" : " |"));
				}
				sobra = space.repeat(aux+16 - y);
				System.out.println(sobra+"│");
			}
			System.out.println(line);
			aux = aux+16;
			}
			System.out.println("└".concat("─".repeat(line.length()-2)).concat("┘"));
			System.out.println();
		}
	}
	
	public static void printArray(String frase, String unidade, double[] valores, String formato, int inicio) {
		if (frase != null )
			System.out.println("\n".concat(frase).concat(":").concat("\n"));
		String formatoValor = formato.equals("D") ? FORMATO_DECIMAL : FORMATO_NOTACAO_CIENTIFICA;		
		int aux = inicio;
		while (aux < valores.length) {
			for (int i = aux; i < aux+7 && i < valores.length; i++) 
				System.out.print(unidade+String.format(FORMATO_INT, i)+" = "+(valores[i] > 0 ? " " : "")+ String.format(formatoValor, valores[i])+"   ");
			aux = aux + 7;
			System.out.println();
			}
		System.out.println();
	}
}
