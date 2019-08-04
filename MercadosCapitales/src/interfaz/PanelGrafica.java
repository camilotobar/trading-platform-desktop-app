package interfaz;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.JPanel;
import mundo.Precio;

@SuppressWarnings("serial")
public class PanelGrafica extends JPanel {

	public static final int MARGEN_DERECHA = 60;
	public static final int MARGEN_IZQUIERDA = 90;
	public static final int MARGEN_ARRIBA = 20;
	public static final int MARGEN_ABAJO = 20;
	public static final int SPREAD = 30;

	public static final Color NARANJA = new Color(84, 130, 53);
	public static final Color AZUL_MARGEN = new Color(0, 132, 255);
	public static final Color VERDE = new Color(197, 90, 17);

	private InterfazMercadosCapitales ventana;
	private double ultimoAsk;
	private double spread;
	private double max;
	private double min;
	private DecimalFormat format;
	private DecimalFormat maxFormat;

	public PanelGrafica(InterfazMercadosCapitales ventana) {
		this.ventana = ventana;
		ventana.darMundo();
		ultimoAsk = -1;
		spread = -1;
		format = new DecimalFormat("0.0000");
		maxFormat = new DecimalFormat("0.000");
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(Color.BLACK);
		g2.fillRect(0, 0, getWidth(), getHeight());
		dibujarValores(g);
		dibujarGrid(g);
		dibujarUltimoValor(g);
	}

	public void dibujarGrid(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(AZUL_MARGEN);
		g2.setStroke(new BasicStroke(3));
		g2.drawLine(MARGEN_IZQUIERDA, getHeight() - MARGEN_ABAJO + 5, MARGEN_IZQUIERDA, MARGEN_ARRIBA);
		g2.drawLine(MARGEN_IZQUIERDA - 5, getHeight() - MARGEN_ABAJO, getWidth() - MARGEN_DERECHA + 20,
				getHeight() - MARGEN_ABAJO);
		g2.setStroke(new BasicStroke((float) 0.2));
		int anchoUtil = getWidth() - MARGEN_DERECHA - MARGEN_IZQUIERDA + 20,
				altoUtil = getHeight() - MARGEN_ARRIBA - MARGEN_ABAJO;
		for (int i = 0; i < anchoUtil; i += 40) {
			g2.drawLine(MARGEN_IZQUIERDA + i, MARGEN_ARRIBA, MARGEN_IZQUIERDA + i, getHeight() - MARGEN_ABAJO);
		}
		for (int i = 0; i < altoUtil; i += 30) {
			g2.drawLine(MARGEN_IZQUIERDA, MARGEN_ARRIBA + i, getWidth() - MARGEN_DERECHA + 20, MARGEN_ABAJO + i);
		}
	}

	public void dibujarUltimoValor(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.DARK_GRAY);
		g2.setStroke(new BasicStroke((float) 1));
		int[] rect = { getWidth() - MARGEN_DERECHA - 230, MARGEN_ARRIBA + 20, 215, 90 };
		for (int i = 0; i < rect[3]; i += 5) {
			int x1 = rect[0], y1 = rect[1] + i;
			int xy2 = 0;
			if (rect[3] - i < rect[2]) {
				xy2 = rect[3] - i;
			} else {
				xy2 = rect[2];
			}
			g2.drawLine(x1, y1, x1 + xy2, y1 + xy2);
		}
		for (int i = 5; i < rect[2]; i += 5) {
			int x1 = rect[0] + i, y1 = rect[1];
			int xy2 = 0;
			if (rect[2] - i < rect[3]) {
				xy2 = rect[2] - i;
			} else {
				xy2 = rect[3];
			}
			g2.drawLine(x1, y1, x1 + xy2, y1 + xy2);
		}

		Font f = new Font("Arial", Font.BOLD, 20);
		g2.setFont(f);
		ultimoAsk = ventana.getActivoActual().getActivo().getUltimoPrecio().getPrecioAsk();
		spread = ventana.getActivoActual().getActivo().getPrimerPrecio().getSpread();
		if (ultimoAsk >= 0) {
			g2.setColor(VERDE);
			g2.drawString("Últ. Ask: " + format.format(ultimoAsk), rect[0] + 15, rect[1] + 30);
			g2.setColor(NARANJA);
			g2.drawString("Últ. Bid: " + format.format((ultimoAsk - spread)), rect[0] + 15, rect[1] + 70);
		}
	}

	public void dibujarValores(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		try {
			// g2.rotate(Math.toRadians(180), 0, 0);
			Precio precio = ventana.getActivoActual().getActivo().darPrecios();
			ArrayList<Double>[] precios = calcularValoresY(precio);

			// Dibuja m�ximo y m�nimo
			g2.setColor(Color.WHITE);
			Font f = new Font("Arial", Font.BOLD, 14);
			g2.setFont(f);
			String maxx = maxFormat.format(max);
			String minn = maxFormat.format(min);
			int offSet = maxx.length();
			g2.drawString(minn, MARGEN_IZQUIERDA - (offSet * 8), MARGEN_ARRIBA + 10);
			g2.drawString(maxx, MARGEN_IZQUIERDA - (offSet * 8), getHeight() - MARGEN_ABAJO - 10);
			for (int i = 0; i < precios[0].size() - 1; i++) {
				int x1 = ((getWidth() - MARGEN_DERECHA - MARGEN_IZQUIERDA) * i / precios[0].size()) + MARGEN_IZQUIERDA;
				int x2 = ((getWidth() - MARGEN_DERECHA - MARGEN_IZQUIERDA) * (i + 1) / precios[0].size())
						+ MARGEN_IZQUIERDA;

				// Dibuja el ask
				g2.setColor(NARANJA);
				g2.drawLine(x1, getHeight() + SPREAD - calcularPosicionY(precios[0].get(i)), x2,
						getHeight() + SPREAD - calcularPosicionY(precios[0].get(i + 1)));

				// Dibuja el bid
				g2.setColor(VERDE);
				g2.drawLine(x1, getHeight() - (calcularPosicionY(precios[0].get(i))), x2,
						getHeight() - (calcularPosicionY(precios[0].get(i + 1))));

				// Dibuja l�nea horizontal
				if (i == precios[0].size() - 2) {
					f = new Font("Arial", Font.BOLD, 10);
					g2.setFont(f);
					g2.setStroke(new BasicStroke((float) 0.5));
					ultimoAsk = ventana.getActivoActual().getActivo().getUltimoPrecio().getPrecioAsk();
					spread = ventana.getActivoActual().getActivo().getPrimerPrecio().getSpread();

					// ask
					g2.setColor(NARANJA);
					g2.drawLine(MARGEN_IZQUIERDA, getHeight() + SPREAD - calcularPosicionY(precios[0].get(i + 1)),
							getWidth() - MARGEN_DERECHA - 10,
							getHeight() + SPREAD - calcularPosicionY(precios[0].get(i + 1)));

					g2.fillRoundRect(getWidth() - MARGEN_DERECHA - 10,
							getHeight() + SPREAD - calcularPosicionY(precios[0].get(i + 1)) - 10, 60, 20, 9, 9);

					g2.setColor(Color.WHITE);
					g2.drawString(format.format(ultimoAsk - spread), getWidth() - MARGEN_DERECHA - 8,
							getHeight() + SPREAD - calcularPosicionY(precios[0].get(i + 1)) + 3);

					// bid
					g2.setColor(VERDE);
					g2.drawLine(MARGEN_IZQUIERDA, getHeight() - (calcularPosicionY(precios[0].get(i + 1))),
							getWidth() - MARGEN_DERECHA - 10, getHeight() - (calcularPosicionY(precios[0].get(i + 1))));
					g2.fillRoundRect(getWidth() - MARGEN_DERECHA - 10,
							getHeight() - calcularPosicionY(precios[0].get(i + 1)) - 10, 60, 20, 9, 9);
					g2.setColor(Color.WHITE);
					g2.drawString(format.format(ultimoAsk), getWidth() - MARGEN_DERECHA - 8,
							getHeight() - calcularPosicionY(precios[0].get(i + 1)) + 3);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int calcularPosicionY(double precio) {
		return (int) ((getHeight() - MARGEN_ABAJO - MARGEN_ARRIBA - 20) * precio / 100) + MARGEN_ARRIBA + SPREAD + 10;
	}

	private ArrayList<Double>[] calcularValoresY(Precio precio) {
		max = 0;
		min = Integer.MAX_VALUE;

		@SuppressWarnings("unchecked")
		ArrayList<Double>[] precios = new ArrayList[2];
		precios[0] = new ArrayList<>();
		precios[1] = new ArrayList<>();

		while (precio != null) {
			max = (precio.getPrecioAsk() > max ? precio.getPrecioAsk() : max);
			min = (precio.getPrecioAsk() < min ? precio.getPrecioAsk() : min);

			precios[0].add(precio.getPrecioAsk());
			precios[1].add(precio.getSpread());
			precio = precio.getSiguiente();
		}

		for (int i = 0; i < precios[0].size(); i++) {
			precios[0].set(i, (((precios[0].get(i) - min) * 100d) / (max - min)));
		}
		return precios;
	}
}