package Kernel;

import java.util.Random;
import MCDP.ModelMCDP;
import sun.rmi.runtime.Log;


/**
 * @authors Leandro Vasquez & Roberto Zulantay. 
 *
 */
public class Solucion{
	
	/**
	 * The variable {@originalModel} is used for manipulate the origin and final data.
	 */
	private ModelMCDP originalModel;
	private double selectionProbability;
	private int trials;
	private boolean res;

	public int getTrials() {
		return trials;
	}


	public void setTrials(int trials) {
		this.trials = trials;
	}


	/**
	 * The ArrayList<Solution> {@code neighbourSet} is used for save a set of neighbour for one Solution.
	 */
	//private ArrayList<Flor> neighbourSet;
	
	
    public double getSelectionProbability() {
		return selectionProbability;
	}


	/**
     * This constructor {@code Solution()} generates an random initial solution based on a model MCDP.
     */
	public Solucion(ModelMCDP model, int optionInitialSolution)
	{
		originalModel = model.clone();
		//neighbourSet = new ArrayList<Flor>();
		boolean check = false;
		
		if (optionInitialSolution == 1)
		{
			// Generates a random initial solution.
			// Genera una solucionn consistente, ha pasado por las 3 restricciones y se ha calculado su fitness.
			while (true)
			{
				check = generate_RandomY();
				if (check == true)
				{
					//System.out.println("==================================================== ENCONTRADO_Y");
					break;
				}
				//System.out.println("==================================================== AGAIN Y");
			}
			while (true)
			{
				check = generate_ManualZ();
				if (check == true)
				{
					//System.out.println("==================================================== ENCONTRADO Z");
					break;
				}
				//System.out.println("==================================================== AGAIN Z");
			}
			int objectiveFunctionValue = originalModel.objectiveFunction();
			originalModel.setLocalZ(objectiveFunctionValue);
		}
		if (optionInitialSolution == 2)
		{
			// Generates a random initial solution.
			// Genera una solucionn consistente, ha pasado por las 3 restricciones y se ha calculado su fitness.
			while (true)
			{
				check = Flor_ManualWithRandomBitB_Y();
				if (check == true)
				{
					//System.out.println("==================================================== ENCONTRADO_Y");
					break;
				}
				//System.out.println("==================================================== AGAIN Y");
			}
			while (true)
			{
				check = generate_ManualZ();
				if (check == true)
				{
					//System.out.println("==================================================== ENCONTRADO Z");
					break;
				}
				//System.out.println("==================================================== AGAIN Z");
			}
			int objectiveFunctionValue = originalModel.objectiveFunction();
			originalModel.setLocalZ(objectiveFunctionValue);
		}
		
	}
	
	
	public boolean Cambio(int newValue){
		
		boolean check;
		// Generates a random initial solution.
		// Genera una solucionn consistente, ha pasado por las 3 restricciones y se ha calculado su fitness.
		while (true)
		{
			int val=(int)(Math.random()*getModel().getMmax());
			check = Flor_ManualWithCambio(val);
			
			if (check == true)
			{
				//System.out.println("==================================================== ENCONTRADO_Y");
				break;
			}
			//System.out.println("==================================================== AGAIN Y");
		}
		while (true)
		{
			check = generate_ManualZ();
			if (check == true)
			{
				//System.out.println("==================================================== ENCONTRADO Z");
				break;
			}
			//System.out.println("==================================================== AGAIN Z");
		}
		int objectiveFunctionValue = originalModel.objectiveFunction();
		
		originalModel.setLocalZ(objectiveFunctionValue);
			
		return true;
		
	}
	
	
	
	public Solucion(ModelMCDP model)
	{
		originalModel = model.clone();
	}

	private boolean generate_RandomY()
	{
		int Y[][] = new int[originalModel.getM()][originalModel.getC()];
		
		// Generar matriz M*C randomicamente
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
				if (Z[j][k] >= valorAlto)
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
		
		originalModel.setZ(Z);
		
		int objectiveFunctionValue = originalModel.objectiveFunction();
		originalModel.setLocalZ(objectiveFunctionValue);
		
		return originalModel.checkConstraint();
	}

	 public void mostrarSolucion()
	{
		originalModel.showInitialData();
	
		return;
	}


	 public ModelMCDP getModel(){
		 
		 
		 return originalModel;
		 
	 }
	 
//	 public int getFunObj(){
//			
//			return originalModel.objectiveFunction(originalModel.getA(),originalModel.getY(),originalModel.getZ());	
//			
//			
//	}
	 
	 private boolean Flor_ManualWithRandomBitB_Y()
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
			

			//System.out.println("\n");
			// Save the new matrix.
			this.originalModel.setY(Y);
			
			boolean res1 =originalModel.consistencyConstraint_1() ;
			
			boolean res2 = originalModel.consistencyConstraint_3();
			//System.out.println("C3 "+this.getModel().ColumnaExcede());
			boolean res =  res1 & res2;
			
			//System.out.println("res1 "+ res1 + " res2 "+ res2 + "  ambas => "+ res);
			return res;
		}
	
	 private boolean Flor_ManualWithCambio(int maq)
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
				
			
//			// Find a (x, y) in the matrix with value 1.
//			oldCell = searchCellPosition(Y, 1);
//			
//			// Find a (x, y) in the matrix.
//			newCell = searchCellPositionInRow(Y, 1, oldCell[0]);
//						
			
			Y[oldCell[0]][oldCell[1]] = 0;
			Y[newCell[0]][newCell[1]] = 1;
			
			
			
			// Save the new matrix.
			this.originalModel.setY(Y);
			//System.out.println("Excede"+originalModel.ColumnaExcede());
			
			
			boolean res1 =originalModel.consistencyConstraint_1() ;
			boolean res2 = originalModel.consistencyConstraint_3();
			
			/*Logica*/
			if(this.getTrials()>trials){
				
				int excede=originalModel.ColumnaExcede();
				
				oldCell =searchCellPosition(Y, 1, excede) ;
				newCell = searchCellPositionInRow(Y, 1, oldCell[0]);
				
				Y[oldCell[0]][oldCell[1]] = 0;
				Y[newCell[0]][newCell[1]] = 1;
				
				System.out.println("Excede"+originalModel.ColumnaExcede());
				res2 = originalModel.consistencyConstraint_3();
				
				
			}
			return res1 & res2;
			
		}

	 
	private int[] searchCellPositionInColumn(int[][] Y, int busca, int col){
		 
		 	int result[] = new int[2];
		 	
			while (true)
			{

				int x = (int)(this.getModel().getM()*V4(this.getModel().getM()/100000000));
				int y = col;
				
				int cell = Y[x][y]; 
				if (cell == busca)
				{
					result[0] = x;
					result[1] = y;
					break;
				}	
			}
		 	
		 	return result;
	}
	
	


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
	 
	/* Busca en la fila:x indicada el valor :value */
	 private int[] searchCellPosition(int Y[][], int value, int x)
		{
			Random random = new Random();
			int result[] = new int[2];
			
			int y = 0;
			while (true)
			{
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
	 
	 private int[] searchCellPositionInRow(int Y[][], int value, int row)
		{
			Random random = new Random();
			int result[] = new int[2];
			int y;
			
			
			while (true)
			{
				//System.out.println("Casi");
				//double r= (   (double)Math.random()*32767 / ((double)(32767)+(double)(1)) );
				//y= (int) (row + (row - aux_y)*(r-0.5)*2);
				
				y = (int) (random.nextDouble() * originalModel.getC());
				y= (int) Math.floor(V4(y)*(originalModel.getC()));
				
				
				//y =(int)(Math.random()*this.getModel().getC());
				
				//System.out.println(y);
				
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
	 
	 	//'
	    public static double S1(float x)
	    {
	        return 1/(1 + Math.pow(Math.E, (-2 * x)));
	    }

	    public static double S2(float x)
	    {
	        return 1/(1 + Math.pow(Math.E, (-1 * x)));
	    }

	    public static double S3(float x)
	    {
	        return 1/(1 + Math.pow(Math.E, ((-1 * x)/2) ));
	    }

	    public static double S4(float x)
	    {
	        return 1/(1 + Math.pow(Math.E, ((-1 * x)/3) ));
	    }


	    // V2
	    public static double V2(float x)
	    {
	        return Math.abs( Math.tanh(x) );
	    }

	    // V3
	    public static double V3(float x)
	    {
	        return Math.abs( x / Math.sqrt(1 + Math.pow(x, 2)));
	    }

	    // V4
	    public static double V4(float x)
	    {
	        return Math.abs( (2/Math.PI) * Math.atan( (Math.PI/2) * x));
	    }

	    


	public void setSelectionProbability(double mSelectionProbability) {
        this.selectionProbability = mSelectionProbability;
    }
}
