
public class ParametrosEntrada {
	
	private String valor;
	private double[] camadaEntrada;
	private double[] saidaEsperada;
	private double[][] pesosCEn;
	private double[][] pesosCEs;
	private double taxaAprendizado;
	private int epocas;
	
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public double[] getCamadaEntrada() {
		return camadaEntrada;
	}
	public void setCamadaEntrada(double[] camadaEntrada) {
		this.camadaEntrada = camadaEntrada;
	}
	public double[] getSaidaEsperada() {
		return saidaEsperada;
	}
	public void setSaidaEsperada(double[] saidaEsperada) {
		this.saidaEsperada = saidaEsperada;
	}
	public double[][] getPesosCEn() {
		return pesosCEn;
	}
	public void setPesosCEn(double[][] pesosCEn) {
		this.pesosCEn = pesosCEn;
	}
	public double[][] getPesosCEs() {
		return pesosCEs;
	}
	public void setPesosCEs(double[][] pesosCEs) {
		this.pesosCEs = pesosCEs;
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
