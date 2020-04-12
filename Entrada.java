import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Entrada {
	
	private final String BIAS = "1,";
	
	private List<ParametrosEntrada> listaPE = new LinkedList<ParametrosEntrada>();
	
	public List<ParametrosEntrada> prepara(String path, int epocas, int neuroniosCamadaEscondida)  {
		
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
			    parametrosEntrada.setCamadaEntrada(entradaAsDouble);
			    parametrosEntrada.setSaidaEsperada(ExpectedValue.getExpectedValue(entrada[entrada.length-1]));
			    
			    neuroniosCamadaEscondida = (int) (neuroniosCamadaEscondida == 0 ? Math.sqrt(entrada.length*ExpectedValue.getExpectedValue(entrada[entrada.length-1]).length) : neuroniosCamadaEscondida);
			    
			    parametrosEntrada.setPesosCEn(Util.getWeightMatrix(neuroniosCamadaEscondida, entradaAsDouble.length));
			    parametrosEntrada.setPesosCEs(Util.getWeightMatrix(ExpectedValue.getExpectedValue(entrada[entrada.length-1]).length, neuroniosCamadaEscondida + 1));
			    parametrosEntrada.setEpocas(epocas);
			    
			    listaPE.add(parametrosEntrada);
			    
			}
			arquivo.close();	
		} catch (MissingExpectValueException e ) {
			System.out.println(e.getMessage());
		} catch (FileNotFoundException e) {
			System.out.println("O arquivo "+path+" n�o existe.");
		} catch (IOException e) {
			System.out.println("Erro na manipula��o do arquivo "+path);
		}
		
		return listaPE;
						
		
	}
	
	
}
