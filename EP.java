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

		ConjuntoDados cd = ConjuntoDados.RADAR;

		String caminho = "./entradas/ionosphere.data";

		leArquivo(caminho, cd);	

		//Numero neuronios na camada escondida (?)
		int neuroniosCE = (int)(Math.sqrt(cd.getNeuroniosEntrada()*cd.getNeuroniosSaida()));

		MultilayerPerceptron rede=new MultilayerPerceptron(cd.getNeuroniosEntrada(),neuroniosCE,cd.getNeuroniosSaida());	

		holdout(dados,rede);

		
	}


	public static void holdout(double[][] dados, MultilayerPerceptron rede){

		for(int i=0; i<30; i++){
			sorteia(dados,respostas);

			int fim = retornaFim(dados,0.7);

			double[][] conjuntoTreino = corta(dados,0,fim);
			double[][] respostasTreino = corta(respostas,0,fim);
			double[][] conjuntoTeste = corta(dados,fim,dados.length);
			double[][] respostasTeste = corta(respostas,fim,respostas.length);

			rede.treinamento(conjuntoTreino,respostasTreino);
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
			//System.out.println(e);
			e.printStackTrace();
		}
	}

	public static  void sorteia (double[][] original,double[][] res){

		Random aleatorio = new Random();

		dados = new double[original.length][original[0].length];
		respostas=new double[respostas.length][respostas[0].length];

		int[] sorteados = new int[original.length];

		for(int i=0; i<dados.length; i++){
			int valor = aleatorio.nextInt(original.length);
			
			int situacao = sorteados[valor];
			
			if(situacao==1){
				i--;
			}
			else{
				sorteados[valor]=1;
				dados[i]=original[valor];
				respostas[i]=res[valor];
			}
		}
	}

	public static double[][] corta(double[][] original, int inicio, int fim){
			
		double[][] nova = new double[fim-inicio][original[0].length];

		for(int i=0; i<fim-inicio; i++){
			nova[i]=original[inicio+i];
		}

		return nova;
	}

	public static int retornaFim(double[][] matriz, double porcentagem){

		int resposta = (int)(matriz.length*porcentagem);

		return resposta;
	}
}
