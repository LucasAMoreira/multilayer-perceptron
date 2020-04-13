import org.junit.Test;

public class MultilayerPerceptronTest {

	@Test
	public void testMultilayerPerceptron() {
		
		 double[] ce = {1,1,1};
		
		 double[][] pcen = { {-0.1, 0.1, -0.1}, {-0.1, 0.1, 0.1}, {0.1, -0.1, -0.1} };
		 double[][] pces = { {-0.1, 0.1, 0.0, 0.1}, {0.1, -0.1, 0.1, -0.1} };
		
		 double[] se = {1,0};
		 
		ParametrosEntrada pe = new ParametrosEntrada();
		pe.setEntradasDaRede(ce);
		pe.setPesosCamadaEscondida(pcen);
		pe.setPesosCamadaSaida(pces);
		pe.setEpocas(1);
		pe.setSaidaEsperada(se);
		pe.setTaxaAprendizado(0.5);
		
		MultilayerPerceptron testSubject = new MultilayerPerceptron(pe, Modo.TREINAMENTO);
		testSubject.processarRede();
	}

}
