import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Entrada {
	
	private static final String BIAS = "1,";
	
	private List<ParametrosEntrada> lstParametrosEntrada;
	
	public double[][] pesosCamadaEscondida;
	public double[][] pesosCamadaEntrada;
	
	
	
	/*
	 * Lê o arquivo csv e realiza a criação de um objeto contendo os valores que serão utilizados no processamento o multilayer perceptron.
	 * - O bias (1) é adicionado nas entradas da rede.
	 * - Caso o número de neurônios na camada escondida seja 0, ele é definido a partir de média geométrica √ (quantidade de neurônios de entrada * quantidade neurônios de saída) 
	 * 
	 */
	public List<ParametrosEntrada> prepara(String path, int epocas, int neuroniosCamadaEscondida, double taxaAprendizado)  {
		
		lstParametrosEntrada = new LinkedList<ParametrosEntrada>();
		
		try {
			String row;
			String[] entrada;
			double[] entradaAsDouble;
			ParametrosEntrada parametrosEntrada;
			BufferedReader arquivo = new BufferedReader(new FileReader(path));
			while ((row = arquivo.readLine()) != null) {
				parametrosEntrada = new ParametrosEntrada();
				entrada = (BIAS.concat(row).replaceAll("\\uFEFF", "")).split(",");
			    entradaAsDouble = Arrays.stream(Arrays.copyOfRange(entrada, 0, entrada.length-1))
	                    .mapToDouble(Double::parseDouble)
	                    .toArray();
			    parametrosEntrada.setValor(entrada[entrada.length-1]);
			    parametrosEntrada.setEntradasDaRede(entradaAsDouble);
			    parametrosEntrada.setSaidaEsperada(ExpectedValue.getExpectedValue(entrada[entrada.length-1]));
			    neuroniosCamadaEscondida = (int) (neuroniosCamadaEscondida == 0 ? Math.sqrt((entrada.length - 1)*ExpectedValue.getExpectedValue(entrada[entrada.length-1]).length) : neuroniosCamadaEscondida);
			    parametrosEntrada.setPesosCamadaEscondida(pesosCamadaEscondida == null ? getMatrizPesos(neuroniosCamadaEscondida, entradaAsDouble.length) : pesosCamadaEscondida);
			    parametrosEntrada.setPesosCamadaSaida(pesosCamadaEntrada == null ? getMatrizPesos(ExpectedValue.getExpectedValue(entrada[entrada.length-1]).length, neuroniosCamadaEscondida + 1) : pesosCamadaEntrada);
			    parametrosEntrada.setEpocas(epocas);
			    parametrosEntrada.setTaxaAprendizado(taxaAprendizado);
			    lstParametrosEntrada.add(parametrosEntrada);
			}
			arquivo.close();	
		} catch (MissingExpectValueException e ) {
			System.out.println(e.getMessage());
		} catch (FileNotFoundException e) {
			System.out.println("O arquivo "+path+" nao existe.");
		} catch (IOException e) {
			System.out.println("Erro na manipulacao do arquivo "+path);
		}
		
		return lstParametrosEntrada;		
	}
	
	/*
	 * Cria uma matriz e preenche com valores aleatórios entre 0 e 1;
	 */
	
	private double[][] getMatrizPesos(int rows, int columns) {
		
		Random rd = new Random();
		
		double[][] weightMatrix = new double[rows][columns];
		for (int i = 0; i < weightMatrix.length; i ++)
			for (int y = 0; y < weightMatrix[i].length; y++)
				weightMatrix[i][y] = rd.nextDouble();
		return weightMatrix;
	}
	
	
}
