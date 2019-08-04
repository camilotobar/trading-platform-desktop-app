package mundo;

@SuppressWarnings("serial")
public class YaExisteCorredorException extends Exception{

	public YaExisteCorredorException(String user) {
		super("Ya existe un corredor con usuario "+user);
	}
}
