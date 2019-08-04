package hilos;

import java.text.*;
import java.util.Calendar;
import interfaz.*;
public class HiloTimer extends Thread{

	private InterfazMercadosCapitales ventana;
	private DateFormat dateFormat;
	private Calendar cal;
	
	public HiloTimer(InterfazMercadosCapitales v) {
		ventana = v;
		dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM");
	}
	
	@Override
	public void run() {
		while (isAlive()) {
			try {
				cal = Calendar.getInstance();
				ventana.actualizarTimer(dateFormat.format(cal.getTime()));
				sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
