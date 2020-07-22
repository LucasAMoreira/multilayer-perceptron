import java.util.Random;

class Perceptron{

	double[] pesos;
	double[] entradas;
	double alfa;//taxa de aprendizado
	double bias;

	Perceptron(int i){
		pesos = new double[i];
		entradas = new double[i];
		alfa=1;
		bias=0;
	}

	int funcaoAtivacao(double in){
		if(in>=0){
			return 1;		
		}
		else
			return -1;
	}

	//Inserir bias?
	double entradaNeuronio(){
		double resposta=0;
		for(int i=0; i<pesos.length;i++){
			resposta+=pesos[i]*entradas[i];
		}
		return resposta+bias;
	}

	//Inicializa pesos
	double[] inicializaPesos(){
		Random r = new Random();
		for(int i=0; i<pesos.length; i++){
			pesos[i]=r.nextDouble();
		}
		return pesos;
	}

	void teste(double[][]entrada){

		for(int j=0; j<entrada.length; j++){
			double resposta=0;
			for(int i=0; i<pesos.length;i++){
				resposta+=pesos[i]*entrada[j][i];
			}
			resposta+=bias;
	
			int solucao=funcaoAtivacao(resposta);
			System.out.println(solucao);
		}
	}


	//Entrada possui resposta
	double[] treinamento(double[][] conjuntoTreino){
		
		//Os pesos devem ser inicializados fora do método (passo 0)
		
		double inFuncaoAtivacao=0;		
		int saida=0;

		int epocas=0;
		while(epocas<2){
			//para cada linha 
			for(int i=0; i<conjuntoTreino.length; i++){
				
				//Entrada:

				double[] linha=conjuntoTreino[i];

				//percorrer linha (exceto ultima coluna) e atribuindo entrada do perceptron
				for(int j=0; j<linha.length-1;j++){
					entradas[j]=linha[j];					
				}

				//Calculo
				for(int j=0; j<entradas.length;j++){
					inFuncaoAtivacao+=entradas[j]*pesos[j];					
				}
				inFuncaoAtivacao+=bias;
			
				saida=funcaoAtivacao(inFuncaoAtivacao);

				if(saida!=linha[linha.length-1]){
					for(int j=0; j<pesos.length; j++){
						pesos[j]=pesos[j]+(alfa*linha[linha.length-1]*entradas[j]);
					}
					bias+=alfa*linha[linha.length-1];
				}
				
			}
			epocas++;
		}
		
		return pesos;
	}
}

public class Main{

	public static void main(String[]args){
		double[][] and=	{
					{1,1,1},
					{1,-1,-1},
					{-1,1,-1},
					{-1,-1,-1}
				};
		Perceptron p=new Perceptron(2);

		double[] pesos=p.treinamento(and);

		/*for(int i=0; i<pesos.length; i++){
			System.out.println(pesos[i]);
		}*/

		double[][] in ={
				{1,1},
				{1,-1},
				{-1,1},			
				{-1,-1}
				};
		p.teste(in);

	}

}
