package mundo;

public class Hipoteca extends RentaFija{

	private final String region;

	public Hipoteca(String symbol, String informacion, double delay, double spread, double precioAsk, double precioBid,
			double tasaInteres, String region) {
		super(symbol, informacion, delay, spread, precioAsk, precioBid, tasaInteres);
		this.region = region;
	}

	public String getRegion() {
		return region;
	}
}
