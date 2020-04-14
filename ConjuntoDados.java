
public enum ConjuntoDados {
	
	AND(4, 2, 1, 63, 7), XOR(4, 2, 1, 2, 1), OR(4, 2, 1, 2, 1), CARACTERES(21, 63, 7, 2, 1);
	
	private int linhasDados;
	private int colunasDados;
	private int colunasRespostas;
	private int neuroniosEntrada;
	private int neuroniosSaida;
	
	private ConjuntoDados(int ld, int cd, int cr, int ne, int ns) {
		linhasDados = ld;
		colunasDados = cd;
		colunasRespostas = cr;
		neuroniosEntrada = ne;
		neuroniosSaida = ns;
		
	}

	public int getLinhasDados() {
		return linhasDados;
	}

	public int getColunasDados() {
		return colunasDados;
	}

	public int getColunasRespostas() {
		return colunasRespostas;
	}

	public int getNeuroniosEntrada() {
		return neuroniosEntrada;
	}

	public int getNeuroniosSaida() {
		return neuroniosSaida;
	}
	
	
	
	
	
	
	
	
	
}
