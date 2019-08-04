package interfaz;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class BarraArchivo extends JMenu implements ActionListener{
	
	private JMenuItem cargar;
	private JMenuItem nuevoCorredor;
	private InterfazMercadosCapitales ventana;
	
	public static final String CAMBIAR_CORREDOR = "Cambiar corredor";
	public static final String NUEVO_CORREDOR = "Nuevo corredor";
	
	public BarraArchivo(InterfazMercadosCapitales ventana) {
		super("Corredor");
		this.ventana = ventana;
		
		cargar = new JMenuItem(CAMBIAR_CORREDOR);
		cargar.setActionCommand(CAMBIAR_CORREDOR);
		cargar.addActionListener(this);
		
		nuevoCorredor = new JMenuItem(NUEVO_CORREDOR);
		nuevoCorredor.setActionCommand(NUEVO_CORREDOR);
		nuevoCorredor.addActionListener(this);
		
		add(cargar);
		add(nuevoCorredor);
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		switch(command){
		case CAMBIAR_CORREDOR:
			String usuario = JOptionPane.showInputDialog("Digite el username al que desea cambiar");
			ventana.cargarUsuario(usuario);
			break;
		case NUEVO_CORREDOR:
			DialogoAgregarCorredor dialog = new DialogoAgregarCorredor(ventana);
			dialog.setVisible(true);
			dialog.repaint();
			break;
		}
	}

}
