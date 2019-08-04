package mundo;
import java.util.*;

public class RentaFija extends Activo{
	
	public final static double CINCO_ANHOS = 0.15;
	public final static double SEIS_MESES = 0.02;
	private final double TASA_INTERES;
	private int cantidad;
	private Random rdm;

	public RentaFija(String symbol, String informacion, double delay, double spread, double precioAsk, double precioBid, double tasaInteres) {
		super(symbol, informacion, delay, spread, precioAsk, precioBid);
		this.TASA_INTERES = tasaInteres;
		rdm = new Random();
		cantidad = 0;
	}

	public double getTasaInteres() {
		return TASA_INTERES;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	@Override
	public void cambiarPrecio() {
		Precio penultimo = getUltimoPrecio();
		int random = rdm.nextInt(50);
		String number = "0.0"+random;
		double num = Double.parseDouble(number);
		double spread = penultimo.getSpread();
		double ask = (rdm.nextInt(2) == 1)? (penultimo.getPrecioAsk() + num): (penultimo.getPrecioAsk() - num);
		double bid = ask - spread;
		Precio nuevoPrecio = new Precio(spread, ask, bid);
		nuevoPrecio.setAnterior(penultimo);
		penultimo.setSiguiente(nuevoPrecio);
		int i = 79;
		while(penultimo.getAnterior() != null && i > 0){
			penultimo = penultimo.getAnterior();
			i--;
		}
		setPrimerPrecio(penultimo);
	}
}
