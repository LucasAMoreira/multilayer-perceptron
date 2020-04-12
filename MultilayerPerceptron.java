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
	
	public MultilayerPerceptron(ParametrosEntrada p) {
		
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
	}

	
	public void processarRede() {
		int epoca = 0;
		while (epoca < pe.getEpocas()) {
			feedforwardStep();
			backwardStep();
			epoca++;
		}
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
		
		Output.printValores("z", saidaCEs);
		
	}
	
	public void calculoSaidaRede() {
//		saidaCEs[0] = 1;
		double valor=0;
		for (int i = 0; i < pe.getPesosCEs().length; i++) {
			for (int y = 0; y < pe.getPesosCEs()[i].length; y++) {
				valor = valor + (pe.getPesosCEs()[i][y] * saidaCEs[y]);
			}
			somaAuxZ_W[i] = valor;
			saidaRede[i] = funcaoAtivacaoSigmoide(valor);
		}		
		Output.printValores("y", saidaRede);

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
		
		Output.printValores("i", informacaoErroCS);
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
		
		Output.printPesos(termoCorrecaoCS, "SN");
		
	}
	
	//TEM ALGUMA COISA ERRADA
	
	public void calculoInformacaoErroCamadaEscondida() {
		double valor;
		for (int i = 0; i < pe.getPesosCEs()[0].length-1; i++) { 
			valor = 0;
			for (int y = 0; y < informacaoErroCS.length; y++) {
				
				valor = valor + (informacaoErroCS[y]*pe.getPesosCEs()[y][i+1]);
				
			}
			informacaoErroCEs[i] = valor*calculoDerivadaCamadaEscondida(i);
		}
		Output.printValores("i", informacaoErroCEs);

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
		
		System.out.println("> PESOS ATUALIZADOS DA CAMADA DE ENTRADA");
		Output.printPesos(pe.getPesosCEn(), "i");

		
		for (int i = 0; i < pe.getPesosCEs().length; i++) {
			for (int j = 0; j < pe.getPesosCEs()[i].length; j++) {
				pe.getPesosCEs()[i][j] = pe.getPesosCEs()[i][j] + termoCorrecaoCS[i][j];
			}	
		}
		
		System.out.println("> PESOS ATUALIZADOS DA CAMADA ESCONDIDA");
		Output.printPesos(pe.getPesosCEs(), "i");

	}
	
	public void calculoTermoCorrecaoEscondida() {
		for (int i = 0; i < pe.getPesosCEn().length; i++) {
			for (int j = 0; j < pe.getPesosCEn()[i].length; j++) {
				termoCorrecaoCEs[i][j] = pe.getTaxaAprendizado()*informacaoErroCEs[i]*pe.getCamadaEntrada()[j];
			}
		}
		
		Output.printPesos(termoCorrecaoCEs, "SN");

		
	}
	
	public double funcaoAtivacaoSigmoide(double valor) {
		return (1/(1+Math.pow(Math.E, -1*(valor)))); 
	}
	
}
