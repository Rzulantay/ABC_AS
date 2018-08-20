package MCDP;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class Solution {

	private ModelMCDP originalModel;
	private boolean existe;
	
	private ArrayList<Neighbour> neighbourSet;
	
	/**
	 * 
	 * @param model
	 * @param option si viene la opcion 1 se generará una solución Random
	 */
	public Solution(ModelMCDP model, int option){
		
		this.originalModel = model.clone();
		this.neighbourSet = new ArrayList<Neighbour>();
		
		boolean check = false;
		
		if(option == 1){
			//System.out.println("============== GENERACIÓN DE SOLIUCIONES =============");
			while(true){
				
				check = generate_RandomY();
				if (check == true)
				{
					break;
				}
		
			}
			
			while(true){
				
				check = generate_ManualZ();
				// Si encuentra una Solucion
				if (check == true){
					
					break;
				}
				
			}
			int objectiveFunctionValue = originalModel.objectiveFunction();
			originalModel.setLocalZ(objectiveFunctionValue);
		}
		
	}
	
	public Solution(ModelMCDP model)
	{
		originalModel = model.clone();
		neighbourSet = new ArrayList<Neighbour>();
	}
	/**
	 * En el caso que quiera generar solo un vecino
	 * @param fila
	 * @param model
	 */
	public Solution(int fila,int celda, ModelMCDP model, ModelMCDP modelFather){
		
		this.originalModel = modelFather.clone();

		boolean check = false;
		while(true){
			
			check = generate_ManualY(model,fila,celda);
			//if (check == true)
			//{
			break;
		}
		if( check == true){
			while(true){
				check = generate_ManualZ();
				// Si encuentra una Solucion
				if (check == true){
					this.existe = true;
					break;
				}
				
			}
			int objectiveFunctionValue = originalModel.objectiveFunction();
			originalModel.setLocalZ(objectiveFunctionValue);
		}else{
			this.existe = false;
		}
	}
	
	
	private boolean generate_ManualY(ModelMCDP model, int fila, int celda)
	{
		int Y[][] = new int[originalModel.getM()][originalModel.getC()];
		int Y_aux[][] = model.getY();
	
		@SuppressWarnings("unused")
		int columna = 0;
		
		// PRIMERO DEBO IDENTIFICAR DONDE SE ENCUENTRA LA SOLUCION EN LA FILA ENVIADA
		for(int i=0;i<Y_aux[fila].length;i++){
			
			if(Y_aux[fila][i] == 1){
				
				columna = i;
			}
			
		}
		// Generar matriz M*P Manualmente 
		for(int i=0; i<Y.length;i++){
			if(i!=fila){
				for(int j=0; j<Y[i].length; j++){
					Y[i][j] = Y_aux[i][j];
				}
			}else{
				// Lo genero Secuencialmente 
				for(int j=0; j<Y[i].length; j++){
					if(j==celda){
						Y[i][j] = 1;
					}else{
						Y[i][j] = 0;
					}
				}
				
			}
		}
		
		
		
		// Save the new matrix.
		this.originalModel.setY(Y);
		
		return originalModel.consistencyConstraint_1() & originalModel.consistencyConstraint_3();
	}
	/**
     * This method {@code showBestSolutionValue} show the best solution {@code z} for MCDP.<br>
     * Is the best value found for the MBO, not necessary the best value.
     */
	public void showBestSolutionValue()
	{
		originalModel.showDEz();
	}
	
	/**
     * This method {@code showAllData} show all solution for MCDP.
     */
	public void showAllData()
	{
		originalModel.showAllData();
	}
	
	/**
     * This method {@code showEscentialData} show the essential data solution for MCDP.
     */
	public void showEssentialData()
	{
		originalModel.showEssentialData();
	}
	
	
	/**
	 * Se genera Randomicamente un Y
	 * @return
	 */
	private boolean generate_RandomY()
	{
		int Y[][] = new int[originalModel.getM()][originalModel.getC()];
		
		// Generar matriz M*P randomicamente
		for (int i = 0; i < originalModel.getM(); i++)
		{
			Random random = new Random();
			int cell;
			
			// The array is initialized with zeros.
			for (int k = 0; k < originalModel.getC(); k++)
			{
				Y[i][k] = 0;
			}
			
			// Get random value between 1 to Number Cell.
			cell = (int) (random.nextDouble() * originalModel.getC());
			Y[i][cell] = 1;
		}
		
		// Save the new matrix.
		this.originalModel.setY(Y);
		
		return originalModel.consistencyConstraint_1() & originalModel.consistencyConstraint_3();
	}
	
	
	private boolean generate_ManualZ()
	{
		int Z[][] = new int[originalModel.getP()][originalModel.getC()];
		int[] indexK = new int[originalModel.getM()];
		
		for(int i=0; i < originalModel.getM(); i++){
			
			for(int k=0; k<originalModel.getC();k++){
				// GUARDO LA CELDA EN QUE SE ENCUENTRA LAS MAQUINARIAS 
				if( originalModel.getY()[i][k]==1){
					indexK[i]=k;
			
				}
			}
		}
		
		// SE CREA DE FORMA MANUAL LA MATRIZ Z[][] 
		for(int j=0; j<this.originalModel.getP(); j++){
			for(int k=0; k<this.originalModel.getC(); k++){
				
				Z[j][k] = 0;
				
			}
			
		}
		// sumar los unos en la matriz A, pero separandolos por celdas, revisar vectorM
		for(int j=0; j < originalModel.getP();j++){
			
			for(int i=0; i < originalModel.getM(); i++){
				
				if(originalModel.getA()[i][j]==1){
					
					// SE OBTIENE EL INDICE DE LAS MAQUINAS DE LAS MATRIZ Y
					int index = indexK[i];
					Z[j][index] = Z[j][index] + 1;
					
				}
			}
		}
		
		//en la matriz Z, asignar un 1 al valor mas alto de la fila.
		for(int j=0;j<this.originalModel.getP();j++){
			
			int valorAlto = Integer.MIN_VALUE;
			int posicionK = 0;
			
			for(int k=0;k<this.originalModel.getC();k++){
				
				if(Z[j][k] >= valorAlto){
					
					posicionK = k;
					valorAlto = Z[j][k];
				}
			}
			//llenar de ceros la fila K
			for (int k=0; k<this.originalModel.getC();k++){
				
				Z[j][k] = 0;           
			}
			
			Z[j][posicionK] = 1; 
		}
		
		this.originalModel.setZ(Z);
		
		int objectiveFunctionValue = this.originalModel.objectiveFunction();
		this.originalModel.setLocalZ(objectiveFunctionValue);			                                                                                                                                           				
		return originalModel.checkConstraint();
	}
	/**
	 * Creates a neighbor Set of this solution which includes {@code n} elements.<br>
	 * Includes SORT ASC
	 * @param number : n elements (neighbor).
	 */
	public void generateNeighbour(int number, int optionNeighbourSolution)
	{
		neighbourSet.clear();
		
		for (int i = 0; i < number; i++)
		{	
			//System.out.println("Generar Vecino numero: " + i + " de " + number);
			// 1: random Y machine, random Z
			// 2: random Y machine, manual Z
			Neighbour neighbour = new Neighbour(this.originalModel, optionNeighbourSolution);
			neighbourSet.add(i, neighbour);
		}
		
		// AÃ±adir ordenamiento
		sortNeighbours();
	}


	/**
	 * Remove and returns best neighbour of this solution and removes it from the neighbor set.
	 * @return Solution
	 */
	public Solution getAndRemoveBestNeighbour()
	{
		Solution solution = new Solution(neighbourSet.get(0).getOriginalModel());
		neighbourSet.remove(0);

		return solution;
	}
	
	/**
	 * Returns best neighbour of this solution// and removes it from the neighbor set.
	 * @return Solution
	 */
	public Solution getBestNeighbour()
	{
		Neighbour neighbour = neighbourSet.get(0);
		Solution solution = new Solution(neighbour.getOriginalModel());
		return solution;
	}
	
	
	
	/**
	 * Add a new neighbor in the set of neighbors of the solution.
	 */
	public void addNeighbour(Solution solution)
	{
		Neighbour n = new Neighbour(solution.getOriginalModel());
		neighbourSet.add(n);
	}
	
	/**
	 * Sort arrangement neighbors.
	 */
	public void sortNeighbours()
	{
		Collections.sort(neighbourSet);
	}
	
	/**
	 * Removes a neighbor in the set of neighbors of the solution by index.
	 */
	public void removeNeighbour(Solution solution)
	{
		neighbourSet.remove(solution);
	}
	/**
	 * Returns cost of this solution object.
	 * @return
	 */
	public int getFitness()
	{
		return originalModel.getLocalZ();
	}
	
	public int[][] getSolutionY()
	{
		return originalModel.getY();
	}
	
	public int[][] getSolutionZ()
	{
		return originalModel.getZ();
	}
	
	public int[][] getFinalSolution_A()
	{
		return originalModel.getFinalSolution_A();
	}
	
	public double getGroupingEfficiency()
	{
		return originalModel.getGroupingEfficiency();
	}
	
	public double getGroupingEfficacy()
	{
		return originalModel.getGroupingEfficacy();
	}
	
	public double getGCI()
	{
		return originalModel.getGCI();
	}
	
	public double getE_V()
	{
		return originalModel.getE_V();
	}
	
	public double getVoids()
	{
		return originalModel.getVoids();
	}
	
	public double getExceptions()
	{
		return originalModel.getExceptions();
	}
	
	/**
	 * a solution with a smaller fitness value is considered as a small solution.  
	 */
	public int compareTo(Solution o)
	{
		int f = o.getFitness();
		return this.getFitness()-f;
	}
	
	@Override
	public String toString()
	{
		return "[ fitness=" + this.getFitness()+"]";
	}
	
	 
	public ModelMCDP getOriginalModel()
	{
		return originalModel;
	}
	
	/**
	 * returns number of neighbors.
	 * @return
	 */
	public int getNumberNeighbors()
	{
		return neighbourSet.size();
	}

	public boolean isExiste() {
		return existe;
	}

	public void setExiste(boolean existe) {
		this.existe = existe;
	}
}
