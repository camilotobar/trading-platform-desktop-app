package interfaz;

import java.awt.Color; 
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.*;

import mundo.Precio;

@SuppressWarnings("serial")
public class PanelGestion extends JPanel implements ActionListener{

	public static final String COMPRAR = Precio.BUY;
	public static final String VENDER = Precio.SELL;
	
	private InterfazMercadosCapitales ventana;
	private JButton comprarBt;
	private JButton venderBt;
	private JLabel cantidadLbl;
	private JTextField cantidadTxt;

	public PanelGestion(InterfazMercadosCapitales ventana) {
		this.ventana = ventana;
		comprarBt =  new JButton(COMPRAR);
		comprarBt.addActionListener(this);
		comprarBt.setActionCommand(COMPRAR);
		comprarBt.setIcon(new ImageIcon("./docs/images/uparrow.png"));
		cantidadLbl = new JLabel("Cantidad (lots/unds):");
		cantidadLbl.setForeground(Color.WHITE);
		cantidadLbl.setFont(new Font("Arial", Font.BOLD, 15));
		cantidadTxt = new JTextField();
		cantidadTxt.setPreferredSize(new Dimension(70, 25));
		cantidadTxt.setHorizontalAlignment(JTextField.CENTER);
		venderBt = new JButton(VENDER);
		venderBt.addActionListener(this);
		venderBt.setActionCommand(VENDER);
		venderBt.setIcon(new ImageIcon("./docs/images/downarrow.png"));
		setBackground(Color.DARK_GRAY);
		TitledBorder border = new TitledBorder("Gestión");
		border.setTitleColor(Color.WHITE);
		setBorder(border);
		
		add(cantidadLbl);
		add(cantidadTxt);
		add(comprarBt);
		add(venderBt);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();
		if(command.equals(COMPRAR))
			ventana.transar(cantidadTxt.getText(), Precio.BUY);
		else if(command.equals(VENDER))
			ventana.transar(cantidadTxt.getText(), Precio.SELL);
	}
	
}
