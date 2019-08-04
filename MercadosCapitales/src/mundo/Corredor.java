package mundo;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.*;

public class Corredor implements Serializable {

	private static final long serialVersionUID = 1L;
	private String nombre;
	private String username;
	private String identificacion;
	private double balance;
	private Corredor izq;
	private Corredor der;
	private ArrayList<Activo> portafolio;

	public Corredor(String nombre, String user, String identificacion, double balance) {
		this.nombre = nombre;
		this.username = user;
		this.identificacion = identificacion;
		this.balance = balance;
		portafolio = new ArrayList<Activo>();
	}

	public Corredor getIzq() {
		return izq;
	}

	public void setIzq(Corredor izq) {
		this.izq = izq;
	}

	public Corredor getDer() {
		return der;
	}

	public void setDer(Corredor der) {
		this.der = der;
	}

	public String getUser() {
		return username;
	}

	public void setUser(String user) {
		this.username = user;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public ArrayList<Activo> getPortafolio() {
		return portafolio;
	}

	public void setPortafolio(ArrayList<Activo> portafolio) {
		this.portafolio = portafolio;
	}
		
	public Activo buscarActivo(String symbol) {
		Activo activo = null;
		if(!portafolio.isEmpty())
			for (Activo act : portafolio) {
				if (act.getSymbol().equalsIgnoreCase(symbol)) {
					activo = act;
					break;
				}
			}
		return activo;
	}
	
	public void transar(Activo activo, int cantidad, String tradeType) throws DineroInsuficienteException, CantidadNoDisponibleException, CloneNotSupportedException{
		double price;
		Activo act;
		int cant;
		ordenarPortafolio();
		
		if (tradeType.equals(Precio.BUY)) {
			price = activo.getUltimoPrecio().getPrecioAsk() * cantidad * 1.0;
			if (price > balance) {
				DecimalFormat f = new DecimalFormat("0.##");
				throw new DineroInsuficienteException(f.format(price));
			}
			act = (buscarActivo(activo.getSymbol()) != null) ? portafolio.get(getPositionBinarioActivo(activo.getSymbol(), 0, portafolio.size()))
					: activo;
			if (act instanceof RentaFija) {
				RentaFija rf = (RentaFija) act;
				cant = rf.getCantidad() + cantidad;
				rf.setCantidad(cant);
				act = rf;
			} else {
				RentaVariable rv = (RentaVariable) act;
				cant = rv.getCantidadLotes() + (cantidad / rv.getLOTE_UNITARIO());
				rv.setCantidadLotes(cant);
				act = rv;
			}
			
			balance -= price;
			if (buscarActivo(activo.getSymbol()) != null)
				portafolio.remove(getPositionBinarioActivo(activo.getSymbol(), 0, portafolio.size()));
			portafolio.add(act);
			
		} else {
			price = activo.getUltimoPrecio().getPrecioBid() * cantidad * 1.0;
			act = (getPositionBinarioActivo(activo.getSymbol(), 0, portafolio.size()) != -1) ? portafolio.get(getPositionBinarioActivo(activo.getSymbol(), 0, portafolio.size()))
					: activo;
			if (act instanceof RentaFija) {
				RentaFija rf = (RentaFija) act;
				if (rf.getCantidad() < cantidad)
					throw new CantidadNoDisponibleException(cantidad);
				cant = rf.getCantidad() - cantidad;
				rf.setCantidad(cant);
				act = rf;
				balance += price;
				if (buscarActivo(activo.getSymbol()) != null)
					portafolio.remove(getPositionBinarioActivo(activo.getSymbol(), 0, portafolio.size()));
				if (rf.getCantidad() != 0)
					portafolio.add(act);
			} else {
				RentaVariable rv = (RentaVariable) act;
				if (rv.getCantidadLotes() == 0 || rv.getCantidadLotes() < (cantidad / rv.getLOTE_UNITARIO()))
					throw new CantidadNoDisponibleException(cantidad);
				cant = rv.getCantidadLotes() - (cantidad / rv.getLOTE_UNITARIO());
				rv.setCantidadLotes(cant);
				act = rv;
				balance += price;
				if (buscarActivo(activo.getSymbol()) != null)
					portafolio.remove(getPositionBinarioActivo(activo.getSymbol(), 0, portafolio.size()));
				if (rv.getCantidadLotes() != 0)
					portafolio.add(act);
			}
		}

	}
	
	public int getPositionBinarioActivo(String symbol, int primero, int ultimo) {
		int a = 0;
		if(portafolio.size() == 0 || primero > ultimo)
			a--;
		int medio = (primero+ultimo)/2;
		if (a != -1) {
			if (portafolio.get(medio).getSymbol().compareTo(symbol) == 0)
				a = medio;
			else if (portafolio.get(medio).getSymbol().compareTo(symbol) > 0)
				a = getPositionBinarioActivo(symbol, primero, medio - 1);
			else
				a = getPositionBinarioActivo(symbol, medio + 1, ultimo);
		}
		return a;
	}
	
	public void ordenarPortafolio(){
		for (int i = 1; i < portafolio.size(); i++) {
			for (int j = i; j > 0 && (portafolio.get(j-1).getSymbol().compareTo(portafolio.get(j).getSymbol()) > 0); j--) {
				Activo evaluando = portafolio.get(j);
				portafolio.set(j, portafolio.get(j-1));
				portafolio.set(j-1, evaluando);
			}
		}
	}

	@Override
	public String toString() {
		return "Nombre: " + nombre + "\nUsername: " + username + "\nIdentificación: " + identificacion + "\nBalance: "
				+ balance;
	}
}
