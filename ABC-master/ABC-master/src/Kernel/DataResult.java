package Kernel;

import java.util.ArrayList;

public class DataResult {


	
	ArrayList<int[][]> PoblacProm = new ArrayList<int[][]>();
	ArrayList<Long[]> IteracionOptimo = new ArrayList<Long[]>();
	ArrayList<int[][]> OptimoPromedio = new ArrayList<int[][]>();
	
	ArrayList<Double> PromedioMejorSol = new ArrayList<Double>();
	ArrayList<Double> PromedioPoblación = new ArrayList<Double>();

	//Soluciones de boctor C3,M6(p1,p2,p3...p10),C3,M7(p1,p2,p3...p10)...
	public int[] SolucionesProblemas = {27,7,9,27,11,6,11,14,12,10,18,6,4,18,8,4,5,11,12,8,11,6,4,14,8,4,5,11,8,8,11,6,4,13,6,3,4,10,8,5,11,7,4,14,9,5,7,13,8,8,11,6,4,13,6,3,4,10,8,5,11,4,4,13,6,3,4,8,8,5,11,3,3,13,5,3,4,5,5,5,11,3,1,13,4,2,4,5,5,5};

	public DataResult() {
		// TODO Auto-generated constructor stub
		
	}
	
	

	public ArrayList<Double> getPromedioMejorSol() {
		return PromedioMejorSol;
	}



	public void setPromedioMejorSol(ArrayList<Double> promedioMejorSol) {
		PromedioMejorSol = promedioMejorSol;
	}



	public ArrayList<Double> getPromedioPoblación() {
		return PromedioPoblación;
	}



	public void setPromedioPoblación(ArrayList<Double> promedioPoblación) {
		PromedioPoblación = promedioPoblación;
	}



	public void setPoblacProm(int[][] resultPob) {
		PoblacProm.add(resultPob);
	}
	public ArrayList<int[][]> getPoblacProm() {
		return PoblacProm;
	}

	public void setIteracionOptimo(Long[] e) {
		IteracionOptimo.add(e);
	}
	public ArrayList<Long[]> getIteracionOptimo() {
		return IteracionOptimo;
	}

	public void setOptimoPromedio(int[][] result) {
		OptimoPromedio.add(result);
	}
	public ArrayList<int[][]> getOptimoPromedio() {
		return OptimoPromedio;
	}

	public void setIteracionOptimo(int[][] result,int[][] resultPob) {

		setOptimoPromedio(result);
		setPoblacProm(resultPob);

	}

	public void mostrar() {
		for (int[][] Var : OptimoPromedio) {
						
			int cont = 0;
			for (int i=0; i < Var.length ; i++) {
				for (int j = 0; j < Var[0].length ; j++) {
					System.out.print("Holaaaaaa Aleconi \n");
					System.out.println(Var[i][j]);
					
				}	
				System.out.println("\n");
			}
		}
	
		
	}
	
	public int PoblacionPromedio(){
		
		int cont = 0;
		int suma=0;
		for (int[][] Var : PoblacProm) {
			for (int i=0; i < Var.length ; i++) {
				for (int j = 0; j < Var[0].length ; j++) {
					suma=suma+Var[i][j];
					cont=cont+1;
				}	
			}
		}
		
		return suma/cont;
	}
	
	
	public Double PromedioMejorSol(){
		
		Double suma = 0.0,cont=0.0;
		
		for (int[][] Var : OptimoPromedio) {
			for (int i=0; i < Var.length ; i++) {
				
				for (int j = 0; j < Var[0].length ; j++) {
					
					suma=suma+Var[i][j];
					cont=cont+1;
					
				}	
			}
		}
		
		return suma/cont;
	}
	
public Double PromedioPoblacion(){
		
		Double suma = 0.0,cont=0.0;
		
		for (int[][] Var : PoblacProm) {
			for (int i=0; i < Var.length ; i++) {
				for (int j = 0; j < Var[0].length ; j++) {
					
					suma=suma+Var[i][j];
					cont=++cont;
					
				}	
			}
		}
		
		return suma/cont;
	}

public void setResumen(Double promedioMejorSol, int poblacionPromedio) {
	// TODO Auto-generated method stub
	this.PromedioMejorSol.add(promedioMejorSol);
	this.PromedioPoblación.add((double) poblacionPromedio);
}


	
}






