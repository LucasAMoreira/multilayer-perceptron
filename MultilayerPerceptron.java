public class MultilayerPerceptron {
		
	private double[] saidaCamadaEscondida; 	
	private double[] saidaRede;
	private double[] informacaoErroCamadaSaida;
	private double[] informacaoErroCamadaEscondida; 
	private double[] somaAuxZ_W;						
	private double[] somaAuxX_V;						
	private double[][] termoAjustePesoCamadaSaida;
	private double[][] termoAjustePesoCamadaEscondida;
	
	private ParametrosEntrada pe;
	private Modo modo;
	private static double erro;
	
	public MultilayerPerceptron(ParametrosEntrada p, Modo modo) {
		
		this.saidaCamadaEscondida = new double[p.getPesosCamadaEscondida().length + 1];
		this.saidaCamadaEscondida[0] = 1;
		this.saidaRede  = new double[p.getSaidaEsperada().length];
		this.informacaoErroCamadaEscondida = new double[p.getPesosCamadaSaida()[0].length - 1];
		this.informacaoErroCamadaSaida = new double[p.getSaidaEsperada().length];
		this.somaAuxX_V = new double[p.getPesosCamadaSaida()[0].length - 1];
		this.somaAuxZ_W = new double[p.getSaidaEsperada().length];
		this.termoAjustePesoCamadaSaida  = new double[p.getPesosCamadaSaida().length][p.getPesosCamadaSaida()[0].length];
		this.termoAjustePesoCamadaEscondida = new double[p.getPesosCamadaEscondida().length][p.getPesosCamadaEscondida()[0].length];
		this.pe = p;
		this.modo = modo;
	}

	
	public void processarRede() {
			forwardStep();
			backwardStep();		
	}
	
	public void forwardStep() {
		
		if (modo == Modo.APRESENTACAO) {
			calculoSaidaCamadaEscondida();
			Util.printArray("SAÍDA DA CAMADA ESCONDIDA", "z", saidaCamadaEscondida, "NS", 1);
			calculoSaidaRede();
			Util.printArray("SAÍDA DA REDE", "y", saidaRede, "f", 0);
		} else {
			calculoSaidaCamadaEscondida();
			calculoSaidaRede();
		}
	}
	
	/*
	   Realize o cálculo da saída da camada escondida 
	 	saida da camada escondida(j) = funcao de ativação(somatório(entrada(i) * peso camada escondida(vij)), para i entre 0 e n, onde n é a quantidade de neurônios + o bias.
	 	O método também armazena os somatórios sem terem passado pela função de ativação em somaAuxX_V para serem utilizados no cálculo da informação de erro.
	 */
	public void calculoSaidaCamadaEscondida() {
		double valor = 0;
		for (int i = 0; i < pe.getPesosCamadaEscondida().length; i++) {
			for (int y = 0; y < pe.getEntradasDaRede().length; y++) {
				valor = valor + (pe.getPesosCamadaEscondida()[i][y] * pe.getEntradasDaRede()[y]);
			}
			somaAuxX_V[i] = valor;
			saidaCamadaEscondida[i+1] = funcaoAtivacaoSigmoide(valor);
		}
	}
	
	/*
	   Realize o cálculo da saída da rede 
	 	saída da rede(k) = funcao de ativação(somatório(saida camada escondida(j) * peso camada de saida(jk)), 
	 		para k entre 0 e p, onde p é a quantidade de neurônios na saída da rede.
	 		para j entre 0 e n, onde j é a quantidade de saídas da camada escondida	
	 	O método também armazena os somatórios sem terem passado pela função de ativação em somaAuxZ_W para serem utilizados no cálculo da informação de erro.
	 */
	public void calculoSaidaRede() {

		double valor=0;
		for (int i = 0; i < pe.getPesosCamadaSaida().length; i++) {
			for (int y = 0; y < pe.getPesosCamadaSaida()[i].length; y++) {
				valor = valor + (pe.getPesosCamadaSaida()[i][y] * saidaCamadaEscondida[y]);
			}
			somaAuxZ_W[i] = valor;
			saidaRede[i] = funcaoAtivacaoSigmoide(valor);
		}	
	}
	
	public void backwardStep() {
		if (modo == Modo.APRESENTACAO) {
			calculoInformacaoErroCamadaSaida();
			Util.printArray("INFORMAÇÃO DE ERRO PARA OS NEURÔNIOS NA SAÍDA DA REDE", "i", informacaoErroCamadaSaida, "NS", 0);
			calculoTermoAjusteCamadaSaida();
			Util.printMatriz("TERMOS DE AJUSTE DOS PESOS ASSOCIADOS A CADA NEURÔNIO DA CAMADA DE SAÍDA", termoAjustePesoCamadaSaida, "NS");
			calculoInformacaoErroCamadaEscondida();
			Util.printArray("INFORMAÇÃO DE ERRO PARA OS NEURÔNIOS DA CAMA ESCONDIDA", "i", informacaoErroCamadaEscondida, "NS", 0);
			calculoTermoAjusteCamadaEscondida();
			Util.printMatriz("TERMOS DE AJUSTE DOS PESOS ASSOCIADOS A CADA NEURÔNIO DA CAMADA ESCONDIDA", termoAjustePesoCamadaEscondida, "NS");
			atualizarValores();
			Util.printMatriz("NOVOS VALORES ATRIBUÍDOS AOS PESOS DA CAMADA ESCONDIDA", pe.getPesosCamadaEscondida(), "D");
			Util.printMatriz("NOVOS VALORES ATRIBUÍDOS AOS PESOS DA CAMADA DE SAÍDA", pe.getPesosCamadaSaida(), "D");
		} else {
			calculoInformacaoErroCamadaSaida();
			calculoTermoAjusteCamadaSaida();
			calculoInformacaoErroCamadaEscondida();
			calculoTermoAjusteCamadaEscondida();
			atualizarValores();
		}
	}
	
	/*
	   Realiza o cálculo da informação de erro para os neurônios na saída da rede:
		 	informacao de erro(k) = (valor esperado(k) - valor de saída(k) ) * derivada da funcao de ativação(somatório(saida camada escondida(j) * peso da camada de saída(jk)), 
			 	para k entre 0 e n, onde n é a quantidade de neurônios na cama de saída.
		 	 	para j entre 0 e p, onde p é a quantidade de neurônios na camada escondida.
		- erro será utilizado, posteriormente, no cálculo do erro do período. 
	 */
	public void calculoInformacaoErroCamadaSaida() {
		double erroAux = 0;
		for (int i = 0; i < somaAuxZ_W.length; i++) {
			erroAux = (pe.getSaidaEsperada()[i] - saidaRede[i]); //(valor esperado(k) - valor de saída(k) )
			erro = erro + (Math.pow(erroAux, 2));							
			informacaoErroCamadaSaida[i] = erroAux*calculoDerivadaCamadaSaida(i);	//(valor esperado(k) - valor de saída(k) ) * derivada da funcao de ativação(somatório(saida cama escondida(j) * peso da camada de saída(jk))
		}
	}
	
	/*
	  Cálculo da derivada de função de ativação para ser utilizada no cálculo da informação de erro.
		  f'(x) = f(x)*(1-f(x))
	 */
	public double calculoDerivadaCamadaSaida(int indice) {
		return funcaoAtivacaoSigmoide(somaAuxZ_W[indice])*(1-funcaoAtivacaoSigmoide(somaAuxZ_W[indice]));
	}
	
	/*
	   Realiza o cálculo dos termos de ajuste dos pesos associados a cada neuronio da camada de saída:
			termo de ajuste(jk) = taxa de aprendizado * informação de erro da camada de saída(k) * valor de saída(j) 
			 	para k entre 0 e n, onde n é a quantidade de neurônios na cama de saída.
		 	 	para i entre 0 e p, onde p é a quantidade de neurônios na cama escondida + o bias.
	 */
	public void calculoTermoAjusteCamadaSaida() {
		for (int i = 0; i < pe.getPesosCamadaSaida().length; i++) {
			for (int j = 0; j < pe.getPesosCamadaSaida()[i].length; j++) {
				termoAjustePesoCamadaSaida[i][j] = pe.getTaxaAprendizado()*informacaoErroCamadaSaida[i]*saidaCamadaEscondida[j];
			}
		}
	}
	
	/* Realiza o cálculo da informação de erro para os neurônios da camada escondida:
		 	informacao de erro(j) = somatorio(informacao de erro camada de saida(k) * peso camada de saída(k) ) * derivada da funcao de ativação(somatório(valor de entrada(i) * peso da camada escondida(ij)), 
			 	para k entre 0 e n, onde n é a quantidade de neurônios na cama de saída.
		 	 	para i entre 0 e p, onde p é a quantidade de entradas da rede + o bias.
	*/
	public void calculoInformacaoErroCamadaEscondida() {
		double valor;
		for (int i = 0; i < pe.getPesosCamadaSaida()[0].length-1; i++) { 
			valor = 0;
			for (int y = 0; y < informacaoErroCamadaSaida.length; y++) {
				valor = valor + (informacaoErroCamadaSaida[y]*pe.getPesosCamadaSaida()[y][i+1]); //somatorio(informacao de erro camada de saida(k) * peso camada de saída(k) )
			}
			informacaoErroCamadaEscondida[i] = valor*calculoDerivadaCamadaEscondida(i);		//somatorio(informacao de erro camada de saida(k) * peso camada de saída(k) ) * derivada da funcao de ativação(somatório(valor de entrada(i) * peso da camada escondida(ij)), 
		}
	}
	
	/*
	   Realiza o cálculo dos termos de ajuste dos pesos associados a cada neuronio da camada escondida:
			termo de ajuste(ij) = taxa de aprendizado * informação de erro da camada escondida(j) * valor de saída(i) 
			 	para i entre 0 e n, onde n é a quantidade de entradas da rede + o bias;
		 	 	para i entre 0 e p, onde p é a quantidade de neurônios na cama escondida.
	 */
	public void calculoTermoAjusteCamadaEscondida() {
		for (int i = 0; i < pe.getPesosCamadaEscondida().length; i++) {
			for (int j = 0; j < pe.getPesosCamadaEscondida()[i].length; j++) {
				termoAjustePesoCamadaEscondida[i][j] = pe.getTaxaAprendizado()*informacaoErroCamadaEscondida[i]*pe.getEntradasDaRede()[j]; //termo de ajuste(ij) = taxa de aprendizado * informação de erro da camada escondida(j) * valor de saída(i) 

			}
		}
	}
	
	
	/*
	  Cálculo da derivada de função de ativação para ser utilizada no cálculo da informação de erro.
		  f'(x) = f(x)*(1-f(x))
	 */
	public double calculoDerivadaCamadaEscondida(int indice) {
		return funcaoAtivacaoSigmoide(somaAuxX_V[indice])*(1-funcaoAtivacaoSigmoide(somaAuxX_V[indice]));
	}
	
	
	/*
	 Atualiza os pesos:
	 	peso anterior + termo de ajuste;
	 */
	
	public void atualizarValores() {
		for (int i = 0; i < pe.getPesosCamadaEscondida().length; i++) {
			for (int j = 0; j < pe.getPesosCamadaEscondida()[i].length; j++) {
				pe.getPesosCamadaEscondida()[i][j] = pe.getPesosCamadaEscondida()[i][j] + termoAjustePesoCamadaEscondida[i][j];
			}	
		}
		for (int i = 0; i < pe.getPesosCamadaSaida().length; i++) {
			for (int j = 0; j < pe.getPesosCamadaSaida()[i].length; j++) {
				pe.getPesosCamadaSaida()[i][j] = pe.getPesosCamadaSaida()[i][j] + termoAjustePesoCamadaSaida[i][j];
			}	
		}
	}
	
	/* 
	 * 	Função de ativação sigmóide	 
	 */
	public double funcaoAtivacaoSigmoide(double valor) {
		return (1/(1+Math.pow(Math.E, -1*(valor)))); 
	}


	public double[] getSaidaRede() {
		return saidaRede;
	}
	
	public double getErro() {
		return erro;
	}
	
	public void zeraErro() {
		erro = 0;
	}
	
	
	
	
}
