package mundo;

public class Divisa extends RentaVariable{

	public final static String ALTA = "Alta";
	public final static String MEDIA = "Media";
	public final static String BAJA = "Baja";
	private final String gobierno;
	private String volatilidad;
	
	public Divisa(String symbol, String informacion, double delay, double spread, double precioAsk, double precioBid,
			int loteUnitario, String gobierno, String volatilidad) {
		super(symbol, informacion, delay, spread, precioAsk, precioBid, loteUnitario);
		this.gobierno = gobierno;
		this.volatilidad = volatilidad;
	}

	public String getVolatilidad() {
		return volatilidad;
	}

	public void setVolatilidad(String volatilidad) {
		this.volatilidad = volatilidad;
	}

	public String getGobierno() {
		return gobierno;
	}
}
