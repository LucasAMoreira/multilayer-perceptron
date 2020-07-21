import java.text.DecimalFormat;
import java.util.Random;

public class MultilayerPerceptron {
	
	double[][] pesos1;//matriz de pesos da entrada para camada escondida
	double[][] pesos2;//matriz de pesos da camada escondida para saída
	double[] entradas;
	double[] camadaEscondida;
	double[] saidas;

	double[] bias1;//arranjo de bias entre entrada e camada escondida
	double[] bias2;//arranjo de bias entre camada escondida e saída

	double alfa;//taxa de aprendizado
	
	private double erro;

	//i:número de elementos de entrada
	//j:número de elementos da camada escondida
	//k:número de elementos da saída
	public MultilayerPerceptron(int i,int j, int k){
		pesos1 = new double[j][i];
		pesos2 = new double[k][j];
		entradas = new double[i];
		camadaEscondida = new double[j];
		saidas = new double[k];
		bias1=new double[j];
		bias2=new double[k];
		alfa=1;
	}

	double funcaoAtivacao(double in){
		return 1/(1+Math.exp(-1*in));
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

	void inicializaBias(){
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
	//não retorna nada, apenas modifica os pesos da estrutura.
	//esperado é uma matriz, pois as letras são representadas como arranjos (ex: A ={1,0,0,0,0,0,0})
	//k:numero de dados;	
	void treinamento(double[][]dados, double[][]esperado){
		
		System.out.println("\n\n"+repeat("-",150));
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

		int max = 1000000;		

		//passo 1: executa passos 2-9 enquanto condição for verdadeira
		while(epocas<max) {

			delta= new double[saidas.length];//termo de informação do erro
			delta2=new double[camadaEscondida.length];
			deltaIn= new double[camadaEscondida.length];
			
			erro = 0;
			
			//System.out.println(repeat("-",150));
			
			//System.out.println("\nÉpoca: "+epocas);

			//passo 2: para cada dado, executa passos 3-8
			for(int k=0; k<dados.length;k++){


				zIn= new double[camadaEscondida.length];
				yIn= new double[saidas.length];
				
				double[][] correcaoPesos1=new double[pesos1.length][pesos1[0].length];
				double[] correcaoBias1=new double[bias1.length];
				double[][] correcaoPesos2=new double[pesos2.length][pesos2[0].length];
				double[] correcaoBias2=new double[bias2.length];
				
				//feedforward (passos 3-5)

				//passo 3: neuronios de entrada recebem os dados
				envioDadosNeuronio(dados[k]);
								
				//passo 4: faz soma ponderada nos neuronios da camada escondida
				calculoSaidaCamadaEscondida(zIn);
								
				//passo 5: faz soma ponderada nos neuronios da camada de saída
				calculoSaidaCamadaSaida(yIn);
				if(epocas==max-1){
					System.out.println("\n\nSaída da Rede: ");
				
					for(int i=0; i<yIn.length; i++){
						System.out.print(df.format(saidas[i])+" ");
					}
				}
				
				//backpropagation (passos 6-7)

				//passo 6: calcula termo de informação do erro delta de cada unidade de saída
				calculoInformacaoErroCamadaSaida(delta, esperado, k, yIn);
				
				//calcula termo de correção dos erros
				calculoTermoCorrecaoSaida(correcaoPesos2, correcaoBias2, delta);
								
				//passo7:cada unidade da camada escondida soma os deltas vindos da camada de cima		
				calculoInformacaoErroCamadaEscondida(deltaIn, delta, pesos2, delta2, zIn);
				
				
				//calcula termo de correção dos erros
				calculoTermoCorrecaoCamadaEscondida(correcaoPesos1, correcaoBias1, delta2);
				

				//passo 8: atualiza pesos e bias
				atualizaValores(correcaoPesos1, correcaoPesos2, correcaoBias1, correcaoBias2);
											
			}
			
			//System.out.println("\n\nErro da Época: "+(erro/2) / (dados.length));

			epocas++;
		}
	}
	
	public static double porcentagemAcertos(double[][] saidas, double[][] esperado){
		
		double acertos = saidas.length;	
		
		for(int i=0; i<saidas.length; i++){
			for(int j=0; j<saidas[i].length; j++){
				if(saidas[i][j]!=esperado[i][j]){
					acertos--;
					break;			
				}				
			}
		}

		double porcentagem = (acertos/saidas.length)*100;
		
		return porcentagem;
	}


	void teste(double[][]entrada, double[][] resposta){

		DecimalFormat df = new DecimalFormat("#0.0000");

		for(int k=0; k<entrada.length;k++){
			entradas=entrada[k];
			//passo 4: faz soma ponderada nos neuronios da camada escondida
			double[] zIn= new double[camadaEscondida.length];
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
			double[] yIn= new double[saidas.length];

			for(int i=0; i<pesos2.length; i++){
				//Faz soma para cada neuronio da camada escondida
				for(int j=0; j<pesos2[i].length;j++){
					yIn[i]+=(pesos2[i][j]*camadaEscondida[j]);			
				}
				yIn[i]+=bias2[i];
				//Aplica a função de ativação no neuronio atual da camada escondida
				saidas[i]=funcaoAtivacao(yIn[i]);
			}
			
			System.out.println("\nSaída da Rede:");
			
			for(int i=0; i<saidas.length; i++){
				System.out.print(df.format(saidas[i])+" ");
			}	
			
			System.out.println();
			System.out.println("\nSaída Esperada:");

				
			for(int i=0; i<resposta[k].length; i++){
				System.out.print(df.format(resposta[k][i])+" ");
			}	
			System.out.println();
		}

	}

	public static String repeat​(String s, int count){
		String resposta="";
		for(int i=0; i<count; i++){
			resposta=resposta+s;
		}
		return resposta;
	}
	
	public void envioDadosNeuronio(double[] dados) {
		entradas=dados;
	}
	
	public void calculoSaidaCamadaEscondida(double[] zIn) {
		for(int i=0; i<pesos1.length; i++){
			for(int j=0; j<pesos1[i].length;j++){
				zIn[i]+=(pesos1[i][j]*entradas[j]);
			}
			zIn[i]+=bias1[i];
			camadaEscondida[i]=funcaoAtivacao(zIn[i]);	
		}
	}
	
	public void calculoSaidaCamadaSaida(double[] yIn) {
		for(int i=0; i<pesos2.length; i++){
			for(int j=0; j<pesos2[i].length;j++){
				yIn[i]+=(pesos2[i][j]*camadaEscondida[j]);			
			}
			yIn[i]+=bias2[i];
			saidas[i]=funcaoAtivacao(yIn[i]);
		}
	}
	
	public void calculoInformacaoErroCamadaSaida(double[] delta, double[][] esperado, int k, double[] yIn) {
		double erro_aux;
		for(int i=0; i<saidas.length;i++){
			erro_aux = esperado[k][i]-saidas[i];
			erro = erro + (Math.pow(erro_aux, 2));
			delta[i]=erro_aux*derivadaAtivacao(yIn[i]);
		}
	}
	

	public void calculoTermoCorrecaoSaida(double[][] correcaoPesos2, double[] correcaoBias2, double[] delta) {
		for(int i=0; i<correcaoPesos2.length; i++){
			for(int j=0; j<correcaoPesos2[0].length; j++){
				correcaoPesos2[i][j]=alfa*delta[i]*camadaEscondida[j];
			}
		}
			
		for(int i=0; i<bias2.length; i++){
			correcaoBias2[i]=alfa*delta[i];
		}
	}
	
	public void calculoInformacaoErroCamadaEscondida(double[] deltaIn, double[] delta, double[][] pesos22, double[] delta2, double[] zIn) {
		for(int i=0; i<deltaIn.length;i++){
			for(int j=0; j<pesos2.length;j++){
				deltaIn[i]+=delta[j]*pesos2[j][i];
			}	
		}
		
		for(int i=0; i<camadaEscondida.length;i++){
			delta2[i]=(deltaIn[i])*derivadaAtivacao(zIn[i]);
		}

	}
	
	public void calculoTermoCorrecaoCamadaEscondida(double[][] correcaoPesos1, double[] correcaoBias1, double[] delta2) {
		for(int i=0; i<correcaoPesos1.length; i++){
			for(int j=0; j<correcaoPesos1[0].length; j++){
				correcaoPesos1[i][j]=alfa*delta2[i]*entradas[j];
			}
		}
		//calcula novo bias
		for(int i=0; i<bias1.length; i++){
			correcaoBias1[i]=alfa*delta2[i];
		}
	}
	
	public void atualizaValores(double[][] correcaoPesos1, double[][] correcaoPesos2, double[] correcaoBias1, double[] correcaoBias2) {
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
	}
	
	

	
}
