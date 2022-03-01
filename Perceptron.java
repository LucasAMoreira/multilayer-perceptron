import java.util.Random;

// Esta classe implementa um perceptron simples
public class Perceptron{

	double[] pesos;
	double[] entradas;
	double alfa; // Taxa de aprendizado
	double bias;

  // Construtor. Recebe como entrada um inteiro 'i'
  // com o tamanho da camada de entrada desejada pelo usuário
	Perceptron(int i){
		pesos = new double[i];
		entradas = new double[i];
		alfa=1;
		bias=0;
	}

  // Função de ativação linear
	int funcaoAtivacao(double in){
		if(in>=0){
			return 1;
		}
		else
			return -1;
	}

  // Alimenta neurônio de saida com somatório de
  // cada entrada pelo peso dos neuronios.
  // Retorna esse resultado somado com o bias (viés).
	double entradaNeuronio(){
		double resposta=0;
		for(int i=0; i<pesos.length;i++){
			resposta+=pesos[i]*entradas[i];
		}
		return resposta+bias;
	}

	// Inicializa pesos com valores aleatórios
	double[] inicializaPesos(){
		Random r = new Random();
		for(int i=0; i<pesos.length; i++){
			pesos[i]=r.nextDouble();
		}
		return pesos;
	}

  // Testa perceptron com conjunto de testes
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

  double[] treino(double[][] conjuntoTreino, int epocas){
    int epocaAtual = 0;

    //Os pesos devem ser inicializados fora do método (passo 0)
		double inFuncaoAtivacao=0;
		int saida=0;

    while(epocaAtual < epocas){

      // Para cada linha
      for(int i=0; i<conjuntoTreino.length; i++){

        // Entrada
				double[] linha=conjuntoTreino[i];

        // Percorre linha (exceto ultima coluna) e atribui entrada do perceptron
        for(int j=0; j<linha.length-1;j++){
          entradas[j]=linha[j];
        }

				inFuncaoAtivacao = entradaNeuronio();

        saida=funcaoAtivacao(inFuncaoAtivacao);

        // Se saida é diferente do rótulo (target), atualiza os pesos e o bias
				if(saida!=linha[linha.length-1]){
					for(int j=0; j<pesos.length; j++){
						pesos[j]=pesos[j]+(alfa*linha[linha.length-1]*entradas[j]);
					}
					bias+=alfa*linha[linha.length-1];
				}

      }
      epocaAtual++;
    }
    return pesos;
  }

}
