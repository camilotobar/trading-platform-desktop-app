package test;

import junit.framework.*;
import org.junit.Test;
import mundo.*;

public class CorredorTest extends TestCase {

	/**
	 * Corredor particular para pruebas
	 */
	private Corredor corredor;

	/**
	 * Crea un nuevo escenario, en el cual el corredor tiene ya disponible un
	 * activo tipo materia prima en su portafolio
	 * 
	 * @throws CloneNotSupportedException
	 * @throws DineroInsuficienteException
	 * @throws CantidadNoDisponibleException
	 */
	private void setupEscenario1()
			throws DineroInsuficienteException, CantidadNoDisponibleException, CloneNotSupportedException {
		corredor = new Corredor("Frodo", "PrancingPony", "80444258", 50000);

		Activo activo = new MateriaPrima("XAG", "Plata ref. Común", 3000, 0.0012, 16.4718, 16.4706, 100,
				MateriaPrima.METAL);
		int cantidad = 500;
		String tradeType = Precio.BUY;

		corredor.transar(activo, cantidad, tradeType);
	}

	/**
	 * Crea un nuevo escenario donde el corredor no tiene ningún activo aún en
	 * su portafolio
	 */
	private void setupEscenario2() {
		corredor = new Corredor("Gandalf", "theWhite", "60214589", 100000);
	}

	/**
	 * Crea un nuevo escenario donde el corredor tiene disponible 4 activos en
	 * su portafolio
	 * 
	 * @throws DineroInsuficienteException
	 * @throws CantidadNoDisponibleException
	 * @throws CloneNotSupportedException
	 */
	private void setupEscenario3()
			throws DineroInsuficienteException, CantidadNoDisponibleException, CloneNotSupportedException {
		corredor = new Corredor("Boromir", "Gondor", "1555778469", 1000000);

		Activo activo1 = new MateriaPrima("XAG", "Plata ref. Común", 3000, 0.0012, 16.4718, 16.4706, 100,
				MateriaPrima.METAL);
		Activo activo2 = new Divisa("JPY", "Yen Japonés", 2000, 0.0010, 0.7396, 0.7386, 100, "Japón", Divisa.ALTA);
		Activo activo3 = new MateriaPrima("WTI", "Petróleo ref. americana", 1000, 0.0012, 42.2518, 42.2506, 100,
				MateriaPrima.PETROLEO);
		Activo activo4 = new Accion("MSFT", "Acción Microsoft Corp.", 8000, 0.0021, 60.5322, 60.5301, 100,
				"Microsoft Corporation");

		int cantidad1 = 500;
		int cantidad2 = 1000;
		String tradeType = Precio.BUY;

		corredor.transar(activo1, cantidad2, tradeType);
		corredor.transar(activo2, cantidad2, tradeType);
		corredor.transar(activo3, cantidad1, tradeType);
		corredor.transar(activo4, cantidad1, tradeType);
	}

	/**
	 * Realiza una prueba donde se busca binariamente un activo ya disponible en
	 * el portafolio del corredor
	 * 
	 * @throws CloneNotSupportedException
	 * @throws CantidadNoDisponibleException
	 * @throws DineroInsuficienteException
	 */
	@Test
	public void testBuscarActivoBinariamente()
			throws DineroInsuficienteException, CantidadNoDisponibleException, CloneNotSupportedException {
		setupEscenario3();

		// Ordena el portafolio del corredor para poder realizar la búsqueda
		// binaria
		corredor.ordenarPortafolio();

		String symbol = "MSFT";
		int pos = corredor.getPositionBinarioActivo(symbol, 0, corredor.getPortafolio().size());
		Activo activo = corredor.getPortafolio().get(pos);

		// Compara si el activo que se encontró es el indicado
		assertEquals(activo.getSymbol(), symbol);

	}

	/**
	 * Realiza una prueba donde intenta transar un activo en condiciones
	 * exitosas
	 * 
	 * @throws CloneNotSupportedException
	 * @throws CantidadNoDisponibleException
	 * @throws DineroInsuficienteException
	 */
	@Test
	public void testTransarSinExcepciones()
			throws DineroInsuficienteException, CantidadNoDisponibleException, CloneNotSupportedException {
		setupEscenario1();
		Activo activo = new Divisa("JPY", "Yen Japonés", 2000, 0.0010, 0.7396, 0.7386, 100, "Japón", Divisa.ALTA);
		int cantidad = 2000;
		String tradeType = Precio.BUY;

		// Datos antes y después de la compra
		double balanceAntes = corredor.getBalance();
		corredor.transar(activo, cantidad, tradeType);
		int lotesComprados = cantidad / 100;
		double balanceEsperado = balanceAntes - (activo.getPrimerPrecio().getPrecioAsk() * cantidad);

		// Verifica que la compra fue exitosa y se trata de el número de lotes
		// correcto
		assertEquals(corredor.getBalance(), balanceEsperado);
		Divisa divisaComprada = (Divisa) corredor.buscarActivo("JPY");
		assertEquals(divisaComprada.getCantidadLotes(), lotesComprados);
	}

	/**
	 * Realiza una prueba donde se intenta realizar una negociación de mercado,
	 * sabiendo que el corredor no tiene cantidades disponibles del activo a
	 * tranzar
	 * 
	 * @throws DineroInsuficienteException
	 * @throws CantidadNoDisponibleException
	 * @throws CloneNotSupportedException
	 */
	@Test
	public void testTransarCantidadNoDisponible()
			throws DineroInsuficienteException, CantidadNoDisponibleException, CloneNotSupportedException {
		setupEscenario1();
		Activo activo = new MateriaPrima("XAG", "Plata ref. Común", 3000, 0.0012, 16.4722, 16.4710, 100,
				MateriaPrima.METAL);
		int cantidad = 1000;
		String tradeType = Precio.SELL;

		// Lanza la excepción debido a cantidad no disponible
		try {
			corredor.transar(activo, cantidad, tradeType);
			fail("Se esperaba excepción CantidadNoDisponibleException");
		} catch (CantidadNoDisponibleException cnde) {
		}
	}

	/**
	 * Prueba que no se pueda transar una cantidad de lotes o unds de un activo
	 * debido a dinero insuficiente en el balance
	 * 
	 * @throws DineroInsuficienteException
	 * @throws CantidadNoDisponibleException
	 * @throws CloneNotSupportedException
	 */
	@Test
	public void testTransarDineroInsuficiente()
			throws DineroInsuficienteException, CantidadNoDisponibleException, CloneNotSupportedException {
		setupEscenario2();
		Activo activo = new Bono("BONAC", "Bono soberano Argentina", 20000, 0.0530, 25550.1535, 25550.1025, 0.06, 0.02);
		int cantidad = 10;
		String tradeType = Precio.BUY;

		try {
			// Lanza la excepción debido a dinero insuficiente
			corredor.transar(activo, cantidad, tradeType);

			fail("Se esperaba excepción DineroInsuficienteException");
		} catch (DineroInsuficienteException cnde) {
		}
	}

	/**
	 * Realiza una prueba donde se ordena el portafolio y se verifica su forma
	 * ascendente
	 * 
	 * @throws CloneNotSupportedException
	 * @throws CantidadNoDisponibleException
	 * @throws DineroInsuficienteException
	 */
	@Test
	public void testOrdenarPortafolio()
			throws DineroInsuficienteException, CantidadNoDisponibleException, CloneNotSupportedException {
		setupEscenario3();

		corredor.ordenarPortafolio();
		boolean ordenado = true;

		// Busca si los activos son ascendentes, sabiendo que el criterio de
		// ordenamiento son los símbolos
		for (int i = 0; i < corredor.getPortafolio().size() - 1; i++) {
			if (corredor.getPortafolio().get(i).getSymbol()
					.compareTo(corredor.getPortafolio().get(i + 1).getSymbol()) > 0) {
				ordenado = false;
				break;
			}
		}

		// Verifica si se cumplió el ordenamiento
		assertTrue(ordenado);
	}

}
