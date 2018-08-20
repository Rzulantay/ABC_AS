package Utilidades;

public class Transformer {

	/**
	 *  Trasnforma la matriz Y al vector Y 
	 * @param M cantidad de maquinas
	 * @param Y matriz de maquinas celdas
	 * @return vector correspondiente a maquinas celdas
	 */
	
	public int[] matrixToVectorY(int M, int Y[][]){
		int L[] = new int[M];
		
		for(int i=0;i<Y.length;i++){
			for(int j=0;j<Y[i].length;j++){
				if(Y[i][j]==1){
					L[i] = j+1;
				}
			}
		}
		return L;
	}
	/**
	 * Metodo que transforma el vector Y a la matriz Y
	 * @param C Celdas
	 * @param Y Vector Maquinas Celdas
	 * @return
	 */
	public int [][] vectorToMatrixY(int C, int [] loc){
		
		int Y[][] = new int[loc.length][C];
		
		for(int i=0;i<Y.length;i++){
			
			for(int j=0;j<Y[i].length;j++){
				if((loc[i]-1)==j){
					Y[i][j] = 1;
				}else{
					Y[i][j] = 0;
				}
				
			}
		}
		return Y;
	}

}
