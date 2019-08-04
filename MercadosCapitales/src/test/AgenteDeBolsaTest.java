package test;

import org.junit.Test;
import junit.framework.TestCase;
import mundo.AgenteDeBolsa;
import mundo.Corredor;
import mundo.YaExisteCorredorException;

public class AgenteDeBolsaTest extends TestCase {

	/**
	 * Agente de bolsa particular para pruebas
	 */
	private AgenteDeBolsa agenteDeBolsa;

	/**
	 * Se crea un agente de bolsa y se inicializan los símbolos
	 */
	public void setUpEscenario1() {
		agenteDeBolsa = new AgenteDeBolsa();
	}

	/**
	 * Se inicializan los corredores.
	 * 
	 * <pre>
	 * :ES NECESARIO TENER UN ARCHIVO DE PRUEBA CON CORREDORES
	 * </pre>
	 */
	public void setUpEscenario2() {
		agenteDeBolsa = new AgenteDeBolsa();
		agenteDeBolsa.cargarCorredores();
	}

	/**
	 * Se crea un escenario con 4 corredores distintos
	 * 
	 * @throws YaExisteCorredorException
	 */
	public void setUpEscenario3() throws YaExisteCorredorException {
		agenteDeBolsa = new AgenteDeBolsa();
		agenteDeBolsa.cargarCorredores();
		Corredor corredor1 = new Corredor("Frodo", "PrancingPony", "80444258", 251000);
		Corredor corredor2 = new Corredor("Boromir", "Gondor", "1555778469", 200);
		Corredor corredor3 = new Corredor("Gandalf", "theWhite", "60214589", 8201555);
		Corredor corredor4 = new Corredor("Sauron", "eye-C-U", "1235847", 15000300);
		
		agenteDeBolsa.agregarCorredor(corredor1, agenteDeBolsa.getCorredorRaiz());
		agenteDeBolsa.agregarCorredor(corredor2, agenteDeBolsa.getCorredorRaiz());
		agenteDeBolsa.agregarCorredor(corredor3, agenteDeBolsa.getCorredorRaiz());
		agenteDeBolsa.agregarCorredor(corredor4, agenteDeBolsa.getCorredorRaiz());
	}

	/**
	 * Se crea un escenario con corredores con usuarios repetidos
	 * 
	 * @throws YaExisteCorredorException
	 */
	public void setUpEscenario4() throws YaExisteCorredorException {
		agenteDeBolsa = new AgenteDeBolsa();
		agenteDeBolsa.cargarCorredores();
		Corredor corredor1 = new Corredor("Frodo", "PrancingPony", "80444258", 251000);
		Corredor corredor2 = new Corredor("Boromir", "Gondor", "1555778469", 200);
		Corredor corredor3 = new Corredor("Gandalf", "theWhite", "60214589", 8201555);
		Corredor corredor4 = new Corredor("Borom", "Gondor", "555544", 15000300);

		agenteDeBolsa.agregarCorredor(corredor1, agenteDeBolsa.getCorredorRaiz());
		agenteDeBolsa.agregarCorredor(corredor2, agenteDeBolsa.getCorredorRaiz());
		agenteDeBolsa.agregarCorredor(corredor3, agenteDeBolsa.getCorredorRaiz());
		agenteDeBolsa.agregarCorredor(corredor4, agenteDeBolsa.getCorredorRaiz());
	}

	/**
	 * Se verifica que al inicializar los símbolos estos sean diferente de null
	 * y el tamaño de la colección sea mayor a 0
	 */
	@Test
	public void testInicializarSimbolos() {
		setUpEscenario1();
		assertTrue(agenteDeBolsa.getSimbolos() != null && agenteDeBolsa.getSimbolos().length > 0);
	}

	/**
	 * Se verifica que se carguen correctamente los corredores y se establezca
	 * un corredor como el actual
	 */
	@Test
	public void testCargarCorredores() {
		setUpEscenario2();
		assertTrue(agenteDeBolsa.getCorredores() != null && agenteDeBolsa.getCorredores().getNombre() != null
				&& agenteDeBolsa.getCorredorActual() != null);
	}

	/**
	 * Prueba que al agregar varios corredores distintos no se lance excepcion.
	 */
	@Test
	public void testAgregarCorredor() {
		try {
			// Carga el escenario 3, donde se agregan 4 corredores con
			// diferentes username
			setUpEscenario3();
		} catch (YaExisteCorredorException e) {
			fail("Excepción no esperada YaExisteCorredorException");
		}
	}

	/**
	 * Prueba que el corredor que se encontró sea igual al creado, según sus
	 * datos.
	 */
	@Test
	public void testBuscarCorredor() {
		try {
			setUpEscenario3();
			Corredor corredor = agenteDeBolsa.buscarCorredor("theWhite", agenteDeBolsa.getCorredorRaiz());

			// Prueba que el corredor encontrado tiene las mismas
			// características que el cargado en el escenario
			assertEquals(corredor.toString(),
					"Nombre: Gandalf\nUsername: theWhite\nIdentificación: 60214589\nBalance: 8201555.0");
		} catch (YaExisteCorredorException e) {
			fail("Excepción no esperada YaExisteCorredorException");
		}
	}

	/**
	 * Prueba que el corredor que encuentra sea igual al creado, según sus
	 * datos.
	 */
	@Test
	public void testBuscarCorredorInexistente() {
		try {
			setUpEscenario3();
			Corredor corredor = agenteDeBolsa.buscarCorredor("camilotobar", agenteDeBolsa.getCorredorRaiz());
			
			// Verifica que no existe ningún usuario <camilotobar> en el sistema
			assertNull(corredor);
		} catch (YaExisteCorredorException e) {
			fail("Excepción no esperada YaExisteCorredorException");
		}
	}

	
	/**
	 * Prueba que el programa evite agregar dos corredores con el mismo usuario,
	 * aún cuando los demás atributos son diferentes
	 */
	@Test
	public void testAgregarRepetidos() {
		try {
			// Carga el escenario 4, que intenta agregar dos corredores con
			// igual usuario
			setUpEscenario4();
			fail("Se esperaba excepción YaExisteCorredorException");
		} catch (YaExisteCorredorException e) {
		}
	}	
}
