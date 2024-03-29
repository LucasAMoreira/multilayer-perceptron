import java.util.Random;
import java.text.DecimalFormat;
//import Entradas.java;
//Criar constante para representar numero de epocas?

// MUDAR FUNÇÃO DE ATIVAÇÃO?

public class MLP{

	private double[][] pesos1;//matriz de pesos da entrada para camada escondida
	private double[][] pesos2;//matriz de pesos da camada escondida para saída
	private double[] entradas;
	private double[] camadaEscondida;
	private double[] saidas;

	private double[] bias1;//arranjo de bias entre entrada e camada escondida
	private double[] bias2;//arranjo de bias entre camada escondida e saída

	private double alfa;//taxa de aprendizado

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

	// Função de ativação logistica
	public double funcaoAtivacao(double in){
		return 1/(1+Math.exp(-1*in));
	}

	// Derivada da função de ativação
	public double derivadaAtivacao(double in){
		double fx=funcaoAtivacao(in);
		return fx*(1-fx);
	}

	//--->		ATENÇÃO NOS PESOS	<---
	//Faz somatório {(pesoI*entradaI)+...+(pesoN*entradaN)}
	//Recebe como parametros os pesos para determinado elemento e o bias para cada elemento
	private double entradaNeuronio(double[] pesos, double bias){
		double resposta=0;
		for(int i=0; i<pesos.length;i++){
			resposta+=pesos[i]*entradas[i];
		}
		return resposta+bias;
	}

	//Inicializa pesos
	//matriz.length->numero de linhas
	//matriz[0].length->numero de colunas
	private double[][] inicializaPesos(double[][] pesos){
		Random r = new Random();
		for(int i=0; i<pesos.length; i++){
			for(int j=0; j<pesos[0].length; j++){
				double sinal=r.nextDouble();
				double atual=r.nextDouble();
				if(sinal<=0.5){
					atual=atual*-1;
				}

				if(atual>0.5 || atual<-0.5){
					j--;
				}else{
					pesos[i][j]=atual;
				}
			}
		}
		return pesos;
	}

	private void inicializaBias(){
		Random r = new Random();

		for(int i=0; i<bias1.length; i++){
			double sinal=r.nextDouble();
			double atual=r.nextDouble();
			if(sinal<=0.5){
				atual=atual*-1;
			}

			if(atual>0.5 || atual<-0.5){
				i--;
			}else{
				bias1[i]=atual;
			}
		}
		for(int i=0; i<bias2.length; i++){
			double sinal=r.nextDouble();
			double atual=r.nextDouble();
			if(sinal<=0.5){
				atual=atual*-1;
			}

			if(atual>0.5 || atual<-0.5){
				i--;
			}else{
				bias2[i]=atual;
			}
		}
	}


	public void teste(double[][]entrada){

		DecimalFormat df = new DecimalFormat("#0.0000");

		for(int k=0; k<entrada.length;k++){
			entradas=entrada[k];
			//passo 4: faz soma ponderada nos neuronios da camada escondida
			for(int i=0; i<pesos1.length; i++){

				double[] zIn= new double[camadaEscondida.length];
				//Faz soma  para cada neuronio da camada escondida
				for(int j=0; j<pesos1[i].length;j++){
					zIn[i]+=(pesos1[i][j]*entradas[j]);
				}
				zIn[i]+=bias1[i];
				//Aplica a função de ativação no neuronio atual da camada escondida
				camadaEscondida[i]=funcaoAtivacao(zIn[i]);
			}

			//passo 5: faz soma ponderada nos neuronios da camada de saída
			for(int i=0; i<pesos2.length; i++){

				double[] yIn= new double[saidas.length];
				//Faz soma para cada neuronio da camada escondida
				for(int j=0; j<pesos2[i].length;j++){
					yIn[i]+=(pesos2[i][j]*camadaEscondida[j]);
				}
				yIn[i]+=bias2[i];
				//Aplica a função de ativação no neuronio atual da camada escondida
				saidas[i]=funcaoAtivacao(yIn[i]);
			}

			for(int i=0; i<saidas.length; i++){
				System.out.print(df.format(saidas[i])+" ");
			}
			System.out.println();
		}
	}



	//Algoritmo de treinamento.
	//não retorna nada, apenas modifica os pesos da estrutura.
	//esperado é uma matriz, pois as letras são representadas como arranjos (ex: A ={1,0,0,0,0,0,0})
	//k:numero de dados;
	public void treinamento(double[][]dados, double[][]esperado){

		DecimalFormat df = new DecimalFormat("#0.0000");

		int epocas=0;
		double[] delta= new double[saidas.length];//termo de informação do erro
		double[] delta2=new double[camadaEscondida.length];
		double[] deltaIn= new double[camadaEscondida.length];

		double[] zIn= new double[camadaEscondida.length];
		double[] yIn= new double[saidas.length];

		//passo 0: Inicializa pesos
		pesos1=inicializaPesos(pesos1);
		pesos2=inicializaPesos(pesos2);

		inicializaBias();

		//passo 1: executa passos 2-9 enquanto condição for verdadeira
		while(epocas<10000){

			delta= new double[saidas.length];//termo de informação do erro
			delta2=new double[camadaEscondida.length];// Termo de informação do erro da camada escondida
			deltaIn= new double[camadaEscondida.length];

			//System.out.println("Epoca: "+epocas);

			//passo 2: para cada dado, executa passos 3-8
			for(int k=0; k<dados.length;k++){

				zIn= new double[camadaEscondida.length];
				yIn= new double[saidas.length];
				//feedforward (passos 3-5)

				//passo 3: neuronios de entrada recebem os dados
				entradas=dados[k];

				//passo 4: faz soma ponderada nos neuronios da camada escondida
				for(int i=0; i<pesos1.length; i++){

					//Faz soma  para cada neuronio da camada escondida
					for(int j=0; j<pesos1[i].length;j++){
						zIn[i]+=(pesos1[i][j]*entradas[j]);
					}
					zIn[i]+=bias1[i];

					//Aplica a função de ativação no neuronio atual da camada escondida
					camadaEscondida[i]=funcaoAtivacao(zIn[i]);
				}

				//passo 5: faz soma ponderada nos neuronios da camada de saída
				for(int i=0; i<pesos2.length; i++){

					//Faz soma para cada neuronio da camada escondida
					for(int j=0; j<pesos2[i].length;j++){
						yIn[i]+=(pesos2[i][j]*camadaEscondida[j]);
					}
					yIn[i]+=bias2[i];
					//Aplica a função de ativação no neuronio atual da camada escondida
					saidas[i]=funcaoAtivacao(yIn[i]);
				}

				//backpropagation (passos 6-7)

				//passo 6: calcula termo de informação do erro delta de cada unidade de saída
				for(int i=0; i<saidas.length;i++){
					delta[i]=(esperado[k][i]-saidas[i])*derivadaAtivacao(yIn[i]);
				}

				//correções
				double[][] correcaoPesos2=new double[pesos2.length][pesos2[0].length];
				double[] correcaoBias2=new double[bias2.length];

				//calcula termo de correção dos erros
				for(int i=0; i<correcaoPesos2.length; i++){
					for(int j=0; j<correcaoPesos2[0].length; j++){
						correcaoPesos2[i][j]=alfa*delta[i]*camadaEscondida[j];
					}
				}

				//calcula termo de correção do bias
				for(int i=0; i<bias2.length; i++){
					correcaoBias2[i]=alfa*delta[i];
				}

				//passo 7: cada unidade da camada escondida soma os deltas vindos da camada de cima
				for(int i=0; i<deltaIn.length;i++){
					for(int j=0; j<pesos2.length;j++){
						deltaIn[i]+=delta[j]*pesos2[j][i];
					}
				}

				for(int i=0; i<camadaEscondida.length;i++){
					delta2[i]=(deltaIn[i])*derivadaAtivacao(zIn[i]);
				}

				double[][] correcaoPesos1=new double[pesos1.length][pesos1[0].length];
				double[] correcaoBias1=new double[bias1.length];

				for(int i=0; i<correcaoPesos1.length; i++){
					for(int j=0; j<correcaoPesos1[0].length; j++){
						correcaoPesos1[i][j]=alfa*delta2[i]*entradas[j];
					}
				}
				//calcula novo bias
				for(int i=0; i<bias1.length; i++){
					correcaoBias1[i]=alfa*delta2[i];
				}

				//passo 8: atualiza pesos e bias
				for(int i=0; i<pesos1.length; i++){
					//Faz soma para cada neuronio da camada escondida
					for(int j=0; j<pesos1[0].length;j++){
						pesos1[i][j]+=correcaoPesos1[i][j];
					}
				}

				for(int i=0; i<pesos2.length; i++){
					//Faz soma para cada neuronio da camada escondida
					for(int j=0; j<pesos2[0].length;j++){
						pesos2[i][j]+=correcaoPesos2[i][j];
					}
				}

				for(int i=0; i<bias1.length; i++){
					bias1[i]+=correcaoBias1[i];
				}

				for(int i=0; i<bias2.length; i++){
					bias2[i]+=correcaoBias2[i];
				}

				/*//---->TESTES<----

				for(int i=0; i<saidas.length; i++){
					System.out.print(df.format(saidas[i])+" ");
				}
				System.out.println();*/

			}

			if(epocas>2500){
				alfa=0.7;
			}
			if(epocas>5000){
				alfa=0.5;
			}
			if(epocas>7500){
				alfa=0.25;
			}
			//alfa-=0.25*alfa;
			epocas++;
		}
	}
}
