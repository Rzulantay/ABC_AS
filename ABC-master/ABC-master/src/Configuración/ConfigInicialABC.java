package Configuración;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

import Kernel.ArtificialBeeColony;


public class ConfigInicialABC implements ManejoArchivo{
	
	private int NoExperimentos;
	

	public boolean detectarArchivo() {
		// TODO Auto-generated method stub
		
		String nombreArchivo = "configABC.txt";
		String ruta = "System/";
		
		DetectorArchivos det = new DetectorArchivos(ruta+nombreArchivo);
		
		// Se instancia el LOG
		Log log = new Log();
		
		if(det.detectar()){
			log.escribirArchivo("Archivo CONFIG Existe");
			return true;
		}
		
		log.escribirArchivo("Archivo CONFIG No Existe");
		
		try {
			
			BufferedWriter bw = new BufferedWriter(new FileWriter(ruta+nombreArchivo));
			log.escribirArchivo("Archivo CONFIG Creado");
			
			
			
			// Escribo en el archivo creado
			bw.write("NumeroExperimentos=1");
			bw.newLine();
			bw.write("Abejas=100");
			bw.newLine();
			bw.write("NP=20");
			bw.newLine();
			bw.write("Limit=20");
			bw.newLine();
			bw.write("Trial=5");
			bw.newLine();
			bw.write("Ejecuciones=15");
			bw.newLine();
			// Cierro el Archivo
			bw.close();
			return true;
		
		} catch (IOException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.escribirArchivo("Error a la creación del archivo CONFIG");
		}
		
		return false;
	}



	public void escribirArchivo(String text) {
		// TODO Auto-generated method stub
		String nombreArchivo = "configABC.txt";
		String ruta = "System/";
		
		Reloj r = new Reloj();
		try {
			//Evito que se sobreescriba el archivo 
			BufferedWriter bw = new BufferedWriter(new FileWriter(ruta+nombreArchivo,true));
			System.out.println(r.obtenerTiempo()+text);
			
			// Escribo en el archivo creado
			bw.write(r.obtenerTiempo()+text);
			bw.newLine();
			
			// Cierro el Archivo
			bw.close();
		
		} catch (IOException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(r.obtenerTiempo()+"Error en la Escritura del archivo Config");
			
		
		}
	}

	public Object leerArchivo(String archivo) {
		// TODO Auto-generated method stub
		ArtificialBeeColony Abc = null;
	

		Log log = new Log();
		
		int Abejas = 0,NP=0,Tries=0,NoExperimentos=0,Limit=0,Ejecuciones=0;
		
		try {
			String cadena = null;
			int cont1 = 0;
			
			FileReader f = new FileReader("System/configABC.txt");
			@SuppressWarnings("resource")
			BufferedReader b = new BufferedReader(f);
			//log.escribirArchivo("Leyendo Archivo Config.txt");
			
			
			// Se lee linea tras linea
			while((cadena = b.readLine())!=null){
				StringTokenizer st = new StringTokenizer(cadena,"=");
				
				int cont2 = 0;
				while(st.hasMoreTokens()){
				
					String aux = st.nextToken();
					if(cont2==1){
						switch(cont1){
							case 0: // numero de experimentos
									this.NoExperimentos = NoExperimentos;
									break;
							case 1: // numero de Abejas
									Abejas = Integer.parseInt(aux);
									break;
							case 2: // numero de NP
									NP = Integer.parseInt(aux);
									break;
							case 3: // numero de NP
									Limit = Integer.parseInt(aux);
									break;
							case 4: // Trial
									Tries = Integer.parseInt(aux);
									break;
							case 5: // Numero de Ejecuciones
									Ejecuciones = Integer.parseInt(aux);
									break;
						}
					}
					cont2++;
				}
				cont1++;
			}
	
		}catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.escribirArchivo("No existe Archivo config.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.escribirArchivo("Error de Lectura archivo config.txt");
		}
		
		Abc = new ArtificialBeeColony(Abejas,NP,Limit,Tries,Ejecuciones);
		
		
		//log.escribirArchivo("Configuración inicial seteada");
		
		return Abc;
	}
	

	public int getNoExperimentos() {
		return NoExperimentos;
	}


	public void setNoExperimentos(int noExperimentos) {
		NoExperimentos = noExperimentos;
	}
	
	
}
