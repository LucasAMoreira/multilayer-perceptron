import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

public class Output {

	public static void printPesos(double[][] matrizPesos, String format) {
		
		String formato = format.equals("SN") ? "%8.3E" : "%.4f";
		
		String formatAux = format.equalsIgnoreCase("SN") ? "----------+" :  "--------+";
		String spaceAuxI =  format.equalsIgnoreCase("SN") ? "     " :  "    ";
		String spaceAuxF =  format.equalsIgnoreCase("SN") ? "    " :  "   ";

		int numeroColunas = matrizPesos[0].length;
		
		if(numeroColunas <= 16) {
						
			
			String line = "│+".concat(formatAux.repeat(numeroColunas)).concat("│");
			String trac = "┌".concat("─".repeat(line.length()-2)).concat("┐");
			
			System.out.println(trac);
			
			System.out.print("│");
			for (int y = 0; y < numeroColunas; y++) 
				System.out.print(spaceAuxI.concat(String.format("%02d",(y))).concat(spaceAuxF));
			
			System.out.println(" │");
			System.out.println(line);
			
			for (int i = 0; i < matrizPesos.length; i++) {
				System.out.print("││");
				for (int y = 0; y < matrizPesos[i].length; y++) {
					
					String sinal = matrizPesos[i][y] >= 0 ? " " : "";
					
					System.out.print(sinal+String.format(formato, matrizPesos[i][y])+ (formato.equalsIgnoreCase("%8.3E") ? "│" : " |"));
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
			
			
			String space = format.equalsIgnoreCase("SN") ? "           " :  "         ";

			while (aux < numeroColunas)  {
				
				System.out.print("│");
			int y;	
				for (y = aux; y < aux+16 && y < numeroColunas; y++)
					System.out.print(spaceAuxI.concat(String.format("%02d",(y))).concat(spaceAuxF));			
				String sobra = space.repeat(aux+16 - y);
				System.out.println(sobra+" │");
				System.out.println(line);
			
			for (int i = 0; i < matrizPesos.length; i++) {
				System.out.print("││");
				for ( y = aux; y < aux+16 && y < matrizPesos[i].length; y++) {
					String sinal = matrizPesos[i][y] >= 0 ? " " : "";

					System.out.print(sinal+String.format(formato, matrizPesos[i][y])+ (formato.equalsIgnoreCase("%8.3E") ? "│" : " |"));
										
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
	
	public static void printValores(String unidade, double[] valores, String formato, int inicio) {
		
		String formatoValor = formato.equals("f") ? "%.4f" : "%8.2E";		
		
		int aux = inicio;
		while (aux < valores.length) {
			for (int i = aux; i < aux+4 && i < valores.length; i++) 
				System.out.print(unidade+String.format("%02d", i)+" = "+(valores[i] > 0 ? " " : "")+ String.format(formatoValor, valores[i])+"   ");
			aux = aux + 4;
			System.out.println();
			}
		System.out.println();
	}
	
	
	
	public void printNeuronios(int size) {
		System.out.print("  bias  ");
		int aux = 0;
		for(int i = 0; i < size-1; i++) {
			System.out.print(" n"+String.format("%03d",(i+1))+"  ");
			aux++;
			if (aux > 7) {System.out.println(); System.out.print(" "); aux=0;};
		}
		System.out.println();


	}
	
	public void printLine(int size) {
		System.out.print("+");
		for(int i = 0; i < size; i++) {
			System.out.print("------+");
			if (i > 6) break;
		}
		System.out.println();
	}
}
