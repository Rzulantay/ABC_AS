package Configuración;

import java.io.File;

public class DetectorArchivos {
	// Nombre de archivo
	private String archivo;

	public DetectorArchivos(String archivo) {
		this.archivo = archivo;
	}
	
	/**
	 * Comprueba que el archivo realmente exista
	 * @return false or true segun la existencia del archivo
	 */
	public boolean detectar(){
		
		// Se enlaza archivo
		File fichero = new File(this.archivo);
		
		// Comprobaciï¿½n de la existensia del fichero
		if(fichero.exists()){
			return true;
		}
		return false;
	}
	
}
