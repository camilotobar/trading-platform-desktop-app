package mundo;

@SuppressWarnings("serial")
public class CantidadNoDisponibleException extends Exception {

	public CantidadNoDisponibleException(int cant) {
		super("<html><body>Cantidad no disponible, "+cant+"<br>excede su n�mero de lots/unds</body></html>");
	}
}
