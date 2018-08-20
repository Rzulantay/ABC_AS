package Kernel;

import java.util.Random;
import MCDP.ModelMCDP;


public class Flor {
	
	/**
	 * The variable {@originalModel} is used for manipulate the origin and final data.
	 */
	private ModelMCDP originalModel;
	
	// crea una Flor dado un modelo
		public Flor (ModelMCDP model)
		{
			originalModel = model.clone();
		}
		
		// este permite crear Flores de forma random Diferentes :D.
		public Flor(ModelMCDP model, int option)
		{
			originalModel = model.clone();
			boolean check;

			// Opcion Boctor Machine con random Z (genera muchas Flores infactibles, pero converge mas rapido)

			// Opcion profe (lenta :( y no genera muchos infactibles :D )
			if (option == 1)
			{
				while (true)
				{
					check = Flor_ManualWithRandomBitB_Y();
					if (check == true)
					{
						//System.out.println("N==================================================== ENCONTRADO_Y");
						break;
					}
					//System.out.println("N==================================================== AGAIN Y");
				}
				while (true)
				{
					//check = RandomZ();
					check = Flor_ManualZ();
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
					check = Flor_ManualWithRandomBitB_Y();
					if (check == true)
					{
						//System.out.println("N==================================================== ENCONTRADO_Y");
						break;
					}
					//System.out.println("N==================================================== AGAIN Y");
				}
				while (true)
				{
					check = generate_ManualZ();
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
			
			if (option == 3)
			{
				while (true)
				{
					check = Flor_ManualWithRandomBitB_Y();
					if (check == true)
					{
						//System.out.println("N==================================================== ENCONTRADO_Y");
						break;
					}
					//System.out.println("N==================================================== AGAIN Y");
				}
				while (true)
				{
					check = generate_ManualZ();
					//check = RandomZ();
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
		
		// Save the new matrix.
		this.originalModel.setY(Y);
		
		return originalModel.consistencyConstraint_1() & originalModel.consistencyConstraint_3();
	}
	

	private boolean Flor_ManualZ()
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
	
//------------Funciones de transicion Standar-------------------

	
	 public static boolean StandardBoolean(double value)
	    {
	        Random rm = new Random();
	        double randomValue = rm.nextDouble();
	        if ( randomValue <= value )
	        {
	            return true;
	        }
	        else
	        {
	            return false;
	        }
	    }

	    public static int StandardInt(double value)
	    {
	        Random rm = new Random();
	        double randomValue = rm.nextDouble();
	        if ( randomValue <= value )
	        {
	            return 1;
	        }
	        else
	        {
	            return 0;
	        }
	    }

	    public static double StandardDouble(double value)
	    {
	        Random rm = new Random();
	        double randomValue = rm.nextDouble();
	        if ( randomValue <= value )
	        {
	            return 1;
	        }
	        else
	        {
	            return 0;
	        }	
	    }
	
//-------------------------------------------------------------------	
	
	
	
//-------------------------------------------Funciones de transicion V
	
	
	private static double V4(double x)
	   {
	       double s_bin;  
	       s_bin= (2/Math.PI)* Math.atan((Math.PI/2)*x);
	       
	       if(s_bin<0)
	       {
	           s_bin = s_bin*-1;
	       }
	       return s_bin;
	       
	   }
	    
	public static double S1(float x)
    {
        return 1/(1 + Math.pow(Math.E, (-2 * x)));
    }
	
    public static double V3(float x)
    {
        return Math.abs( x / Math.sqrt(1 + Math.pow(x, 2)));
    }
	
    public static double V2(float x)
    {
        return Math.abs( Math.tanh(x) );
    }
	
//--------------------------------------------------------------------------------------------	
	
    private boolean RandomZ()
	{		
		// Determine a new solution matrix Z
		int Z[][] = new int[originalModel.getP()][originalModel.getC()];
		
		for (int j = 0; j < originalModel.getP(); j++)
		{
			for (int k = 0; k < originalModel.getC(); k++)
			{
				Z[j][k] = originalModel.getZ()[j][k];
			}
		}
	
		Random random = new Random();
		int Maq = (int) (random.nextDouble() * originalModel.getM());
		Maq= (int) Math.floor(V4(Maq)*(originalModel.getM()));
		
		for (int k = 0; k < originalModel.getC(); k++)
		{
			Z[Maq][k] = 0;
		}

		int Cel = (int) (random.nextDouble() * originalModel.getC());
		
		Z[Maq][Cel] = 1;
		
		
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
	
			
		// Find a (x, y) in the matrix with value 1.
		oldCell = searchCellPosition(Z, 1);
		// Find a (x, y) in the matrix.
		newCell = searchCellPositionInRow(Z, 1, oldCell[0]);
	
		// Change the values to generate a new neighbor.
		Z[oldCell[0]][oldCell[1]] = 0;
		Z[newCell[0]][newCell[1]] = 1;
		
		
//		Random random = new Random();
//		int Maq = (int) (random.nextDouble() * originalModel.getM());
//		Maq= (int) Math.floor(V2(Maq)*(originalModel.getM()));
//		
//		System.out.println("maq :"+ originalModel.getM());
//		for (int k = 0; k < originalModel.getC(); k++)
//		{
//			Z[Maq][k] = 0;
//		}
//		
//		int Cel = (int) (random.nextDouble() * originalModel.getC());
//		Z[Maq][Cel] = 1;
		
		
		// Save the new matrix.
		originalModel.setZ(Z);
	
		return originalModel.checkConstraint();
	}
	
	private int[] searchCellPositionInRow(int Y[][], int value, int row)
	{
		Random random = new Random();
		int result[] = new int[2];
		int y;
		
		
		while (true)
		{	
			double r= (   (double)Math.random()*32767 / ((double)(32767)+(double)(1)) );
			//y= (int) (row + (row - aux_y)*(r-0.5)*2);
			
			y = (int) (random.nextDouble() * originalModel.getC());
			y= (int) Math.floor(V4(y)*(originalModel.getC()));
			
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
	
	public void mostrarFlor(){
	
		
		originalModel.showInitialData();
		return;
	}
	
	public int getFunObj(){
	
		return originalModel.objectiveFunction(originalModel.getA(),originalModel.getY(),originalModel.getZ());	
		
		
	}

	public ModelMCDP getModel(){
		
		return originalModel;
	}
	
   public static int IntervalDoubleValue(double value, int numberIntervals, double lowerBoundRange, double upperBoundRange)
	    {
	        // Input validations.

	        if (lowerBoundRange > upperBoundRange)
	        {
	            throw new NumberFormatException("Upper bound range it's should be greater than lower bound range.");
	        }

	        if (lowerBoundRange == upperBoundRange)
	        {
	            throw new NumberFormatException("Upper bound range and lower bound range should be different.");
	        }

	        if (numberIntervals < 0)
	        {
	            throw new NumberFormatException("Number of intervals must be greater than zero.");
	        }

	        // Calcular el intervalo
	        double  rangeNumber = (upperBoundRange - lowerBoundRange) / numberIntervals;
	        //System.out.println("range number: "+ rangeNumber);

	        int interval;
	        double tempRange = lowerBoundRange;
	        for (interval = 0; interval < numberIntervals; interval++)
	        {
	            boolean inInterval = check(value, lowerBoundRange, tempRange + rangeNumber);

	            if (inInterval == true)
	            {
	                return interval;
	            }
	            tempRange = tempRange + rangeNumber;
	        }
	        return interval;
	    }

	    private static boolean check(double value, double lowerBound, double upperBound)
	    {
	        if (value >= lowerBound && value <= upperBound)
	        {
	            return true;
	        }
	        else
	        {
	            return false;
	        }
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

}