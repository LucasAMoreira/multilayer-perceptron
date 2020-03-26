import java.util.Random;

//Criar constante para representar numero de epocas?
//Inicializar bias1 e bias2?


class MLP{

	double[][] pesos1;//matriz de pesos da entrada para camada escondida
	double[][] pesos2;//matriz de pesos da camada escondida para saída
	double[] entradas;
	double[] camadaEscondida;
	double[] saidas;

	double[] bias1;//arranjo de bias entre entrada e camada escondida
	double[] bias2;//arranjo de bias entre camada escondida e saída

	double alfa;//taxa de aprendizado

	//i:número de elementos de entrada
	//j:número de elementos da camada escondida
	//k:número de elementos da saída
	MLP(int i,int j, int k){
		pesos1 = new double[i][j];
		pesos2 = new double[j][k];
		entradas = new double[i];
		camadaEscondida = new double[j];
		saidas = new double[k];
		bias1=new double[j];
		bias2=new double[k];
		alfa=1;
	}

	int funcaoAtivacao(double in){
		if(in>=0){
			return 1;		
		}
		else
			return -1;
	}

	//--->		ATENÇÃO NOS PESOS	<---
	//Faz somatório {(pesoI*entradaI)+...+(pesoN*entradaN)}
	//Recebe como parametros os pesos para determinado elemento e o bias para cada elemento
	double entradaNeuronio(double[] pesos, double bias){
		double resposta=0;
		for(int i=0; i<pesos.length;i++){
			resposta+=pesos[i]*entradas[i];
		}
		return resposta+bias;
	}

	//---> Continuar daqui
	//Inicializa pesos
	
	/*double[][] inicializaPesos(){
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
	}*/
}

public class EP1{
	
	
	public static void main(String[]args){
	/*	double[][] and=	{
					{1,1,1},
					{1,-1,-1},
					{-1,1,-1},
					{-1,-1,-1}
				};
		Perceptron p=new Perceptron(2);

		double[] pesos=p.treinamento(and);

		//for(int i=0; i<pesos.length; i++){
		//	System.out.println(pesos[i]);
		//}

		double[][] in ={
				{1,1},
				{1,-1},
				{-1,1},			
				{-1,-1}
				};
		p.teste(in);
	*/

	}
	
	

}
