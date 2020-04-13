import java.io.IOException;
import java.util.List;

public class Main{

	public static void main(String[]args) throws IOException{
		
		Entrada entrada  = new Entrada();
		
		String modo = "";
		
		List<ParametrosEntrada> lstPE = entrada.prepara("resources//caracteres-ruido.csv", 10000, 0, 0.6);
		
		int nEpocas = 0;
		
		System.out.println("> VALORES INICIAIS DA CAMA DE ENTRADA\n");
		Output.printPesos( lstPE.get(0).getPesosCEnInicial(), "");
		System.out.println("> VALORES INICIAIS DA CAMA ESCONDIDA\n");
		Output.printPesos( lstPE.get(0).getPesosCEsInicial(), "");
		
		
		while (nEpocas < lstPE.get(0).getEpocas()) {
			
			for (ParametrosEntrada pe : lstPE) {

				MultilayerPerceptron mp = new MultilayerPerceptron(pe, modo);
				mp.processarRede();				
				
				if (modo.equalsIgnoreCase("AP") || nEpocas == lstPE.get(0).getEpocas()-1) {
					System.out.println(" > ÉPOCA: "+(nEpocas+1)+"\n");
					System.out.println(" > ENTRADA: "+pe.getValor()+"\n");
					
				}
				
				if (!modo.equalsIgnoreCase("AP") && nEpocas == lstPE.get(0).getEpocas()-1) {
					System.out.println("> SAÍDA DA REDE\n");
					Output.printValores("y", mp.getSaidaRede(), "f", 0);
				}
				
				if (modo.equalsIgnoreCase("AP")) {
					Output.printValores("x", pe.getCamadaEntrada(), "f", 0);
				}
				
				
				

		}
			nEpocas++;

		}
		
	}

}
