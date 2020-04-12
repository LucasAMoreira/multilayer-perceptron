import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class Main{

	public static void main(String[]args) throws IOException{
		
		Entrada entrada  = new Entrada();
		List<ParametrosEntrada> lstPE = entrada.prepara("resources//caracteres-limpo.csv", 1, 0, 0.5);
		
		int nEpocas = 0;
		while (nEpocas < lstPE.get(0).getEpocas()) {
			
			for (ParametrosEntrada pe : lstPE) {
				
				System.out.println("\n > ENTRADA: "+pe.getValor()+"\n");
				Output.printValores("x", pe.getCamadaEntrada());
				System.out.println("> VALORES INICIAIS DA CAMA DE ENTRADA\n");
				Output.printPesos( pe.getPesosCEnInicial(), "");
				System.out.println("> VALORES INICIAIS DA CAMA ESCONDIDA\n");
				Output.printPesos( pe.getPesosCEsInicial(), "");

				MultilayerPerceptron mp = new MultilayerPerceptron(pe);
				mp.processarRede();						
		}
			nEpocas++;

//			Output op = new Output();
//			op.write(pe);
		}
		
	}

}
