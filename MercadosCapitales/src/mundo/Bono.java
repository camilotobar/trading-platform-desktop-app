package mundo;

public class Bono extends RentaFija{

	private double coeficienteRiesgo;

	public Bono(String symbol, String informacion, double delay, double spread, double precioAsk, double precioBid,
			double tasaInteres, double coeficienteRiesgo) {
		super(symbol, informacion, delay, spread, precioAsk, precioBid, tasaInteres);
		this.coeficienteRiesgo = coeficienteRiesgo;
	}

	public double getCoeficienteRiesgo() {
		return coeficienteRiesgo;
	}

	public void setCoeficienteRiesgo(double coeficienteRiesgo) {
		this.coeficienteRiesgo = coeficienteRiesgo;
	}
}
