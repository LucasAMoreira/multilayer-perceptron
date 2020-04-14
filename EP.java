import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.io.BufferedReader;
import java.io.FileReader;

public class EP{


	static double[][] dados;
	static double[][] dadosTR;
	static double[][] dadosTE;
	static double[][] respostas;	
	static double[][] respostasTR;	
	static double[][] respostasTE;	



	public static void main(String[]args) throws Exception {
		
		ConjuntoDados cd = ConjuntoDados.OR;
		
		String caminhoArquivoTreinamento = "C://Projetos//IA//resources//problemOR.csv";
		String caminhoArquivoTeste = "C://Projetos//IA//resources//problemOR.csv";
		
		leArquivo(caminhoArquivoTreinamento, cd);
		
		int neuroniosCE=(int)(Math.sqrt(cd.getNeuroniosEntrada()*cd.getNeuroniosSaida()));
		MultilayerPerceptron rede = new MultilayerPerceptron(cd.getNeuroniosEntrada(),10,cd.getNeuroniosSaida());

		
		if (caminhoArquivoTreinamento.equalsIgnoreCase(caminhoArquivoTeste)) { 
			
			List<Integer> lst = new LinkedList<Integer>();
			
			Random random = new Random();
			
			int itensTreinamento = (int) (0.25 * dados.length);
			
			dadosTE = new double[itensTreinamento][dados[0].length];
			respostasTE = new double[itensTreinamento][respostas[0].length];
			
			dadosTR = new double[dados.length - itensTreinamento][dados[0].length];
			respostasTR = new double[respostas.length - itensTreinamento][respostas[0].length];
			
			for  (int i = 0; i < itensTreinamento; i++) { 
				
				lst.add(random.nextInt(dados.length));
				
			}
			
			int aux = 0;
			int aux2 = 0;
			for (int y = 0; y < dados.length; y++) {
				
				if (lst.contains(y)) {
					dadosTE[aux] = dados[y];
					respostasTE[aux] = respostas[y];
					aux++;
				} else {
					dadosTR[aux2] = dados[y];
					respostasTR[aux2] = respostas[y];
					aux2++;
				}
			
			}
			
			rede.treinamento(dadosTR,respostasTR);
			rede.teste(dadosTE);
			
		} else {
			
			rede.treinamento(dados,respostas);
			
			leArquivo(caminhoArquivoTeste, cd);
			
			rede.teste(dados);
		}

	}
	
	
	private static void leArquivo(String caminho, ConjuntoDados cd) {
		
		List<String> valor = new LinkedList<String>();
		dados = new double[cd.getLinhasDados()][cd.getColunasDados()];
		respostas = new double [cd.getLinhasDados()][cd.getColunasRespostas()];
		
		int linha = 0;
		
		try {
			String row;
			String[] entrada;
			double[] entradaAsDouble;
			BufferedReader arquivo = new BufferedReader(new FileReader(caminho));
			while ((row = arquivo.readLine()) != null) {
				entrada = row.replaceAll("\\uFEFF", "").split(",");
			    entradaAsDouble = Arrays.stream(Arrays.copyOfRange(entrada, 0, entrada.length-1))
	                    .mapToDouble(Double::parseDouble)
	                    .toArray();
			    dados[linha] = entradaAsDouble;
			    respostas[linha] =  ValorEsperado.getValorEsperado(entrada[entrada.length-1]);
			    valor.add(entrada[entrada.length-1]);
			    linha++;
			}
			arquivo.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
