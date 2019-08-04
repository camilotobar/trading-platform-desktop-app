package mundo;

public class Precio {
	
	public final static String BUY = "Comprar";
	public final static String SELL = "Vender";
	private final double SPREAD;
	private double precioAsk;
	private double precioBid;
	private Precio siguiente;
	private Precio anterior;
	
	public Precio(double spread, double precioAsk, double precioBid) {
		this.SPREAD = spread;
		this.precioAsk = precioAsk;
		this.precioBid = precioBid;
	}
	
	public double getSpread() {
		return SPREAD;
	}
	public double getPrecioAsk() {
		return precioAsk;
	}
	public void setPrecioAsk(double precioAsk) {
		this.precioAsk = precioAsk;
	}
	public double getPrecioBid() {
		return precioBid;
	}
	public void setPrecioBid(double precioBid) {
		this.precioBid = precioBid;
	}
	public Precio getSiguiente() {
		return siguiente;
	}
	public void setSiguiente(Precio siguiente) {
		this.siguiente = siguiente;
	}
	public Precio getAnterior() {
		return anterior;
	}
	public void setAnterior(Precio anterior) {
		this.anterior = anterior;
	}
}
