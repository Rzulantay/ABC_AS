package Configuración;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Log implements ManejoArchivo{

	public boolean detectarArchivo() {
		// TODO Auto-generated method stub
		
		String nombreArchivo = "LOG.txt";
		String ruta = "System/";
		
		DetectorArchivos det = new DetectorArchivos(ruta+nombreArchivo); 
		
		// se crea una variale de tiempo
		Reloj r = new Reloj();
		if(det.detectar()){
			System.out.println(r.obtenerTiempo()+"Archivo LOG Existe");
			return true;
		}
		
		System.out.println(r.obtenerTiempo()+"Archivo LOG No Existe");
		// Se crea el Archivo a traves de una sentencia try catch
		try {
			
			BufferedWriter bw = new BufferedWriter(new FileWriter(ruta+nombreArchivo));
			System.out.println(r.obtenerTiempo()+"Archivo LOG Creado");
			
			// Escribo en el archivo creado
			bw.write(r.obtenerTiempo()+"Archivo LOG Creado");
			bw.newLine();
			
			// Cierro el Archivo
			bw.close();
			return true;
		
		} catch (IOException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(r.obtenerTiempo()+"Error a la creaciï¿½n del archivo");
		
		}
		
		return false;
	}
	/**
	 *  Escribe en el archivo LOG
	 */
	public void escribirArchivo(String text) {
		// TODO Auto-generated method stub
		
		String nombreArchivo = "LOG.txt";
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
			System.out.println(r.obtenerTiempo()+"Error en la Escritura del archivo LOG");
			
		
		}
		
	}
	
	public Object leerArchivo(String archivo) {
		// TODO Auto-generated method stub
		// Este metodo no se utiliza dentro del log
		return null;
	}
	
	
	
}
