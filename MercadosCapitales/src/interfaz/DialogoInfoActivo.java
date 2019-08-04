package interfaz;

import mundo.*;
import java.awt.*;
import java.text.DecimalFormat;

import javax.swing.*;

@SuppressWarnings("serial")
public class DialogoInfoActivo extends JDialog {

	private Activo activo;
	private JLabel lblSymbol;
	private JTextArea areaInfo;

	public DialogoInfoActivo(Activo a) {
		activo = a;
		setTitle("Información Activo : :");
		setLayout(new BorderLayout());

		lblSymbol = new JLabel(activo.getSymbol());
		lblSymbol.setHorizontalAlignment(JLabel.CENTER);

		areaInfo = new JTextArea(infoActivo());
		areaInfo.setEditable(false);
		
		JScrollPane scroll = new JScrollPane(areaInfo);
		scroll.setBackground(Color.WHITE);

		add(lblSymbol, BorderLayout.NORTH);
		add(scroll, BorderLayout.CENTER);
	}

	private String infoActivo() {
		DecimalFormat f = new DecimalFormat("0.####");
		String info = "    Información: " + activo.getInformacion() + "\n    Precio\n       Delay: " + f.format((activo.getDelay()/1000))
				+ " seg(s)\n       Spread: " + f.format(activo.getPrimerPrecio().getSpread()) + "\n       Ultimo Precio Ask: "
				+ f.format(activo.getUltimoPrecio().getPrecioAsk()) + " USD\n       Primer registro Ask: "
				+ f.format(activo.getPrimerPrecio().getPrecioAsk()) + " USD\n\n";
		if(activo instanceof RentaFija){
			RentaFija rf = (RentaFija) activo;
			info += "    Su categoría es Renta fija, su tasa de interés es de "+(rf.getTasaInteres()*100)+"% anual.\n";
			if(rf instanceof Bono){
				Bono bono = (Bono) rf;
				info += "    Siendo un bono gubernamental, su coeficiente de riesgo es "+bono.getCoeficienteRiesgo()+".";
			}else{
				Hipoteca hip = (Hipoteca) rf;
				info += "    Una hipoteca de la región de "+hip.getRegion()+".";
			}
		}else{
			RentaVariable rv = (RentaVariable) activo;
			info += "    Su categoria es Renta variable, su tamaño de lote unitario es de "+rv.getLOTE_UNITARIO()+" unds.\n";
			if(rv instanceof Divisa){
				Divisa div = (Divisa) rv;
				info += "    Una divisa del gobierno de "+div.getGobierno()+" y posee una voltatilidad "+div.getVolatilidad()+".";
			}else if( rv instanceof Accion){
				Accion acc = (Accion) rv;
				info += "    Una acción unitaria de la compañía "+acc.getCOMPANHIA()+".";
			}else{
				MateriaPrima mtp = (MateriaPrima) rv;
				info += "    Siendo una materia prima, se encuentra dentro del tipo "+mtp.getTipo()+".";
			}
		}
		return info;
	}

}
