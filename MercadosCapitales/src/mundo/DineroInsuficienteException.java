package mundo;

@SuppressWarnings("serial")
public class DineroInsuficienteException extends Exception {

	public DineroInsuficienteException(String price) {
		super("<html><body>El precio de la operaci�n es "+price+",<br>fondos insuficientes</body></html>");
	}
}
