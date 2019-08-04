package mundo;

import java.io.*;

public class AgenteDeBolsa {

	public final static String RUTA_CORREDORES = "./docs/Corredores.corredor";
	public final static String RUTA_ACTIVOS = "./docs/activos.txt";
	private Corredor corredorRaiz;
	private Corredor corredorActual;
	private Activo[] simbolos;

	public AgenteDeBolsa() {
		try {
			inicializarSimbolos();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void inicializarSimbolos() throws Exception {
		BufferedReader in = new BufferedReader(new FileReader(new File(RUTA_ACTIVOS)));
		int numActivos = Integer.parseInt(in.readLine());
		simbolos = new Activo[numActivos];
		String linea = in.readLine();
		int i = 0;
		do {
			String[] datos = linea.split("\t");
			Activo activo;
			String symbol = datos[0];
			String info = datos[1];
			double delay = Double.parseDouble(datos[2]);
			double spread = Double.parseDouble(datos[3]);
			double ask = Double.parseDouble(datos[4]);
			double bid = Double.parseDouble(datos[5]);
			if (datos[6].equals("RV")) {
				int lote = Integer.parseInt(datos[8]);
				if (datos[7].equals("D")) {
					activo = new Divisa(symbol, info, delay, spread, ask, bid, lote, datos[9], datos[10]);
				} else if (datos[7].equals("A")) {
					activo = new Accion(symbol, info, delay, spread, ask, bid, lote, datos[9]);
				} else {
					String tipo = MateriaPrima.PETROLEO;
					if (datos[9].equals(MateriaPrima.METAL))
						tipo = MateriaPrima.METAL;
					activo = new MateriaPrima(symbol, info, delay, spread, ask, bid, lote, tipo);
				}
			} else {
				double tasa = (datos[8].equals("5Y")) ? RentaFija.CINCO_ANHOS : RentaFija.SEIS_MESES;
				if (datos[7].equals("B")) {
					double coeficienteRiesgo = Double.parseDouble(datos[9]);
					activo = new Bono(symbol, info, delay, spread, ask, bid, tasa, coeficienteRiesgo);
				} else {
					activo = new Hipoteca(symbol, info, delay, spread, ask, bid, tasa, datos[9]);
				}
			}
			simbolos[i] = activo;
			linea = in.readLine();
			i++;
		} while (linea != null);
		in.close();
	}

	public Corredor getCorredorRaiz() {
		return corredorRaiz;
	}

	public void setCorredorRaiz(Corredor corredorRaiz) {
		this.corredorRaiz = corredorRaiz;
	}

	public void cargarCorredores() {
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(RUTA_CORREDORES));
			corredorRaiz = (Corredor) in.readObject();
			corredorActual = corredorRaiz;
			in.close();
//			System.out.println(corredorRaiz.getUser());
		} catch (Exception exc) {
		}
	}

	public Corredor agregarCorredor(Corredor cor, Corredor actual) throws YaExisteCorredorException {
		if (corredorRaiz != null) {
			if (actual.getUser().compareTo(cor.getUser()) > 0) {
				if (actual.getIzq() == null)
					actual.setIzq(cor);
				else
					agregarCorredor(cor, actual.getIzq());
				return cor;
			} else if (actual.getUser().compareTo(cor.getUser()) < 0) {
				if (actual.getDer() == null)
					actual.setDer(cor);
				else
					agregarCorredor(cor, actual.getDer());
				return cor;
			} else {
				throw new YaExisteCorredorException(cor.getUser());
			}
		} else {
			corredorRaiz = cor;
			return cor;
		}
	}

	public Corredor buscarCorredor(String user, Corredor actual) {
		Corredor buscado = actual;
		if (actual != null) {
			if (!actual.getUser().equalsIgnoreCase(user)) {
				if (actual.getUser().compareTo(user) > 0)
					buscado = buscarCorredor(user, actual.getIzq());
				else
					buscado = buscarCorredor(user, actual.getDer());
			}
		}
		return buscado;
	}

	public Activo[] getSimbolos() {
		return simbolos;
	}

	public String listaActivos() {
		String lista = "";
		for (Activo activo : simbolos) {
			lista += activo.toString() + "\n";
		}
		return lista;
	}

	public String[] darNombresActivos() {
		String[] activos = new String[simbolos.length];
		int i = 0;
		for (Activo act : simbolos) {
			activos[i] = act.getSymbol();
			i++;
		}
		return activos;
	}

	public Corredor getCorredores() {
		return corredorRaiz;
	}

	public Corredor getCorredorActual() {
		return corredorActual;
	}

	public void setCorredorActual(Corredor corredorActual) {
		this.corredorActual = corredorActual;
	}
}
