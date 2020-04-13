import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;

public class Main{
	
	private final static double MAIOR_TAXA_APRENDIZADO = 0.01;
	private final static double MENOR_TAXA_APRENDIZADO = 0.00;
	
	private static List<String> lstErros = new LinkedList<String>();
	private static Entrada entrada;
	private static BufferedWriter w ;
	private static PrintStream fileStream;

	public static void main(String[]args) throws IOException{
				
		
		/*
		 * O método é responsável por enviar os conjuntos de treinamento para serem processados pelo multilayer perceptor  
		 * 1. É necessário definir o valor inicial da taxa de aprendizado e a quantidade de períodos. 
		 * 2. Quando o conjunto de dados estiver passado pelo treinamento, será incrementado 0.05 ao valor da taxa de aprendizado e o conjunto irá para treinamento novamente.
		 * 3. O passo 2. será repetido até atingir o maior valor de taxa, 1.00.
		 * 
		 * Os itens que contém o comentário //EXIBIÇÃO são coisas referentes a criação do arquivo e exibição de valores **Está bem feio, porém é super necessário. 
		 * 
		 * O erro do período também é calculado dentro do método main. 
		 */
		
		/*
		 * O código pode ser rodado no Modo TREINAMENTO ou APRESENTACAO, o modo APRESENTACAO exibirá o resultado de caso passo no console, não é indicado, pois torna o processo
		 * mais lento e poluído, a opção existe apenas para que o video que será criado, já o método TREINAMENTO não exibirá os resultados no console, porém criará os arquivos
		 *  saida, contendo informações essenciais e erro, contendo os erros de cada período.
		 *  Os arquivos de saída podem ser 
		 */
		Modo modo = Modo.APRESENTACAO;
		String arquivo = "caracteres-ruido.csv"; //O nome do arquivo, que deverá estar na pasta resources.
		int periodos = 1;					 //A quantidade de períodos
		int neuroniosCamadaEscondida = 0;		 //Se o valor for 0, será utilizada a média geométrica para definir o número de neurônios.
		double taxaAprenzidado = 0.0;			 //A taxa de aprendizado inicial.
		
		double erroEpoca = 0;
				
		//Se o modo for TREINAMENTO, irá gerar os arquivos de saída;
		if (modo == Modo.TREINAMENTO) { 
			
			File file = new File("resources//".concat(arquivo.substring(0, arquivo.length()-3)).concat("erro.txt"));
			w = new BufferedWriter(new FileWriter(file, true));
			
			fileStream = new PrintStream("resources//".concat(arquivo.substring(0, arquivo.length()-4)).concat("-saida.txt"));
			System.setOut(fileStream);
			
		}
		
		System.out.println("-".repeat(150));
		System.out.println("\nSOURCE: ".concat(arquivo).concat("\n"));

		
		while (taxaAprenzidado < MAIOR_TAXA_APRENDIZADO) {
			
			entrada = new Entrada();
			List<ParametrosEntrada> lstPE = entrada.prepara("resources//".concat(arquivo), periodos, neuroniosCamadaEscondida, taxaAprenzidado);
			
			int epoca = 0;
			
			// EXIBIÇÃO
			if (taxaAprenzidado == MENOR_TAXA_APRENDIZADO) {
				for (ParametrosEntrada parametrosEntrada : lstPE) {
					System.out.println("ENTRADA: "+parametrosEntrada.getValor().concat("\n"));
					Util.printArray(null, "x", parametrosEntrada.getEntradasDaRede(), "D", 1);
					Util.printArray("SAÍDA ESPERADA", "d", parametrosEntrada.getSaidaEsperada(), "D", 0);
				}
			}
			
			System.out.println("-".repeat(150));
			System.out.println("\nTAXA APRENDIZADO: ".concat(String.valueOf(taxaAprenzidado)));
			Util.printMatriz("PESOS INICIAIS DA CAMA ESCONDIDA", lstPE.get(0).getPesosCamadaEscondida(), "");
			Util.printMatriz("PESOS INICIAIS DA CAMA DE SAIDA", lstPE.get(0).getPesosCamadaSaida(), "");
			
			
			while (epoca < lstPE.get(0).getEpocas()) {
				
				for (int i = 0; i < lstPE.size(); i++) {			
					
					// EXIBIÇÃO
					if (modo == Modo.APRESENTACAO || epoca == lstPE.get(0).getEpocas()-1) {
						if (lstPE.indexOf(lstPE.get(i)) == 0 ) {
							System.out.println("\nEPOCA: "+(epoca+1)+"\n");
						}
						System.out.println("ENTRADA: "+lstPE.get(i).getValor());
					}
					
					//Processa a rede com os parâmetros de entrada
					MultilayerPerceptron mp = new MultilayerPerceptron(lstPE.get(i), modo);
					mp.processarRede();				

					
					// EXIBIÇÃO
					if (!(modo == Modo.APRESENTACAO) && epoca == lstPE.get(0).getEpocas()-1) {
						Util.printArray(" > SAÍDA DA REDE", "y", mp.getSaidaRede(), "D", 0);

					}
					
					
					/*
					 * Calcula o erro do período
					 * erro periodo = ( ( Somatorio(saída espera - valor de saída)²) /  2 ) / quantidade elementos do conjunto de treinamento
					 */
					if (i == lstPE.size()-1) {
						erroEpoca = (mp.getErro()/2) / (lstPE.size());
						lstErros.add(taxaAprenzidado+","+(epoca+1)+","+erroEpoca);
						mp.zeraErro();
					}
					
					// EXIBIÇÃO
					if ((modo == Modo.APRESENTACAO && (lstPE.indexOf(lstPE.get(i)) == lstPE.size() - 1)) || (epoca == lstPE.get(0).getEpocas()-1 && (lstPE.indexOf(lstPE.get(i)) == lstPE.size() - 1))) {
						System.out.println("\nERRO DA REDE: "+(erroEpoca)+"\n");
						if (modo == Modo.TREINAMENTO) {
							Util.printMatriz("NOVOS PESOS DA CAMADA DE ENTRADA", lstPE.get(i).getPesosCamadaEscondida(), "D");
							Util.printMatriz("NOVOS PESOS DA CAMADA ESCONDIDA", lstPE.get(i).getPesosCamadaSaida(), "D");
						}
					}
				}
				epoca++;
			}
			taxaAprenzidado = taxaAprenzidado+0.05;
		}
		
		//Escreve o arquivo contendo os valores dos erros.
		if (w != null) {
			for (String erro : lstErros) {
				w.write(erro);
				w.newLine();
			}
		}
		if (w != null) 
			w.close();			
	}

}
