package mundo;

public class Accion extends RentaVariable{
	
	private final String COMPANHIA;

	public Accion(String symbol, String informacion, double delay, double spread, double precioAsk, double precioBid,
			int loteUnitario, String companhia) {
		super(symbol, informacion, delay, spread, precioAsk, precioBid, loteUnitario);
		this.COMPANHIA = companhia;
	}

	public String getCOMPANHIA() {
		return COMPANHIA;
	}
}
