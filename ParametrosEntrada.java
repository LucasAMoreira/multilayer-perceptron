
public class ParametrosEntrada {
	
	private String valor;
	private double[] entradasDaRede;
	private double[] saidaEsperada;
	private double[][] pesosCamadaEscondida;
	private double[][] pesosCamadaSaida;
	private double taxaAprendizado;
	private int epocas;
	
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public double[] getEntradasDaRede() {
		return entradasDaRede;
	}
	public void setEntradasDaRede(double[] entradasDaRede) {
		this.entradasDaRede = entradasDaRede;
	}
	public double[] getSaidaEsperada() {
		return saidaEsperada;
	}
	public void setSaidaEsperada(double[] saidaEsperada) {
		this.saidaEsperada = saidaEsperada;
	}
	public double[][] getPesosCamadaEscondida() {
		return pesosCamadaEscondida;
	}
	public void setPesosCamadaEscondida(double[][] pesosCamadaEscondida) {
		this.pesosCamadaEscondida = pesosCamadaEscondida;
	}
	public double[][] getPesosCamadaSaida() {
		return pesosCamadaSaida;
	}
	public void setPesosCamadaSaida(double[][] pesosCamadaSaida) {
		this.pesosCamadaSaida = pesosCamadaSaida;

	}
	public double getTaxaAprendizado() {
		return taxaAprendizado;
	}
	public void setTaxaAprendizado(double taxaAprendizado) {
		this.taxaAprendizado = taxaAprendizado;
	}
	public int getEpocas() {
		return epocas;
	}
	public void setEpocas(int epocas) {
		this.epocas = epocas;
	}

}
