package Utilidades;

import org.apache.poi.ss.formula.eval.StringEval;

import Configuración.Log;

public class Mostrador {

	/**
	 * Muestra Cualquier Matriz INTEGER
	 * @param nombreMatriz
	 * @param matriz
	 */
	public void mostrarMatriz(String nombreMatriz, int[][] matriz){
		Log log = new Log();
		log.escribirArchivo("Se Muestra Matriz: "+nombreMatriz);
		
		System.out.println("Matriz :" + nombreMatriz);
		for(int i=0;i<matriz.length;i++){
			for(int j=0;j<matriz[i].length;j++){
				System.out.print(matriz[i][j]+" ");
			}
			System.out.println("");
		}
		
	}
	/**
	 * Muestra Cualquier Matriz Double
	 * @param nombreMatriz
	 * @param matriz
	 */
	public void mostrarMatriz(String nombreMatriz, double[][] matriz){
		Log log = new Log();
		log.escribirArchivo("Se Muestra Matriz: "+nombreMatriz);
		
		System.out.println("Matriz :" + nombreMatriz);
		for(int i=0;i<matriz.length;i++){
			for(int j=0;j<matriz[i].length;j++){
				System.out.print(matriz[i][j]+" ");
			}
			System.out.println("");
		}
		
	}
	
	/**
	 * Muestra Cualquier Vector INT
	 * @param nombre vector
	 * @param vector
	 */
	public void mostrarVector(String nombreVector, int[] vector){
		Log log = new Log();
		log.escribirArchivo("Se Muestra Matriz: "+nombreVector);
		
		System.out.println("Vector :" + nombreVector);
		for(int i=0;i<vector.length;i++){
			
			System.out.print(vector[i]+" ");
		}
		System.out.println("");
		
		
	}
	public void mostrarVector(String nombreVector, double[] vector) {
		// TODO Auto-generated method stub
		Log log = new Log();
		log.escribirArchivo("Se Muestra Matriz: "+nombreVector);
		
		System.out.println("Vector :" + nombreVector);
		for(int i=0;i<vector.length;i++){
			
			System.out.print(vector[i]+" ");
		}
		System.out.println("");
		
	}
	
	
	
}
