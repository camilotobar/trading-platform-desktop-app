package mundo;

import java.util.Random;

public class RentaVariable extends Activo{

	private final int LOTE_UNITARIO;
	private int cantidadLotes;
	private Random rdm;

	public RentaVariable(String symbol, String informacion, double delay, double spread, double precioAsk, double precioBid, int loteUnitario) {
		super(symbol, informacion, delay, spread, precioAsk, precioBid);
		this.LOTE_UNITARIO = loteUnitario;
		rdm = new Random();
		cantidadLotes = 0;
	}
		
	public int getLOTE_UNITARIO() {
		return LOTE_UNITARIO;
	}
	public int getCantidadLotes() {
		return cantidadLotes;
	}
	public void setCantidadLotes(int cantidadLotes) {
		this.cantidadLotes = cantidadLotes;
	}

	@Override
	public void cambiarPrecio() {
		Precio penultimo = getUltimoPrecio();
		int random = rdm.nextInt(15);
		String number = "0.00"+random;
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
