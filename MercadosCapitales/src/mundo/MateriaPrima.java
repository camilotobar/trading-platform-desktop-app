package mundo;

public class MateriaPrima extends RentaVariable{

	public final static String METAL = "Metal";
	public final static String PETROLEO = "Petróleo";
	private final String tipo;
	
	public MateriaPrima(String symbol, String informacion, double delay, double spread, double precioAsk, double precioBid,
			int loteUnitario, String tipo) {
		super(symbol, informacion, delay, spread, precioAsk, precioBid, loteUnitario);
		this.tipo = tipo;
	}
	
	public String getTipo() {
		return tipo;
	}
}
