package Configuración;

import java.io.File;
import java.util.ArrayList;

public class ExtractorRutas {
	
	private ArrayList<String> archivos;
	private int posicion;
	private int dimension;
	
	// Se menciona la ruta y la razon porque se esta accediendo donde la clase se considera una
	// clase generica 
	public ExtractorRutas(String ruta,String razon){
		
		Log log = new Log();
		
		File f = new File(ruta);
		if(f.exists()){
			log.escribirArchivo("Ruta: "+ruta+" de "+razon+" fue econtrada");
			
			// si fue encontro se realizan las siguientes acciones
			// Se obtiene los archivos que se extraen
			File[] files = f.listFiles();
			
			log.escribirArchivo("De la ruta: "+ruta+" se obtuvieron: "+files.length+" archivos");
			
			
			archivos = new ArrayList<String>();
			
			log.escribirArchivo("Lista de Archivos Creada");
			
			// Se obtienen todos los nombres de cada archivo
			for(int i=0;i<files.length;i++){
				
				//Se extrae el nombre de el archivo de la posicion i 
				String nombre = files[i].getName();
				
				// se agrega al ArrayList los nombres de archivos
				archivos.add(nombre);
				log.escribirArchivo("Archivo: "+nombre+" agregado a la lista de Archivos");
				
			}
			
			this.dimension = files.length;
			
			log.escribirArchivo("Se han agregado los archivos de la ruta: "+ruta);
			
				
		}else{
			
			log.escribirArchivo("Ruta: "+ruta+" de "+razon+" no fue econtrada");
		
		}
		
		this.posicion=0;
		
	
	}
	
	// Se obtiene el nombre de la posicion actual de la lista 
	public String obtenerNombre(){
		
		Log log = new Log();
		
		// se extrae el nombre de la posicion actual
		String nombre = this.archivos.get(this.posicion);
		this.posicion++;
		
		log.escribirArchivo("Se obtuvo nombre del archivo: "+nombre+" exitosamente");
		
		// se retorna el nombre
		return nombre;
	}
	
	public int getPosicion(){
		return this.posicion;
	}
	
	public int getDimension(){
		return this.dimension;
	}
	
}
