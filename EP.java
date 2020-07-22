import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.io.BufferedReader;
import java.io.FileReader;

public class EP{


	static double[][] dados;
	static double[][] dadosTreinamento;
	static double[][] dadosTeste;
	static double[][] respostas;	
	static double[][] respostasTreinamento;	
	static double[][] respostasTeste;	



	public static void main(String[]args) throws Exception {
		
		ConjuntoDados cd = ConjuntoDados.IONOSPHERE;
		
		String caminhoArquivo = "C:\\Users\\leoco\\Desktop\\ionosphere.data";
		
		leArquivo(caminhoArquivo, cd);
		
		int neuronioCamadaEscondida = 20;
		double taxaAprendizado = 0.1;
		int epocas = 5000 ;
		
		MultilayerPerceptron rede = new MultilayerPerceptron(cd.getNeuroniosEntrada(), neuronioCamadaEscondida, cd.getNeuroniosSaida(), epocas, taxaAprendizado);
		prepararFolds();
		rede.treinamento(dadosTreinamento,respostasTreinamento);
		rede.teste(dadosTeste, respostasTeste);


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
	
	private static void prepararFolds()  {
		
		List<Integer> posicoesDadosTreinamento = new LinkedList<Integer>();
		Random random = new Random();
		
		int itensTreinamento = (int) (0.60 * dados.length);
		System.out.println(itensTreinamento);
		System.out.println(respostas.length - itensTreinamento);

		dadosTeste = new double[respostas.length - itensTreinamento][dados[0].length];
		respostasTeste = new double[respostas.length - itensTreinamento][respostas[0].length];
		
		dadosTreinamento = new double[itensTreinamento][dados[0].length];
		respostasTreinamento = new double[itensTreinamento][respostas[0].length];
		
		for  (int i = 0; i < itensTreinamento; i++) {
//			System.out.println(i);
			int pos = ThreadLocalRandom.current().nextInt(0, dados.length);
			while (posicoesDadosTreinamento.contains(pos)) {
				pos = ThreadLocalRandom.current().nextInt(0, dados.length);
			}
			posicoesDadosTreinamento.add(pos);
//			System.out.println(pos);

		}
		
		int aux = 0;
		int aux2 = 0;
		for (int y = 0; y < dados.length - 1; y++) {
			
			if (posicoesDadosTreinamento.contains(y)) {
//				System.out.println(aux);
				dadosTreinamento[aux] = dados[y];
				respostasTreinamento[aux] = respostas[y];
				aux++;
			} else {
				dadosTeste[aux2] = dados[y];
			respostasTeste[aux2] = respostas[y];
//				System.out.println(aux2);
				aux2++;

			}
		
		}


	}
}
