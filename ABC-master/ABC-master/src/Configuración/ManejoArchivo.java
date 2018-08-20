package Configuración;

public interface ManejoArchivo {

	public boolean detectarArchivo();
	
	public void escribirArchivo(String text);

	public Object leerArchivo(String archivo);
}
