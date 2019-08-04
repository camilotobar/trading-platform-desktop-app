package interfaz;

import java.awt.*; 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

@SuppressWarnings("serial")
public class DialogoAgregarCorredor extends JDialog implements ActionListener {

	public static final String AGREGAR = "Agregar";
	public static final String CANCELAR = "Cancelar";
	public static final int ALTURA = 200;
	public static final int ANCHO = 335;

	private InterfazMercadosCapitales ventana;
	private JLabel nombre;
	private JLabel user;
	private JLabel id;
	private JLabel balance;
	private JTextField nombreTxt;
	private JTextField userTxt;
	private JTextField idTxt;
	private JTextField balanceTxt;
	private JButton agregarBt;
	private JButton cancelarBt;
	
	public DialogoAgregarCorredor(InterfazMercadosCapitales ventana) {
		setLayout(new GridLayout(5, 2, 5, 5));
		setSize(ANCHO, ALTURA);
		setModal(true);
		setLocationRelativeTo(null);
		setTitle("Agregar corredor");

		this.ventana = ventana;

		nombre = new JLabel("    Nombre:");
		user = new JLabel("    Username:");
		id = new JLabel("    ID:");
		balance = new JLabel("    Balance:");

		nombreTxt = new JTextField();
		userTxt = new JTextField();
		idTxt = new JTextField();
		balanceTxt = new JTextField();

		agregarBt = new JButton(AGREGAR);
		agregarBt.setActionCommand(AGREGAR);
		agregarBt.addActionListener(this);
		cancelarBt = new JButton(CANCELAR);
		cancelarBt.setActionCommand(CANCELAR);
		cancelarBt.addActionListener(this);

		add(nombre);
		add(nombreTxt);
		add(user);
		add(userTxt);
		add(id);
		add(idTxt);
		add(balance);
		add(balanceTxt);
		add(cancelarBt);
		add(agregarBt);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand();
		switch (comando) {
		case AGREGAR:
			String[] datos = new String[4];
			if (nombreTxt.getText().equals("") || userTxt.getText().equals("") || idTxt.getText().equals("")
					|| balanceTxt.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "Debe llenar todos los espacios", "Error", JOptionPane.ERROR_MESSAGE);
			}else{
				try{
					Integer.parseInt(balanceTxt.getText());
					datos[0] = nombreTxt.getText();
					datos[1] = userTxt.getText();
					datos[2] = idTxt.getText();
					datos[3] = balanceTxt.getText();
					ventana.agregarCorredor(datos);
					this.dispose();
				}catch (NumberFormatException n) {
					JOptionPane.showMessageDialog(this, "El balance debe ser un valor numérico", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
			break;
		case CANCELAR:
			this.dispose();
			break;
		}
	}
}
