package MCDP;


public class ModelMCDP {

	private int M;
	private int P;
	private int C;
	private int Mmax;
	private int Sum;
	
	private int[][] A;
	private int[][] Y;
	private int[][] Z;
	
	private int[][] tempY;
	private int[][] tempZ;
	
	private int localZ;
	private int globalZ;
	
	private String identificator;
	
	private int[][] finalSolution_A;
	private int[][] A_Solution_Temp;
	
	
	private double groupingEfficiency;
	private double groupingEfficacy;
	private double GCI;
	private double E_V;
	private double exceptions;
	private double voids;
	
	private String archivo;
	
	
	
	public ModelMCDP clone()
	{
		ModelMCDP clon = new ModelMCDP(this.M, this.P, this.C, this.Mmax, this.Sum, this.A, this.Y, this.Z, this.localZ, this.globalZ);
		return clon;
	}
	
	public ModelMCDP(int m, int p, int c, int mmax, int sum, int[][] a,	int[][] y, int[][] z, int localZ, int globalZ)
	{
		super();
		identificator = "";
		M = m;
		P = p;
		C = c;
		Mmax = mmax;
		Sum = sum;
		A = a;
		Y = y;
		Z = z;
		this.localZ = localZ;
		this.globalZ = globalZ;
		
		this.finalSolution_A = new int[this.M][this.P];
		this.A_Solution_Temp = new int[this.M][this.P];
	}

	public ModelMCDP(int M, int P, int C, int mmax, int sum, int[][] A, String archivo)
	{	
		this.identificator = "";
		this.M = M;
		this.P = P;
		this.C = C;
		this.Mmax = mmax;
		this.Sum = sum;
		this.localZ = 0;
		this.globalZ = 0;
		this.setArchivo(archivo);
		
		try
		{
			this.A = new int[this.M][this.P];
			this.finalSolution_A = new int[this.M][this.P];
			this.A_Solution_Temp = new int[this.M][this.P];
			
			for (int i = 0; i < M; i++)
			{
				for(int j = 0; j < P; j++)
				{
					this.A[i][j] = A[i][j];
					this.finalSolution_A[i][j] = 0;
					this.A_Solution_Temp[i][j] = 0;
				}
			}			
		}
		catch(Exception e)
        {
			System.out.println(e);
        }
		
	}
	
	/**
	 * 
	 * @return true si esta correta la restriccion, en caso contrario retorna falso.
	 */
	public boolean consistencyConstraint_1()
	{
		for (int i = 0; i < this.M; i++)
		{
			int flag = 0;
			int zeros = 0;
			for (int k = 0; k < this.C; k++)
			{
				// Check that there is only one true value in the column.
				if (this.Y[i][k] == 1)
				{
					if (flag == 1)
					{
						return false;
					}
					else
					{
						flag = 1;
					}
				}
				else
				{
					zeros = zeros + 1;
				}
			}
			if (zeros == C)
			{
				return false;
			}
			else
			{
				zeros = 0;
			}
		}
		
		return true;
	}
	
	/**
	 * 
	 * @return true si esta correta la restriccion, en caso contrario retorna falso.
	 */	
	public boolean consistencyConstraint_2()
	{
		for (int j = 0; j < this.P; j++)
		{
			int flag = 0;
			int zeros = 0;
			for (int k = 0; k < this.C; k++)
			{
				// Check that there is only one true value in the column.
				if (this.Z[j][k] == 1)
				{
					if (flag == 1)
					{
						return false;
					}
					else
					{
						flag = 1;
					}
				}
				else
				{
					zeros = zeros + 1;
				}
			}
			
			if (zeros == this.C)
			{
				return false;
			}
			else
			{
				zeros = 0;
			}
		}
		
		return true;
	}
	/**
	 * 
	 * @return true si esta correta la restriccion, en caso contrario retorna falso.
	 */	
	public boolean consistencyConstraint_3()
	{
		int count = 0;
		
		for (int k = 0; k < this.C; k++)
		{
			for (int i = 0; i < this.M; i++)
			{
				// Count 1 in the Column
				if (Y[i][k] == 1)
				{
					count++;
				}
			}
			
			// Check Summation <= Mmax
			if (count > this.Mmax)
			{
//				System.out.println("Hay "+ count+" Deben ser "+this.Mmax+" Exede la celda "+k);
				return false;
			}
			
			count = 0;
			
		}
//		System.out.println("Count "+ count);
		return true;
	}
	
	public int ColumnaExcede()
	{
		int count = 0;
		
		for (int k = 0; k < this.C; k++)
		{
			for (int i = 0; i < this.M; i++)
			{
				// Count 1 in the Column
				if (Y[i][k] == 1)
				{
					count++;
				}
			}
			
			// Check Summation <= Mmax
			if (count > this.Mmax)
			{
				//System.out.println("Maquinas "+count);
				return k;
			}
			
			count = 0;
			
		}
//		System.out.println("Count "+ count);
		return -1;
	}
	
	public int ColumnaFalta()
	{
		int count = 0;
		
		for (int k = 0; k < this.C; k++)
		{
			for (int i = 0; i < this.M; i++)
			{
				// Count 1 in the Column
				if (Y[i][k] == 1)
				{
					count++;
				}
			}
			
			// Check Summation <= Mmax
			if (count < this.Mmax)
			{
				return k;
			}
			
			count = 0;
			
		}
//		System.out.println("Count "+ count);
		return 1;
	}
	
	public int objectiveFunction(int[][] A, int[][] Y, int[][] Z)
	{
		int sum = 0;
		
		for (int k = 0; k < this.C; k++)
		{
			for (int i = 0; i < this.M; i++)
			{
				for (int j = 0; j < this.P; j++)
				{
					sum = sum + (A[i][j] * Z[j][k]) * (1 - Y[i][k]);			
				}
			}
		}
		return sum;
	}
	
	public int objectiveFunction()
	{
		int sum = 0;
		
		for (int k = 0; k < this.C; k++)
		{
			for (int i = 0; i < this.M; i++)
			{
				for (int j = 0; j < this.P; j++)
				{
					sum = sum + (this.A[i][j] * this.Z[j][k]) * (1 - this.Y[i][k]);			
				}
			}
		}
		return sum;
	}
	
	public boolean checkConstraint()
	{
		boolean c1 = consistencyConstraint_1();
		boolean c2 = consistencyConstraint_2();
		boolean c3 = consistencyConstraint_3();
		
		if ((c1 == false) )
		{
			//System.out.println("==============================================================FALSE1");
			return false;
		}
		if ( (c2 == false) )
		{
			//System.out.println("==============================================================FALSE2");
			return false;
		}
		if ( (c3 == false))
		{
			//System.out.println("==============================================================FALSE3");
			return false;
		}
		
		return true;
	}
	
	
	public void showMatrizA() {
		System.out.println("A: ");
		for (int i = 0; i < M; i++)
		{
			for (int j = 0; j < P; j++)
			{
				System.out.print(A[i][j]);
			}
			System.out.println("");
		}
	}
	
	public void showAllData()
	{
		System.out.println("Machines number: " + this.M);
		System.out.println("Parts number: " + this.P);
		System.out.println("Cells number: " + this.C);
		System.out.println("Mmax: "+ this.Mmax);
		//System.out.println("Sum: "+ this.Sum);
		System.out.println("DE z: "+ this.localZ);
		System.out.println("Global z: "+ this.globalZ);
		
		System.out.println("A: ");
		for (int i = 0; i < M; i++)
		{
			for (int j = 0; j < P; j++)
			{
				System.out.print(A[i][j]);
			}
			System.out.println("");
		}
		
		System.out.println("Y: ");
		for (int i = 0; i < M; i++)
		{
			for (int k = 0; k < C; k++)
			{
				System.out.print(Y[i][k]);
			}
			System.out.println("");
		}
		
		System.out.println("Z: ");
		for (int j = 0; j < P; j++)
		{
			for (int k = 0; k < C; k++)
			{
				System.out.print(Z[j][k]);
			}
			System.out.println("");
		}
	}
	
	public void showInitialData()
	{
		System.out.println("Machines number: " + this.M);
		System.out.println("Parts number: " + this.P);
		System.out.println("Cells number: " + this.C);
		System.out.println("Mmax: "+ this.Mmax);
		System.out.println("Sum: "+ this.Sum);
		System.out.println("MBO z: "+ this.localZ);
		System.out.println("Global z: "+ this.globalZ);
		
		System.out.println("A: ");
		for (int i = 0; i < M; i++)
		{
			for (int j = 0; j < P; j++)
			{
				System.out.print(A[i][j]);
			}
			System.out.println("");
		}
	}
	
	public void showDEz()
	{
		System.out.println("MBO z: "+ this.localZ);
	}
	
	public void showEssentialData()
	{
		System.out.println("MBO z: "+ this.localZ);
		System.out.println("Y: ");
		for (int i = 0; i < M; i++)
		{
			for (int k = 0; k < C; k++)
			{
				System.out.print(Y[i][k]);
			}
			System.out.println("");
		}
		
		System.out.println("Z: ");
		for (int j = 0; j < P; j++)
		{
			for (int k = 0; k < C; k++)
			{
				System.out.print(Z[j][k]);
			}
			System.out.println("");
		}
		
		convertToFinalMatrix();
		showMatrixSolutionFinal();
		setAndShowMeasures();
	}
	
	/*
	 * Para ver la cantidad de celdas ocupadas, estas deben de tener una columna de ceros
	 */
	/*
	private void calculateFinalCells()
	{
		ArrayList<Integer> machines = new ArrayList<Integer>();
		boolean value = false;
		ArrayList<Integer> cells = new ArrayList<Integer>();
		
		for (int k = 0; k < C; k++)
		{		
			for (int i = 0; i < M; i++)
			{
				if (Y[i][k] == 1)
				{
					value = true;
				}
			}
			
			if (value == false)
			{
				machines.add(k);
			}
			else
			{
				value = false;
			}
			
		}
		
	}*/
	
	
	/**
	 * Esta función ordena la matriz de incidencia de acuerdo al ordenamiento
	 * dado por las matrices de soluciones Y y Z.
	 * Su ordenamiento es de acuerdo al índice de menor a mayor de las maquinas y partes.
	 */
	public void convertToFinalMatrix()
	{
		// Sort Machine Cell
		int ii = 0;
		int jj = 0;
		
		// Ordenar Maquinas
		for (int k = 0; k < C; k++)
		{
			for (int i = 0; i < M; i++)
			{
				// Si Y[maquina][celda] == 1
				if (Y[i][k] == 1)
				{
					// copiar toda la fila de la maquina Y[i][k] en la matriz A2 (final)
					for (int j = 0; j < P; j++)
					{
						A_Solution_Temp[ii][j] = A[i][j];
					}
					ii++;
				}
			}
		}
		
		// Ordenar Partes.
		for (int k = 0; k < C; k++)
		{
			for (int j = 0; j < P; j++)
			{
				// Si Z[parte][celda] == 1
				if (Z[j][k] == 1)
				{
					// copiar toda la fila de la parte Z[j][k] en la matriz A_Solution (final),
					// acá se realiza la copia utilizando la matriz A_Solution.
					for (int i = 0; i < M; i++)
					{
						this.finalSolution_A[i][jj] = A_Solution_Temp[i][j];
					}
					jj++;
				}
			}
		}
	}
	
	public void setAndShowMeasures()
	{	
		int exceptions = 0;
		for (int k = 0; k < C; k++)
		{
			for (int i = 0; i < M; i++)
			{
				for (int j = 0; j < P; j++)
				{
					int sum_temp = (A[i][j] * Z[j][k]) * ( 1 - Y[i][k]);	
					//System.out.println("A["+i+"]["+j+"]="+A[i][j]+" * Z["+j+"]["+k+"]="+Z[j][k]+"  * (1-Y["+i+"]["+k+"]="+Y[i][k]+" ) => sum: "+sum_temp);
					//System.out.println("i,j "+i+","+j +"  "+ A[i][j]+" * "+Z[j][k]+" * (1 - "+Y[i][k]+" ) => sum: "+sum_temp);
					exceptions = exceptions + sum_temp;	
				}
			}
		}
		System.out.println("- Optimum Value: "+ exceptions);
		System.out.println("- Exceptions: "+ exceptions);
		
		// Voids
		int voids = 0;
		for (int k = 0; k < C; k++)
		{
			for (int i = 0; i < M; i++)
			{
				for (int j = 0; j < P; j++)
				{
					int sum_temp = (1 - A[i][j]) * Z[j][k] * Y[i][k];	
					//System.out.println(" (1 - A["+i+"]["+j+"]) * Z["+j+"]["+k+"] * Y["+i+"]["+k+"]) => sum: "+sum_temp);
					voids = voids + sum_temp;	
				}
			}
		}
		System.out.println("- Voids: "+ voids);
		
		// Números de 1 y 0
		int ceros = 0;
		int unos = 0;
		for (int i = 0; i < M; i++)
		{
			for (int j = 0; j < P; j++)
			{
				if (A[i][j] == 0)
				{
					ceros++;
				}
				else
				{
					unos++;
				}
			}
		}
		System.out.println("- Unos totales en matriz de incidencias: "+ unos);
		System.out.println("- Ceros totales en matriz de incidencias: "+ ceros);
		
		
		System.out.println("Medidas");	
		/*
		 * GROUPABIL1TY: an analysis of the properties of binary data matrices for group technology
		 * M. P. CHANDRASEKHARAN a  & R. RAJAGOPALAN b
		 */
		
		double q = 0.5;
		double n1 = (double) (unos-exceptions)/(unos-exceptions+voids);
		double n2 = (double) (M*P-unos-voids)/(M*P-unos+exceptions-voids);
		
		this.groupingEfficiency = (double) q*n1 + (1 - q)*n2;
		
		System.out.println("- Grouping Efficiency [Chandrasekharan and Rajagopolan]: "+ this.groupingEfficiency + " with weighting factor: "+q + ", n1: "+n1+", n2:"+n2);
	
		/*
		 * Grouping efficacy: a quantitative criterion for goodness of block
		 * diagonal forms of binary matrices in group technology
		 * C. SURESH KUMARt and M. P. CHANDRASEKHARANt
		 */
		this.groupingEfficacy = (double) (unos - exceptions)/(unos + voids);
		System.out.println("- Grouping Efficacy [Kumar and Chandrasekharan]: "+ this.groupingEfficacy);
	
		/*
		 * Group capability index (GCI) suggested by Hsu.
		 * 14-0/14
		 */
		this.GCI = (double) (unos - exceptions) / unos;
		System.out.println("- GCI [Hsu]: "+ this.GCI);
		
		/*
		 * Number of exceptions ands voids
		 */
		this.E_V = exceptions + voids;
		System.out.println("- E + V: "+ this.E_V);
		
		/*
		 * Exceptions
		 */
		this.exceptions = exceptions;
		
		/*
		 * Voids
		 */
		this.voids = voids;
	}
	
	public void showMatrixSolutionFinal()
	{
		System.out.println("Final Matrix: ");
		for (int i = 0; i < M; i++)
		{
			for (int j = 0; j < P; j++)
			{
				System.out.print(finalSolution_A[i][j]);
			}
			System.out.println("");
		}
	}
	
	
	
	/**
	 * Getters and setters
	 * 
	 */
	public int getLocalZ()
	{
		return localZ;
	}

	public void setLocalZ(int localZ)
	{
		this.localZ = localZ;
	}

	public int getGlobalZ()
	{
		return globalZ;
	}

	public void setGlobalZ(int globalZ)
	{
		this.globalZ = globalZ;
	}

	public int getM()
	{
		return M;
	}

	public void setM(int m)
	{
		M = m;
	}

	public int getP()
	{
		return P;
	}

	public void setP(int p)
	{
		P = p;
	}

	public int getC()
	{
		return C;
	}

	public void setC(int c)
	{
		C = c;
	}

	public int getMmax()
	{
		return Mmax;
	}

	public void setMmax(int mmax)
	{
		Mmax = mmax;
	}

	public int getSum()
	{
		return Sum;
	}

	public void setSum(int sum)
	{
		Sum = sum;
	}

	public int[][] getA()
	{
		return A;
	}

	public void setA(int[][] a)
	{
		A = a;
	}

	public int[][] getY()
	{
		return Y;
	}

	public void setY(int[][] y)
	{
		Y = y;
	}

	public int[][] getZ()
	{
		return Z;
	}

	public void setZ(int[][] z)
	{
		Z = z;
	}
	
	public int[][] getTempY()
	{
		return tempY;
	}

	public void setTempY(int[][] tempY)
	{
		this.tempY = tempY;
	}

	public int[][] getTempZ()
	{
		return tempZ;
	}

	public void setTempZ(int[][] tempZ)
	{
		this.tempZ = tempZ;
	}

	public String getIdentificator()
	{
		return identificator;
	}

	public void setIdentificator(String identificator)
	{
		this.identificator = identificator;
	}
	
	public int[][] getFinalSolution_A()
	{
		return finalSolution_A;
	}


	public double getGroupingEfficiency()
	{
		return groupingEfficiency;
	}


	public double getGroupingEfficacy()
	{
		return groupingEfficacy;
	}


	public double getGCI()
	{
		return GCI;
	}


	public double getE_V()
	{
		return E_V;
	}
	
	public double getVoids()
	{
		return voids;
	}

	public double getExceptions()
	{
		return exceptions;
	}


	public void setA_Solution_Final(int[][] a_Solution_Final)
	{
		finalSolution_A = a_Solution_Final;
	}


	public void setGroupingEfficiency(double groupingEfficiency)
	{
		this.groupingEfficiency = groupingEfficiency;
	}


	public void setGroupingEfficacy(double groupingEfficacy)
	{
		this.groupingEfficacy = groupingEfficacy;
	}


	public void setGCI(double gCI)
	{
		GCI = gCI;
	}

	public void setE_V(double e_V)
	{
		E_V = e_V;
	}
	
	public void setExceptions(double exceptions)
	{
		this.exceptions = exceptions;
	}

	public void setVoids(double voids)
	{
		this.voids = voids;
	}

	public String getArchivo() {
		return archivo;
	}

	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}
	
}
