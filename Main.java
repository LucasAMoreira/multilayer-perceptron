import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class Main{

	public static void main(String[]args) throws IOException{
		
		Entrada entrada  = new Entrada();
		List<ParametrosEntrada> lstPE = entrada.prepara("resources//caracteres-limpo.csv", 1, 0, 0.5);
		for (ParametrosEntrada pe : lstPE) {
			
			MultilayerPerceptron mp = new MultilayerPerceptron(pe);
			mp.processarRede();		
			
			Output op = new Output();
			op.write(pe);
		}
		
	}

}
