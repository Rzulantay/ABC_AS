  package MCDP;

import java.util.Random;






public class Neighbour  implements Comparable <Neighbour>, Cloneable {
	
	/**
	 * The variable {@originalModel} is used for manipulate the origin and final data.
	 */
	private ModelMCDP originalModel;
	
	// crea un vecino dado un modelo
	public Neighbour(ModelMCDP model)
	{
		originalModel = model.clone();
	}



	// este permite crear vecinos de forma random diferentes.
	public Neighbour(ModelMCDP model, int option)
	{
		originalModel = model.clone();
		boolean check;

		// Opcion Boctor Machine con random Z (genera muchas soluciones infactibles, pero converge mas rapido)

		// Opcion profe (lenta y no genera muchos infactibles)
		if (option == 1)
		{
			while (true)
			{
				check = neighbour_ManualWithRandomBitB_Y();
				if (check == true)
				{
					//System.out.println("N==================================================== ENCONTRADO_Y");
					break;
				}
				//System.out.println("N==================================================== AGAIN Y");
			}
			while (true)
			{
				check = neighbour_ManualZ();
				if (check == true)
				{
					//System.out.println("N==================================================== ENCONTRADO Z");
					break;
				}
				//System.out.println("N==================================================== AGAIN Z");
			}
			int objectiveFunctionValue = originalModel.objectiveFunction();
			//System.out.println(objectiveFunctionValue);
			originalModel.setLocalZ(objectiveFunctionValue);
		}
		
		if (option == 2)
		{
			while (true)
			{
				check = neighbour_ManualWithRandomBitB_Y();
				if (check == true)
				{
					//System.out.println("N==================================================== ENCONTRADO_Y");
					break;
				}
				//System.out.println("N==================================================== AGAIN Y");
			}
			while (true)
			{
				check = neighbour_RandomZ();
				if (check == true)
				{
					//System.out.println("N==================================================== ENCONTRADO Z");
					break;
				}
				//System.out.println("N==================================================== AGAIN Z");
			}
			int objectiveFunctionValue = originalModel.objectiveFunction();
			originalModel.setLocalZ(objectiveFunctionValue);
		}
	}
	
	
	
	
	public ModelMCDP getOriginalModel()
	{
		return originalModel;
	}


	public int compareTo(Neighbour o)
	{
		int f = o.getFitness();
		return this.getFitness() - f;
	}
	
	/**
	 * Creates a new Solution object as a neighbor of this solution.<br>
	 * This new Solution object is created by simply randomly chosen any cell.<br>
	 * For MCDP problem to generate a neighboring solution just change a consistent bit to the solution.<br>
	 * @return Solution object
	 */
	private boolean neighbour_ManualWithRandomBitB_Y()
	{
		// Determine a new solution matrix Y
		int[] oldCell, newCell;
		int Y[][] = new int[originalModel.getM()][originalModel.getC()];
	
		// Add matrix Y into temp matrix Y.
		for (int i = 0; i < originalModel.getM(); i++)
		{
			for (int k = 0; k < originalModel.getC(); k++)
			{
			    Y[i][k] = originalModel.getY()[i][k];
			}
		}

		// Find a (x, y) in the matrix with value 1.
		oldCell = searchCellPosition(Y, 1);
		
		// Find a (x, y) in the matrix.
		newCell = searchCellPositionInRow(Y, 1, oldCell[0]);
		
		// Change the values to generate a new neighbor.
		// Example
		// [x,y] <=> [0,0] = 1
		// [x,y+1] <=> [0,1] = 0
		// after->
		// [x,y] <=> [0,0] = 0
		// [x,y+1] <=> [0,1] = 1
		Y[oldCell[0]][oldCell[1]] = 0;
		Y[newCell[0]][newCell[1]] = 1;
		
		// Save the new matrix.
		this.originalModel.setY(Y);
		
		return originalModel.consistencyConstraint_1() & originalModel.consistencyConstraint_3();
	}
	
	private boolean neighbour_ManualZ()
	{
		int Z[][] = new int[originalModel.getP()][originalModel.getC()];
		int[] indexK = new int[originalModel.getM()];
		
		// Generar matriz temporal indexK
		// requisito que la matriz Y sea consistente a las restricciones.
		for (int i = 0; i < originalModel.getM(); i++)
		{
			// 
			for (int k = 0; k < originalModel.getC(); k++)
			{
				if (originalModel.getY()[i][k] == 1)
				{
					indexK[i] = k;
				}
			}
		}
		
		// En este punto se debe de calcular la nueva matriz Z de forma manual
		// Create P*C matrix <--> Z[][] de forma manual
		// Inicializar P*C
		for (int j = 0; j < originalModel.getP(); j++)
		{
			for (int k = 0; k < originalModel.getC(); k++)
			{
				Z[j][k] = 0;
			}
		}

		// sumar los unos en la matriz A, pero separandolos por celdas, revisar vectorM
		for (int j = 0; j < originalModel.getP(); j++)
		{
			for (int i = 0; i < originalModel.getM(); i++)
			{
				if (originalModel.getA()[i][j] == 1)
				{
					// obtener indice de la matriz Y (M*C)
					int index = indexK[i];
					Z[j][index] = Z[j][index] + 1;
				}
			}
		}

		//en la matriz Z, asignar un 1 al valor mas alto de la fila.
		for (int j = 0; j < originalModel.getP(); j++)
		{
			int valorAlto = Integer.MIN_VALUE;
			int posicionK = 0;
			for (int k = 0; k < originalModel.getC(); k++)
			{
				if (Z[j][k] > valorAlto)
				{
					posicionK = k;
					valorAlto = Z[j][k];
				}
			}
			//llenar de ceros la fila K
			for (int k = 0; k < originalModel.getC(); k++)
			{
				Z[j][k] = 0;
			}
			// asignar la posicionK el valor 1, ya que es el valor mas alto.
			Z[j][posicionK] = 1;	
		}	

		// Save the new matrix.
		originalModel.setZ(Z);
				
		return originalModel.checkConstraint();
	}
	
	private boolean neighbour_RandomZ()
	{		
		// Determine a new solution matrix Z
		int Z[][] = new int[originalModel.getP()][originalModel.getC()];
		int[] oldCell, newCell;

		for (int j = 0; j < originalModel.getP(); j++)
		{
			for (int k = 0; k < originalModel.getC(); k++)
			{
				Z[j][k] = originalModel.getZ()[j][k];
			}
		}

		// show matrix
		/*System.out.println("Show matrix Z original");
		for (int j = 0; j < originalModel.getP(); j++)
		{
			for (int k = 0; k < originalModel.getC(); k++)
			{
				System.out.print(Z[j][k]);
			}
			System.out.println("");
		}*/

		// Find a (x, y) in the matrix with value 1.
		oldCell = searchCellPosition(Z, 1);

		// Find a (x, y) in the matrix.
		newCell = searchCellPositionInRow(Z, 1, oldCell[0]);

		// Change the values to generate a new neighbor.
		Z[oldCell[0]][oldCell[1]] = 0;
		Z[newCell[0]][newCell[1]] = 1;

		//System.out.println("Show matrix Z nueva");
		/*for (int j = 0; j < originalModel.getP(); j++)
		{
			for (int k = 0; k < originalModel.getC(); k++)
			{
				System.out.print(Z[j][k]);
			}
			System.out.println("");
		}*/

		// Save the new matrix.
		originalModel.setZ(Z);

		return originalModel.checkConstraint();
	}
	
	

	/**
	* Dynamically seek the position (x, y) of a value 0 or 1 (depending on the input value) in the matrix.
	* @return int[]: Is a array of size 2, with int[0] is a {@code x} position, and int[1] is a {@code y} position.
	*/
	private int[] searchCellPosition(int Y[][], int value)
	{
		Random random = new Random();
		int result[] = new int[2];
		
		int x = 0;
		int y = 0;
		while (true)
		{
			// Random (i,k) position.
			// (i,k) <=> (x,y).
			x = (int) (random.nextDouble() * originalModel.getM());
			y = (int) (random.nextDouble() * originalModel.getC());
			
			int cell = Y[x][y]; 
			if (cell == value)
			{
				result[0] = x;
				result[1] = y;
				break;
			}	
		}

		return result;
	}
	
	/**
	 * Looking for a cell position (x, y) in the matrix, with a different value entered in the function.<br>
	 * The position is found by random manner.
	 * 
	 */
	private int[] searchCellPositionInRow(int Y[][], int value, int row)
	{
		Random random = new Random();
		int result[] = new int[2];
		
		int y = 0;
		while (true)
		{
			// Random new y position.
			y = (int) (random.nextDouble() * originalModel.getC());
			
			int cell = Y[row][y]; 
			if (cell != value)
			{
				result[0] = row;
				result[1] = y;
				break;
			}	
		}

		return result;
	}
	
	/**
	 * Returns cost of this solution object.
	 * @return
	 */
	public int getFitness()
	{
		return originalModel.getLocalZ();
	}
	
	/**
	 * For debug
	 */
	@Override
	public String toString()
	{
		return "[ fitness = " + originalModel.getLocalZ() +"]";
	}
	
	/**
     * This method {@code showAllData} show all solution for MCDP.
     */
	public void showNeighbourAllData()
	{
		originalModel.showAllData();
	}
	
	/**
     * This method {@code showEscentialData} show the essential data solution for MCDP.
     */
	public void showNeighbourEssentialData()
	{
		originalModel.showEssentialData();
	}
	
	
}
