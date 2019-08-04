package interfaz;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.*;
import javax.swing.border.*;

import mundo.*;

@SuppressWarnings("serial")
public class PanelActivos extends JPanel implements ActionListener {
	public static final String REPORTE_ACTIVO = "Reporte activo";
	public static final String CARGAR = "Cargar";
	public static final String MAS_INFO = "Más Info";
	public static final int WIDTH = 300;
	
	private JComboBox<String> opcionesJCB;
	private JButton btnReporteActivoBt;
	private JButton btnMasInfo;
	private InterfazMercadosCapitales ventana;
	private JLabel lblInfoSymbol;
	private JLabel lblTimer;
	private JLabel lblCorredorActual;
	private JLabel lblActivoActual;
	private JLabel[] nLotesLbl;
	private JLabel[] nombreLbl;
	private JLabel[] usuarioLbl;
	private JLabel[] idLbl;
	private JLabel[] balanceLbl;
	private DecimalFormat f;
	
	@SuppressWarnings("static-access")
	public PanelActivos(InterfazMercadosCapitales ventana) {
		this.ventana = ventana;
		TitledBorder border = new TitledBorder("Activos");
		border.setTitleColor(Color.WHITE);
		setBorder(border);
		BorderLayout ly = new BorderLayout();
		setLayout(ly);
		setPreferredSize(new Dimension(WIDTH, ventana.getHeight()));
		setBackground(Color.DARK_GRAY);
		Font titleFont = new Font("Arial", Font.PLAIN, 16);
		Font infoFont = new Font("Arial", Font.PLAIN, 13);
		Font font = new Font("Arial", Font.BOLD, 13);
		setFont(titleFont);

		f = new DecimalFormat("#.####");
		
		lblTimer = new JLabel();
		lblTimer.setPreferredSize(new Dimension(280, 20));
		lblTimer.setHorizontalAlignment(JLabel.CENTER);
		lblTimer.setForeground(Color.WHITE);

		opcionesJCB = new JComboBox<>(ventana.darMundo().darNombresActivos());
		opcionesJCB.setBackground(Color.DARK_GRAY);
		opcionesJCB.setForeground(Color.WHITE);
		opcionesJCB.setActionCommand(CARGAR);
		opcionesJCB.addActionListener(this);
		opcionesJCB.setPreferredSize(new Dimension(100, 25));
		
		btnReporteActivoBt = new JButton(REPORTE_ACTIVO);
		btnReporteActivoBt.addActionListener(this);
		btnReporteActivoBt.setActionCommand(REPORTE_ACTIVO);
		btnReporteActivoBt.setBackground(new Color(195, 195, 195));
		btnReporteActivoBt.setIcon(new ImageIcon("./docs/images/generar Reporte.png"));
		btnReporteActivoBt.setPreferredSize(new Dimension(160, 47));
		
		btnMasInfo = new JButton(MAS_INFO);
		btnMasInfo.addActionListener(this);
		btnMasInfo.setActionCommand(MAS_INFO);
		btnMasInfo.setBackground(new Color(195, 195, 195));
		btnMasInfo.setIcon(new ImageIcon("./docs/images/mas-info.png"));
		btnMasInfo.setPreferredSize(new Dimension(100, 47));
		
		JPanel aux = new JPanel();
		aux.setBackground(Color.DARK_GRAY);
		aux.setLayout(new GridLayout(8, 2));
		
		JPanel aux2 = new JPanel();
		aux2.setBackground(Color.DARK_GRAY);
		aux2.setLayout(new FlowLayout());
		aux2.add(lblTimer);
		aux2.add(opcionesJCB);
		aux2.add(btnReporteActivoBt);
		aux2.setPreferredSize(new Dimension(WIDTH, 120));
		add(aux2, ly.NORTH);
		
		nLotesLbl = new JLabel[2];
		nLotesLbl[0] = new JLabel("     Núm. lots / unds:");
		nLotesLbl[0].setForeground(Color.LIGHT_GRAY);
		nLotesLbl[0].setFont(font);
		
		nLotesLbl[1] = new JLabel("N/A");
		nLotesLbl[1].setForeground(Color.LIGHT_GRAY);
		nLotesLbl[1].setFont(infoFont);
		new JLabel("");

		nombreLbl = new JLabel[2];
		usuarioLbl = new JLabel[2];
		idLbl = new JLabel[2];
		
		balanceLbl = new JLabel[2];
		Corredor actual = ventana.darMundo().getCorredorActual();
		
		nombreLbl[0] = new JLabel("     Nombre: ");
		nombreLbl[0].setForeground(Color.LIGHT_GRAY);
		nombreLbl[0].setFont(font);
		nombreLbl[1] = actual != null && actual.getNombre() != null ? new JLabel(actual.getNombre()) : new JLabel("N/A");
		nombreLbl[1].setForeground(Color.LIGHT_GRAY);
		nombreLbl[1].setFont(infoFont);
		
		usuarioLbl[0] = new JLabel("     Username: ");
		usuarioLbl[0].setForeground(Color.LIGHT_GRAY);
		usuarioLbl[0].setFont(font);
		usuarioLbl[1] = actual != null && actual.getUser() != null ? new JLabel(actual.getUser()) : new JLabel("N/A");
		usuarioLbl[1].setForeground(Color.LIGHT_GRAY);
		usuarioLbl[1].setFont(infoFont);
		
		idLbl[0] = new JLabel("     Identificación: ");
		idLbl[0].setForeground(Color.LIGHT_GRAY);
		idLbl[0].setFont(font);
		idLbl[1] = actual != null && actual.getIdentificacion() != null ? new JLabel(actual.getIdentificacion()): new JLabel("N/A");
		idLbl[1].setForeground(Color.LIGHT_GRAY);
		idLbl[1].setFont(infoFont);
		
		balanceLbl[0] = new JLabel("     Balance: ");
		balanceLbl[0].setForeground(Color.LIGHT_GRAY);
		balanceLbl[0].setFont(font);
		balanceLbl[1] = actual != null && actual.getBalance() >= 0 ? new JLabel("US $"+f.format(actual.getBalance())): new JLabel("N/A");
		balanceLbl[1].setForeground(Color.LIGHT_GRAY);
		balanceLbl[1].setFont(infoFont);
		
		lblInfoSymbol = new JLabel(ventana.getActivoActual().getActivo().getInformacion());
		lblInfoSymbol.setForeground(Color.LIGHT_GRAY);
		lblInfoSymbol.setFont(infoFont);
		lblInfoSymbol.setHorizontalAlignment(JLabel.CENTER);
		
		lblActivoActual = new JLabel("     Activo Actual");
		lblActivoActual.setForeground(Color.WHITE);
		lblActivoActual.setFont(font);
		
		lblCorredorActual = new JLabel("     Corredor Actual");
		lblCorredorActual.setForeground(Color.WHITE);
		lblCorredorActual.setFont(font);
		
		JPanel panelMasInfo = new JPanel();
		panelMasInfo.setLayout(new BorderLayout());
		panelMasInfo.setBackground(Color.DARK_GRAY);
		panelMasInfo.add(new JLabel("    "), BorderLayout.EAST);
		panelMasInfo.add(new JLabel("    "), BorderLayout.WEST);
		panelMasInfo.add(btnMasInfo, BorderLayout.CENTER);
		
		aux.add(lblActivoActual);
		aux.add(lblInfoSymbol);
		aux.add(new JLabel());
		aux.add(panelMasInfo);
		aux.add(lblCorredorActual);
		aux.add(new JLabel());
		aux.add(nombreLbl[0]);
		aux.add(nombreLbl[1]);
		aux.add(usuarioLbl[0]);
		aux.add(usuarioLbl[1]);
		aux.add(idLbl[0]);
		aux.add(idLbl[1]);
		aux.add(balanceLbl[0]);
		aux.add(balanceLbl[1]);
		aux.add(nLotesLbl[0]);
		aux.add(nLotesLbl[1]);
		
		add(aux, ly.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		switch (command) {
		case REPORTE_ACTIVO:
			ventana.generarReporte();
			break;
		case CARGAR:
			ventana.cambiarActivoActual(String.valueOf(opcionesJCB.getSelectedItem()));
			lblInfoSymbol.setText(ventana.getActivoActual().getActivo().getInformacion());
			break;
		case MAS_INFO:
			ventana.masInfoActivo();
			break;
		}
	}

	public void actualizar() {
		Corredor actual = ventana.darMundo().getCorredorActual();
		nombreLbl[1].setText(actual != null && actual.getNombre() != null ? (actual.getNombre()) : "N/A");
		usuarioLbl[1].setText(actual != null && actual.getUser() != null ? (actual.getUser()) : "N/A");
		idLbl[1].setText(actual != null && actual.getIdentificacion() != null ? (actual.getIdentificacion()) : "N/A");
		balanceLbl[1].setText(actual != null && actual.getBalance() >= 0 ? ("US $"+f.format(actual.getBalance())) : "N/A");
	}
	
	public void actualizarCantidad(Activo act){
		String cant;
		if(act != null){
			if(act instanceof RentaFija){
				RentaFija rf = (RentaFija) act;
				cant = rf.getCantidad()+" unds";
			}else{
				RentaVariable rv = (RentaVariable) act;
				cant = rv.getCantidadLotes()+" lots";
			}
		}else
			cant = "N/A";
		nLotesLbl[1].setText(cant);
	}
	
	public void actualizarTimer(String time){
		lblTimer.setText(time);
	}
}
