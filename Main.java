import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;

public class Main{
	
	private final static double MAIOR_TAXA_APRENDIZADO = 1.05;
	
	private static List<String> lstErros = new LinkedList<String>();
	private static Entrada entrada  = new Entrada();

	public static void main(String[]args) throws IOException{
				
		
		Modo modo = Modo.TREINAMENTO;
		
		double taxaAprenzidado = 0.0;
		
		String arquivo = "caracteres-ruido.csv";
		
		int periodos = 5000;
		
		int neuroniosCEs = 0;
		
		double erroEpoca = 1;
		
		File file = new File("resources//".concat(arquivo.substring(0, arquivo.length()-3)).concat("erro.txt"));
		
		BufferedWriter w = new BufferedWriter(new FileWriter(file, true));
		
		if (modo == Modo.TREINAMENTO) { 
			
			PrintStream fileStream = new PrintStream("resources//".concat(arquivo.substring(0, arquivo.length()-4)).concat("-saida.txt"));
			System.setOut(fileStream);
			
			System.out.println("-".repeat(150));
			System.out.println("\nSOURCE: ".concat(arquivo).concat("\n"));
			
		}
		
		while (taxaAprenzidado < MAIOR_TAXA_APRENDIZADO) {
			

						
			List<ParametrosEntrada> lstPE = entrada.prepara("resources//".concat(arquivo), periodos, neuroniosCEs, taxaAprenzidado);
			
			int epoca = 0;
			
			if (taxaAprenzidado == 0.0) {
				for (ParametrosEntrada parametrosEntrada : lstPE) {
					System.out.println("ENTRADA: "+parametrosEntrada.getValor().concat("\n"));
					Output.printValores(null, "x", parametrosEntrada.getCamadaEntrada(), "f", 1);
					Output.printValores("SAÍDA ESPERADA", "y", parametrosEntrada.getSaidaEsperada(), "f", 0);


				}
			}
			
			System.out.println("-".repeat(150));
			System.out.println("\nTAXA APRENDIZADO: ".concat(String.valueOf(taxaAprenzidado)));
			Output.printPesos("PESOS INICIAIS DA CAMA DE ENTRADA", lstPE.get(0).getPesosCEnInicial(), "");
			Output.printPesos("PESOS INICIAIS DA CAMA ESCONDIDA", lstPE.get(0).getPesosCEsInicial(), "");
			
			
			while (epoca < lstPE.get(0).getEpocas()) {
				
				for (int i = 0; i < lstPE.size(); i++) {
					
					MultilayerPerceptron mp = new MultilayerPerceptron(lstPE.get(i), modo);
					mp.processarRede();				
					
					if (modo == Modo.APRESENTACAO || epoca == lstPE.get(0).getEpocas()-1) {
						
						if (lstPE.indexOf(lstPE.get(i)) == 0 ) {
							System.out.println("\nEPOCA: "+(epoca+1)+"\n");
						}
						System.out.println("ENTRADA: "+lstPE.get(i).getValor());
					}
					
					if (!(modo == Modo.APRESENTACAO) && epoca == lstPE.get(0).getEpocas()-1) {
						Output.printValores(" > SAÍDA DA REDE", "y", mp.getSaidaRede(), "f", 0);

					}
					
					if (modo == Modo.APRESENTACAO) {
						Output.printValores("CAMADA ENTRADA", "x", lstPE.get(i).getCamadaEntrada(), "f", 0);
					}
					
					if (i == lstPE.size()-1) {
						erroEpoca = (mp.getErro()/2) / (lstPE.size());
						lstErros.add(taxaAprenzidado+","+(epoca+1)+","+erroEpoca);
						mp.zeraErro();
					}
					
					if (modo == Modo.APRESENTACAO || (epoca == lstPE.get(0).getEpocas()-1 && (lstPE.indexOf(lstPE.get(i)) == lstPE.size() - 1))) {

						System.out.println("\nERRO DA REDE: "+(erroEpoca)+"\n");
						Output.printPesos("NOVOS PESOS DA CAMADA DE ENTRADA", lstPE.get(i).getPesosCEn(), "i");
						Output.printPesos("NOVOS PESOS DA CAMADA ESCONDIDA", lstPE.get(i).getPesosCEs(), "i");


					}
			}
				
				epoca++;

			}

			taxaAprenzidado = taxaAprenzidado+0.05;
		}
		
		for (String erro : lstErros) {
			w.write(erro);
			w.newLine();
		}
		
		w.close();

			
		}

}
