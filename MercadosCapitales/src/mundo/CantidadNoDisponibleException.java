package mundo;

@SuppressWarnings("serial")
public class CantidadNoDisponibleException extends Exception {

	public CantidadNoDisponibleException(int cant) {
		super("<html><body>Cantidad no disponible, "+cant+"<br>excede su número de lots/unds</body></html>");
	}
}
