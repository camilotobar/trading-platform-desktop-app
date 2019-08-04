package mundo;

import java.text.DecimalFormat; 

public abstract class Activo implements Cloneable{

	private final String SYMBOL;
	private String informacion;
	private double delay;
	private Precio primerPrecio;
	private boolean esActual;

	public Activo(String symbol, String informacion, double delay, double spread, double precioAsk, double precioBid) {
		this.SYMBOL = symbol;
		this.informacion = informacion;
		this.delay = delay;
		esActual = false;
		primerPrecio = new Precio(spread, precioAsk, precioBid);
	}

	public boolean esActual() {
		return esActual;
	}

	public void setEsActual(boolean esActual) {
		this.esActual = esActual;
	}

	public void setInformacion(String informacion) {
		this.informacion = informacion;
	}

	public double getDelay() {
		return delay;
	}

	public void setDelay(double delay) {
		this.delay = delay;
	}

	public String getSymbol() {
		return SYMBOL;
	}

	public String getInformacion() {
		return informacion;
	}

	public Precio getPrimerPrecio() {
		return primerPrecio;
	}

	public void setPrimerPrecio(Precio primerPrecio) {
		this.primerPrecio = primerPrecio;
	}

	public Precio getUltimoPrecio() {
		Precio ultimo = primerPrecio;
		while (ultimo.getSiguiente() != null)
			ultimo = ultimo.getSiguiente();
		return ultimo;
	}

	public abstract void cambiarPrecio();

	public Precio darPrecios() {
		return primerPrecio;
	}
	
	@Override
	public String toString() {
		DecimalFormat f = new DecimalFormat("#.####");
		String mensaje = SYMBOL + " - ultimo precio del mercado: Ask [" + f.format(getUltimoPrecio().getPrecioAsk()) + "], Bid ["
				+ f.format(getUltimoPrecio().getPrecioBid()) + "] \n\t" + informacion + "\n";
		if (this instanceof RentaFija) {
			RentaFija rf = (RentaFija) this;
			mensaje += "Tasa de interés de: " + rf.getTasaInteres() + "%\n";
		} else {
			RentaVariable rv = (RentaVariable) this;
			mensaje += "Lote unitario de: " + rv.getLOTE_UNITARIO() + " unidades\n";
		}
		return mensaje;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
