import java.util.Random;

//Criar constante para representar numero de epocas?

// MUDAR FUNÇÃO DE ATIVAÇÃO?

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
		pesos1 = new double[j][i];
		pesos2 = new double[k][j];
		entradas = new double[i];
		camadaEscondida = new double[j];
		saidas = new double[k];
		bias1=new double[j];
		bias2=new double[k];
		alfa=1;
	}


	//--->É necessário criar uma função de ativação para cada letra?<---

	/*double funcaoAtivacao(double in){
		if(in>=0){
			return 1;		
		}
		else
			return -1;
	}*/

	double funcaoAtivacao(double in){
		return 1/(1-Math.exp(in));
	}

	double derivadaAtivacao(double in){
		double fx=funcaoAtivacao(in);
		return fx*(1-fx);
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

	//Inicializa pesos
	//matriz.length->numero de linhas
	//matriz[0].length->numero de colunas
	double[][] inicializaPesos(double[][] pesos){
		Random r = new Random();
		for(int i=0; i<pesos.length; i++){
			for(int j=0; j<pesos[0].length; j++){
				pesos[i][j]=r.nextDouble();
			}
		}
		return pesos;
	}

	/*
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
	}*/

	
	
	//Algoritmo de treinamento.
	//não retorna nada, apenas modifica pesos da estrutura.
	//esperado é uma matriz, pois as letras são representadas como arranjos (ex: A ={1,0,0,0,0,0,0})
	//k:numero de dados;	
	void treinamento(double[][]dados, double[][]esperado){
		
		//Os pesos devem ser inicializados fora do método (passo 0)
				
		int epocas=0;
		double[] delta= new double[dados.length];//termo de informação do erro
		double[] delta2=new double[camadaEscondida.length];

		while(epocas<2){//(passo 1)

			//para cada dado
			for(int k=0; k<dados[0].length;k++){

				//neuronios de entrada recebem os dados (passo 3)
				entradas=dados[k];

				//para cada linha de pesos1 (entre a entrada e a camada escondida)
				for(int i=0; i<pesos1.length; i++){//(passo 4)
				
					//Entrada:
					double[] linha=pesos1[i];

					//Faz soma para cada neuronio da camada escondida
					for(int j=0; j<linha.length;j++){
						camadaEscondida[i]+=linha[j]*entradas[j];					
					}

					//Aplica a função de ativação no neuronio atual da camada escondida
					camadaEscondida[i]=funcaoAtivacao(camadaEscondida[i]);		
				}
				//para cada linha de pesos2 (entre a camada escondida e a saída)
				for(int i=0; i<pesos2.length; i++){//(passo 5)
				
					//Entrada:
					double[] linha=pesos2[i];

					//Faz soma para cada neuronio da camada escondida
					for(int j=0; j<linha.length;j++){
						saidas[i]+=linha[j]*camadaEscondida[j];					
					}

					//Aplica a função de ativação no neuronio atual da camada escondida
					saidas[i]=funcaoAtivacao(saidas[i]);
				}
	
				//(Passo 6)
				//para cada saída calcula termo de informação do erro
				for(int i=0; i<saidas.length;i++){
					delta[i]=(saidas[i]-dados[k][i])*derivadaAtivacao(saidas[i]);
				}
				//correções
				double[][] correcaoPesos2=new double[pesos2.length][pesos2[0].length];
				double[] correcaoBias2=new double[bias2.length];
				//calcula correção de erros
				for(int i=0; i<correcaoPesos2.length; i++){
					for(int j=0; j<correcaoPesos2[0].length; j++){
						//correcaoPesos2[i][j]=alfa*delta[i]*pesos2[i][j];
						correcaoPesos2[i][j]=alfa*delta[i]*camadaEscondida[j];
					}
				}
				//calcula novo bias
				for(int i=0; i<bias2.length; i++){
					correcaoBias2[i]=alfa*delta[i];
				}


				//(Passo 7)
				//deltas da camada escondida
				double[] deltaIn= new double[camadaEscondida.length];
		
				for(int i=0; i<deltaIn.length;i++){
					for(int j=0; i<pesos2.length;j++){
						deltaIn[i]+=delta[j]*pesos2[i][j];
					}	
				}
				
				for(int i=0; i<camadaEscondida.length;i++){
					delta2[i]=(deltaIn[i])*derivadaAtivacao(camadaEscondida[i]);
				}

				double[][] correcaoPesos1=new double[pesos1.length][pesos1[0].length];
				double[] correcaoBias1=new double[bias1.length];

				for(int i=0; i<correcaoPesos1.length; i++){
					for(int j=0; j<correcaoPesos1[0].length; j++){
						correcaoPesos2[i][j]=alfa*delta2[i]*entradas[j];
					}
				}
			}
			epocas++;
		}
	}
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
