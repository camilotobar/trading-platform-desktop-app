package interfaz;

import mundo.*;
import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import hilos.*;

@SuppressWarnings("serial")
public class InterfazMercadosCapitales extends JFrame {

	/**
	 * Constantes
	 */
	public static final int HEIGHT = 600;
	public static final int WIDTH = 1200;

	/**
	 * Atributos de la clase
	 */
	private AgenteDeBolsa agente;
	private PanelGrafica panelGrafica;
	private PanelActivos panelActivos;
	private PanelGestion panelGestion;
	private BarraArchivo menuVer;
	private HiloActivo[] activos;
	private HiloActivo activoActual;
	private HiloTimer tiempoActual;

	/**
	 * Constructor de la clase
	 */
	public InterfazMercadosCapitales() {
		setTitle("Mercados de Capitales : :");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		getContentPane().setBackground(Color.DARK_GRAY);
		setSize(HEIGHT, WIDTH);
		setLayout(new BorderLayout());
		agente = new AgenteDeBolsa();
		agente.cargarCorredores();
		try {
			inicializarActivos();
		} catch (Exception e) {
		}
		activoActual = activos[0];
		activoActual.getActivo().setEsActual(true);
		panelGrafica = new PanelGrafica(this);
		panelActivos = new PanelActivos(this);
		panelGestion = new PanelGestion(this);
		menuVer = new BarraArchivo(this);

		tiempoActual = new HiloTimer(this);
		tiempoActual.start();

		JMenuBar miMenuBar = new JMenuBar();
		miMenuBar.add(menuVer);
		setJMenuBar(miMenuBar);

		JPanel panelCentro = new JPanel();
		panelCentro.setLayout(new FlowLayout());
		panelCentro.add(panelGrafica);
		panelCentro.add(panelActivos);

		add(panelActivos, BorderLayout.EAST);
		add(panelGrafica, BorderLayout.CENTER);
		add(panelGestion, BorderLayout.SOUTH);
		pack();
	}

	/**
	 * Inicializa y corre los hilos. Un hilo por cada activo
	 */
	public void inicializarActivos() {
		activos = new HiloActivo[agente.getSimbolos().length];
		for (int i = 0; i < agente.getSimbolos().length; i++) {
			activos[i] = new HiloActivo(agente.getSimbolos()[i], this);
			activos[i].start();
		}
	}

	/**
	 * Da el hilo actual que se est? visualizando en el programa.
	 * 
	 * @return El hilo actual
	 */
	public HiloActivo getActivoActual() {
		return activoActual;
	}

	/**
	 * Intenta comprar una cantidad determinada de lotes de un activo
	 * 
	 * @param cant
	 *            - Cantidad de lotes a transar
	 * @param tradeType
	 *            - Tipo de activo
	 */
	public void transar(String cant, String tradeType) {
		try {
			int cantidad = Integer.parseInt(cant);
			if (agente.getCorredorActual() != null) {
				Activo aTransar = (Activo) activoActual.getActivo().clone();
				if (aTransar instanceof RentaVariable) {
					RentaVariable rv = (RentaVariable) aTransar;
					if (cantidad < rv.getLOTE_UNITARIO())
						throw new Exception(
								"No alcanza la cantidad m?nima de lote (" + rv.getLOTE_UNITARIO() + " unds)");
				}
				agente.getCorredorActual().transar(aTransar, cantidad, tradeType);
				panelActivos.actualizar();
				panelActivos.actualizarCantidad(
						agente.getCorredorActual().buscarActivo(activoActual.getActivo().getSymbol()));
				JOptionPane.showMessageDialog(this, "?Operaci?n exitosa!");
			} else
				JOptionPane.showMessageDialog(this, "No hay corredores para transar");
		} catch (NumberFormatException nfe) {
			JOptionPane.showMessageDialog(this, "Digite una cantidad v?lida");
		} catch (DineroInsuficienteException die) {
			JOptionPane.showMessageDialog(this, die.getMessage());
		} catch (CantidadNoDisponibleException cnde) {
			JOptionPane.showMessageDialog(this, cnde.getMessage());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
	}

	/**
	 * Agregar un nuevo corredor al programa Pre: Los datos deben venir ya de
	 * acorde a lo que requiere el constructor. Todo contro sobre estos se
	 * realiz? previamente.
	 * 
	 * @param datos
	 *            - Los datos del corredor
	 */
	public void agregarCorredor(String[] datos) {
		Corredor c;
		try {
			c = new Corredor(datos[0], datos[1], datos[2], Double.parseDouble(datos[3]));
			agente.agregarCorredor(c, agente.getCorredores());
			agente.setCorredorActual(c);
			panelActivos.actualizar();
			panelActivos.actualizarCantidad(null);
			JOptionPane.showMessageDialog(this, "Agregado correctamente");
		} catch (YaExisteCorredorException e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		} catch (Exception exc) {
			JOptionPane.showMessageDialog(this, exc.getMessage());
		}
	}

	public AgenteDeBolsa darMundo() {
		return agente;
	}

	public static void main(String[] args) {
		InterfazMercadosCapitales ventana = new InterfazMercadosCapitales();
		ventana.setVisible(true);
		ventana.setSize(WIDTH, HEIGHT);
		ventana.centrar();
	}

	public void centrar() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(((screenSize.width - getWidth()) / 2), ((screenSize.height - getHeight()) / 2));
	}

	@Override
	public void dispose() {
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(AgenteDeBolsa.RUTA_CORREDORES));
			out.writeObject(agente.getCorredores());
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.exit(0);
		}
	}

	public void refrescar() {
		panelGrafica.repaint();
	}

	public void generarReporte() {
		String reporte = "Reporte activos " + new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss").format(new Date()) + "\n\n"
				+ agente.listaActivos();
		try {
			PrintWriter p = new PrintWriter(new File("./docs/Reporte activos ("
					+ (new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss").format(new Date())) + ").txt"));
			p.write(reporte);
			p.close();
			JOptionPane.showMessageDialog(this,
					"<html><body>Reporte de activos generado correctamente,<br>encuentrelo en la carpeta docs</body></html>");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void cambiarActivoActual(String symbol) {
		if (!activoActual.getActivo().getSymbol().equals(symbol)) {
			activoActual.getActivo().setEsActual(false);
			panelGrafica.repaint();
			for (int i = 0; i < activos.length; i++) {
				if (activos[i].getActivo().getSymbol().equals(symbol)) {
					activoActual = activos[i];
					activos[i].getActivo().setEsActual(true);
					panelActivos.actualizar();
				}
			}
		}
	}

	/**
	 * Intenta cargar un corredor segun un usuario dado. notifica si no existe
	 * ese corredor.
	 * 
	 * @param usuario
	 *            - El usuario del corredor que se desea encontrar
	 */
	public void cargarUsuario(String usuario) {
		Corredor previo = agente.getCorredorActual();
		Corredor c = (usuario == null) ? null : agente.buscarCorredor(usuario, agente.getCorredorRaiz());
		agente.setCorredorActual(c != null ? c : previo);
		if (c == null)
			JOptionPane.showMessageDialog(this, "No se encontr? un corredor con el usuario " + usuario, "Error",
					JOptionPane.WARNING_MESSAGE);
		else
			panelActivos.actualizarCantidad(c.buscarActivo(activoActual.getActivo().getSymbol()));
		panelActivos.actualizar();
	}

	public void masInfoActivo() {
		Activo activo;
		try {
			activo = (Activo) activoActual.getActivo().clone();
			DialogoInfoActivo dialogo = new DialogoInfoActivo(activo);
			dialogo.setVisible(true);
			dialogo.setSize(450, 200);
			dialogo.setLocationRelativeTo(this);
			dialogo.setResizable(false);
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}

	public void actualizarTimer(String timer) {
		panelActivos.actualizarTimer(timer);
	}
}
