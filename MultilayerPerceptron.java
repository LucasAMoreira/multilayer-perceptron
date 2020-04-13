public class MultilayerPerceptron {
		
	private double[] saidaCEs; 	
	private double[] saidaRede;
	private double[] informacaoErroCS;
	private double[] informacaoErroCEs; 
	private double[] somaAuxZ_W;
	private double[] somaAuxX_V;
	private double[][] termoCorrecaoCS;
	private double[][] termoCorrecaoCEs;
	
	private ParametrosEntrada pe;
	private String modo;
	
	public MultilayerPerceptron(ParametrosEntrada p, String modo) {
		
		this.saidaCEs = new double[p.getPesosCEn().length + 1];
		this.saidaCEs[0] = 1;
		this.saidaRede  = new double[p.getSaidaEsperada().length];
		this.informacaoErroCEs = new double[p.getPesosCEs()[0].length - 1];
		this.informacaoErroCS = new double[p.getSaidaEsperada().length];
		this.somaAuxX_V = new double[p.getPesosCEs()[0].length - 1];
		this.somaAuxZ_W = new double[p.getSaidaEsperada().length];
		this.termoCorrecaoCS  = new double[p.getPesosCEs().length][p.getPesosCEs()[0].length];
		this.termoCorrecaoCEs = new double[p.getPesosCEn().length][p.getPesosCEn()[0].length];
		this.pe = p;
		this.modo = modo;
	}

	
	public void processarRede() {
			feedforwardStep();
			backwardStep();		
	}
	
	public void feedforwardStep() {
		calculoSaidaCamadaEscondida();
		calculoSaidaRede();
	}
	
	public void calculoSaidaCamadaEscondida() {
		double valor = 0;
		for (int i = 0; i < pe.getPesosCEn().length; i++) {
			for (int y = 0; y < pe.getPesosCEn()[i].length; y++) {
				valor = valor + (pe.getPesosCEn()[i][y] * pe.getCamadaEntrada()[y]);
			}
			somaAuxX_V[i] = valor;
			saidaCEs[i+1] = funcaoAtivacaoSigmoide(valor);
		}
		
		if (modo.equalsIgnoreCase("DEBUG")) {
			System.out.println("> SAÍDA DA CAMADA ESCONDIDA\n");
			Output.printValores("z", saidaCEs, "sn", 1);
		}
		
	}
	
	public void calculoSaidaRede() {

		double valor=0;
		for (int i = 0; i < pe.getPesosCEs().length; i++) {
			for (int y = 0; y < pe.getPesosCEs()[i].length; y++) {
				valor = valor + (pe.getPesosCEs()[i][y] * saidaCEs[y]);
			}
			somaAuxZ_W[i] = valor;
			saidaRede[i] = funcaoAtivacaoSigmoide(valor);
		}	
		if (modo.equalsIgnoreCase("DEBUG")) {
			System.out.println("> SAÍDA DA REDE\n");
			Output.printValores("y", saidaRede, "f", 0);
		}
	}
	
	public void backwardStep() {
		calculoInformacaoErroCamadaSaida();
		calculoTermoCorrecaoSaida();
		calculoInformacaoErroCamadaEscondida();
		calculoTermoCorrecaoEscondida();
		atualizarValores();
	}
	
	public void calculoInformacaoErroCamadaSaida() {
		for (int i = 0; i < somaAuxZ_W.length; i++) {
			informacaoErroCS[i] = (pe.getSaidaEsperada()[i] - saidaRede[i])*calculoDerivadaCamadaSaida(i);
		}
		
		if (modo.equalsIgnoreCase("DEBUG")) {
			System.out.println("> INFORMAÇÃO DE ERRO PARA OS NEURÔNIOS NA SAÍDA DA REDE\n");
			Output.printValores("i", informacaoErroCS, "ns", 0);
		}
	}
	
	public double calculoDerivadaCamadaSaida(int indice) {
		return funcaoAtivacaoSigmoide(somaAuxZ_W[indice])*(1-funcaoAtivacaoSigmoide(somaAuxZ_W[indice]));
	}
	
	public void calculoTermoCorrecaoSaida() {
		for (int i = 0; i < pe.getPesosCEs().length; i++) {
			for (int j = 0; j < pe.getPesosCEs()[i].length; j++) {
				termoCorrecaoCS[i][j] = pe.getTaxaAprendizado()*informacaoErroCS[i]*saidaCEs[j];
			}
		}
		if (modo.equalsIgnoreCase("DEBUG")) {
			System.out.println("> TERMOS DE AJUSTE DOS PESOS ASSOCIADOS A CADA NEURÔNIO DA CAMADA DE SAÍDA\n");
			Output.printPesos(termoCorrecaoCS, "SN");
		}
		
	}
		
	public void calculoInformacaoErroCamadaEscondida() {
		double valor;
		for (int i = 0; i < pe.getPesosCEs()[0].length-1; i++) { 
			valor = 0;
			for (int y = 0; y < informacaoErroCS.length; y++) {
				
				valor = valor + (informacaoErroCS[y]*pe.getPesosCEs()[y][i+1]);
				
			}
			informacaoErroCEs[i] = valor*calculoDerivadaCamadaEscondida(i);
		}
		if (modo.equalsIgnoreCase("DEBUG")) {
			System.out.println("> INFORMAÇÃO DE ERRO PARA OS NEURÔNIOS DA CAMA ESCONDIDA\n");
			Output.printValores("i", informacaoErroCEs, "ns", 0);
		}

	}
	
	public void calculoTermoCorrecaoEscondida() {
		for (int i = 0; i < pe.getPesosCEn().length; i++) {
			for (int j = 0; j < pe.getPesosCEn()[i].length; j++) {
				termoCorrecaoCEs[i][j] = pe.getTaxaAprendizado()*informacaoErroCEs[i]*pe.getCamadaEntrada()[j];
			}
		}
		if (modo.equalsIgnoreCase("DEBUG")) {
			System.out.println("> TERMOS DE AJUSTE DOS PESOS ASSOCIADOS A CADA NEURÔNIO DA CAMADA ESCONDIDA\n");
			Output.printPesos(termoCorrecaoCEs, "SN");
		}

		
	}
	
	public double calculoDerivadaCamadaEscondida(int indice) {
		return funcaoAtivacaoSigmoide(somaAuxX_V[indice])*(1-funcaoAtivacaoSigmoide(somaAuxX_V[indice]));
	}
	
	public void atualizarValores() {
		for (int i = 0; i < pe.getPesosCEn().length; i++) {
			for (int j = 0; j < pe.getPesosCEn()[i].length; j++) {
				pe.getPesosCEn()[i][j] = pe.getPesosCEn()[i][j] + termoCorrecaoCEs[i][j];
			}	
		}
		if (modo.equalsIgnoreCase("DEBUG")) {
			System.out.println("> NOVOS VALORES ATRIBUÍDOS AOS PESOS DA CAMADA ESCONDIDA\n");
			Output.printPesos(pe.getPesosCEn(), "i");
		}
		
		for (int i = 0; i < pe.getPesosCEs().length; i++) {
			for (int j = 0; j < pe.getPesosCEs()[i].length; j++) {
				pe.getPesosCEs()[i][j] = pe.getPesosCEs()[i][j] + termoCorrecaoCS[i][j];
			}	
		}
		
		if (modo.equalsIgnoreCase("DEBUG")) {
			System.out.println("> NOVOS VALORES ATRIBUÍDOS AOS PESOS DA CAMADA DE SAÍDA\n");
			Output.printPesos(pe.getPesosCEs(), "i");
		}

	}
	
	
	public double funcaoAtivacaoSigmoide(double valor) {
		return (1/(1+Math.pow(Math.E, -1*(valor)))); 
	}


	public double[] getSaidaRede() {
		return saidaRede;
	}
	
	
}
