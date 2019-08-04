package hilos;

import interfaz.InterfazMercadosCapitales;
import mundo.*;

public class HiloActivo extends Thread {

	private Activo activo;
	private InterfazMercadosCapitales ventana;

	public HiloActivo(Activo activo, InterfazMercadosCapitales ventana) {
		this.ventana = ventana;
		this.activo = activo;
	}

	@Override
	public void run() {
		while (isAlive()) {
			try {
				activo.cambiarPrecio();
				sleep((long) activo.getDelay());
				if (activo.esActual()) {
					ventana.refrescar();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public Activo getActivo() {
		return activo;
	}
	public void setActivo(Activo activo) {
		this.activo = activo;
	}
}
