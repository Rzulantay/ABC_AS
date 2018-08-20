package Kernel;



import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

import javax.swing.plaf.synth.SynthSplitPaneUI;
import javax.swing.text.DefaultEditorKit.BeepAction;

import org.omg.CORBA.Current;

import com.sun.xml.internal.ws.policy.spi.PolicyAssertionValidator.Fitness;

import Configuración.Log;
import Kernel.Flor;
import Kernel.Solucion;
import MCDP.ModelMCDP;
import MCDP.Neighbour;
import MCDP.Solution;

public class ArtificialBeeColony {

	/* PARAMETROS METAHEURiSTICA */
	
	int Ejecuciones;
	int Tries;
	int MejorSol;
	int limite;
	boolean explorar;
	int Switch ;
	
	/*Parametros ABEJAS*/
	int MAX_LENGTH; 		/*The number of parameters of the problem to be optimized*/
    int Abejas; 			/*The number of total bees/colony size. employed + onlookers*/
    int FOOD_NUMBER; 		/*The number of food sources equals the half of the colony size*/
    
    int LIMIT;  			/*A food source which could not be improved through "limit" trials is abandoned by its employed bee*/
    int MAX_EPOCH; 			/*The number of cycles for foraging {a stopping criteria}*/
    

	Random rand;
    ArrayList<Solucion> foodSources=null;;
    ArrayList<Flor> solutions;
    Solucion gBest;
    int epoch;
    
    int result[]=null;

	
	private ModelMCDP base;
	private double MejorFit;
	private int PosicionMejorFit;
	private Object[] probabilidad;
	private boolean ONtrial=false;
	private boolean OnLocalz=false;
	private boolean ONMejoro=true;
	private int countMejoro;
	private int val;
	private boolean PrevActualConflict=false;

	public ArtificialBeeColony(int Abejas, int NP2,int limite, int Tries, int Ejecuciones) {
		
		this.Abejas = Abejas;
		this.FOOD_NUMBER = NP2 ;
		this.limite = limite;
		this.Tries = Tries;
		this.Ejecuciones = Ejecuciones;
	    this.result = new int[limite];
		this.Switch = 0;
		
	}

	public void setSol(int solucion) {
		this.MejorSol = solucion;
		return;
	}

	public int getSol() {
		return MejorSol;
	}

	public int getTries() {
		return Tries;
	}

	public int getAbejas() {
		return Abejas;
	}
	
	public void setAbejas(int bee) {
		this.Abejas = bee;
	}
	

	double CalculateFitness(double fun) {
		double result = 0;

		if (fun >= 0) {
			result = 1 / (fun + 1);
		} else {
			result = 1 + Math.abs(fun);
		}
		return result;
	}

	public InterCambio algorithm(ModelMCDP model) {

		foodSources = new ArrayList<Solucion>();
    	rand = new Random();
        boolean done = false;
        epoch = 0;
        
        initialize(model);
        memorizeBestFoodSource();
        
        while(!done) {
            if(epoch < limite) {
            	
            	sendEmployedBees();
                	getFitness();
                	calculateProbabilities();
                
//                System.out.println("[ABC TERM1] "+gBest.getModel().getLocalZ());
                sendOnlookerBees();
                	memorizeBestFoodSource();
//                System.out.println("[ABC TERM2] "+gBest.getModel().getLocalZ());
                sendScoutBees(model);
//                System.out.println("[ABC TERM3] "+gBest.getModel().getLocalZ());
                
                epoch++;
            } else {
                done = true;
            }
            
            for(int z=0;z<epoch;z++) {
            	result[epoch-1] =gBest.getModel().getLocalZ();
            }
            
        }
        
        /*Muestras de Resultado*/
        for(int z=0;z<epoch;z++) {
        	System.out.println(result[z]);
        	System.out.println(foodSources.size());
        }
        
        InterCambio Sol = new InterCambio(gBest);
        
        System.out.println("[ABC TERM] "+gBest.getModel().getLocalZ());
        
        return Sol;
		
	}
	
	/* Initializes all of the solutions' placement of queens in ramdom positions.
	 *
	 */ 
   public void initialize(ModelMCDP model) {
       
	   for(int i = 0; i < FOOD_NUMBER; i++) {
           Solucion newHoney = new Solucion(model,1);
           
           foodSources.add(newHoney);
       }
   }


	private void sendScoutBees(ModelMCDP model) {
		Solucion currentBee = null;
        
        for(int i =0; i < FOOD_NUMBER; i++) {
            
        	currentBee = foodSources.get(i);
            
        	if(ONtrial)
        		System.out.println("Trial " +" i "+ i +"| Trial:"+currentBee.getTrials());

        	Solucion newHoney = new Solucion(model,1);
//        	System.out.println("currentBee.getTrials() :"+currentBee.getTrials() + " i: "+i);
        	
        	
//        		if( Switch == 0) {
//
//        			foodSources.add(newHoney);
//		        	FOOD_NUMBER = foodSources.size();
//		            
//		        	if(foodSources.size() == 20) {
//		        		Switch=1;	
//		        	}
//		        }else {
//		        	
//		        	
//		        		        	
//	        		
////		        	System.out.println("Random"+ Math.round((foodSources.size()*rand.nextFloat())));
//	        			
//		        	if(foodSources.size() <= 6) {
//		        		Switch=0;
//		        	}
//		        	
//		        	if(foodSources.get(1).getModel().getLocalZ() != currentBee.getModel().getLocalZ()) {
//		        		foodSources.remove(1);	
//		        	}else
//		        	{
//		        		foodSources.remove(0);
//		        	}
//		        	
//		        	FOOD_NUMBER = foodSources.size();	
//		        	
//        		}
        		
        		
//        		System.out.println("Food NUM "+foodSources.size());
        	
        	
        	
        	
        	if(currentBee.getTrials() >= Tries && gBest.getModel().getLocalZ() != currentBee.getModel().getLocalZ()){
        		currentBee = newHoney;
            	try {

//            		System.out.println("FNUM "+foodSources.size());
            		foodSources.set(i, currentBee);
            		if(ONtrial)
            			System.out.println("Nueva Solucion");
					
				} catch (Exception e) {
					foodSources.set(foodSources.size()-1, currentBee);
				}

            }
        }
        
        
        
        
        
	}

	private void memorizeBestFoodSource() {
		
		gBest = foodSources.get(0);
		int min = gBest.getModel().getLocalZ();
		
		int Fitness =0;
		
		
		for (int i = 0; i < foodSources.size(); i++) {
			Fitness = foodSources.get(i).getModel().objectiveFunction();
			//System.out.println(Fitness +" i:"+i);
			if(Fitness < min){
				min=Fitness;
			    gBest = foodSources.get(i);
			}
			
		}
		 
		
	}

	private void sendOnlookerBees() {
		// TODO Auto-generated method stub
		int neighborBeeIndex = 0;
        Solucion currentBee = null;
        Solucion neighborBee = null;

//        System.out.println("[SEND ONLOOKER]____ ACTUAL MEJOR SOLUCION GLOBAL"+ gBest.getModel().objectiveFunction());
//        System.out.println("\n");
        
        for(int i = 0; i < FOOD_NUMBER; i++) {
        	currentBee = foodSources.get(i);
            if(rand.nextDouble() < currentBee.getSelectionProbability()) {
                
                neighborBeeIndex = getExclusiveRandomNumber(FOOD_NUMBER-1, i);
	            neighborBee = new Solucion( foodSources.get(neighborBeeIndex).getModel());
	            sendToWork(currentBee, neighborBee,i);
	        }
            i++;
            if(i == FOOD_NUMBER) {
                i = 0;
            }
            
        }
        
    
        
        
        
	}

	private void calculateProbabilities() {
		Solucion thisFood = null;
        double maxfit = foodSources.get(0).getModel().objectiveFunction();
        
        for(int i = 1; i < FOOD_NUMBER; i++) {
            thisFood = foodSources.get(i);
            if(thisFood.getModel().getLocalZ() > maxfit) {
                maxfit = thisFood.getModel().getLocalZ();
            }
        }
         
        for(int j = 0; j < FOOD_NUMBER; j++) {
            thisFood = foodSources.get(j);
            thisFood.setSelectionProbability((0.9*(thisFood.getModel().objectiveFunction()/maxfit))+0.1);
            //System.out.println("proibabilidad "+j+" : "+thisFood.getSelectionProbability());
            
        }
		
	}

	private void getFitness() {
		// TODO Auto-generated method stub
		
		 MejorFit = CalculateFitness(foodSources.get(0).getModel().getLocalZ());
		for (int i=0;i<foodSources.size();i++){
			if (CalculateFitness(foodSources.get(i).getModel().getLocalZ()) > MejorFit)
				MejorFit = CalculateFitness(foodSources.get(i).getModel().getLocalZ());
				PosicionMejorFit = i;
	    }
		
	}

	private void sendEmployedBees() {
		
		int neighborBeeIndex = 0;
        Solucion currentBee = null;
        Solucion neighborBee = null;
        
//        System.out.println("[SEND EMPLOY]____ ACTUAL MEJOR SOLUCION GLOBAL"+ gBest.getModel().objectiveFunction());
//        System.out.println("\n");
        
        for(int i = 0; i < FOOD_NUMBER; i++) {
            
        	neighborBeeIndex = getExclusiveRandomNumber(foodSources.size()-1, i);
            currentBee = foodSources.get(i);
            neighborBee = new Solucion (foodSources.get(neighborBeeIndex).getModel());
            
            
            sendToWork(currentBee, neighborBee,i);
        
        }
//        System.out.println("2 TERMINO DE sendEmployedBees \n");
	}

	
	public void sendToWork(Solucion currentBee, Solucion neighborBee,int i) {
    	 	
//		System.out.println("[ABC]_1 ACTUAL MEJOR SOLUCION GLOBAL"+ gBest.getModel().objectiveFunction());
        //The parameter to be changed is determined randomly
		
        /*
	    v_{ij}=x_{ij}+\phi_{ij}*(x_{kj}-x_{ij}) 
	    solution[param2change]=Foods[i][param2change]+(Foods[i][param2change]-Foods[neighbour][param2change])*(r-0.5)*2;
         */ 
     
		neighborBee.Cambio(val);
		
//		System.out.println("[ABC] NEIGHT < CURRENT "+ neighborBee.getModel().objectiveFunction()+"<"+ currentBee.getModel().objectiveFunction() );
    	
        if(neighborBee.getModel().objectiveFunction() < currentBee.getModel().objectiveFunction()  ) {
        	
        	foodSources.set(i, neighborBee);
        	currentBee = foodSources.get(i);
        	
//        	System.out.println("> [ABC] Nuevo Mejor Fitness"+ currentBee.getModel().objectiveFunction());
//        	System.out.println("[ABC] CURRENT < GBEST "+ currentBee.getModel().objectiveFunction() +" < " +gBest.getModel().objectiveFunction());
        	
        	if(currentBee.getModel().objectiveFunction() < gBest.getModel().objectiveFunction()) {
        		
        		gBest = currentBee;
        		
//        		System.out.println(">> [ABC] NUEVA MEJOR SOLUCION GLOBAL"+ gBest.getModel().objectiveFunction());
//        		System.out.println("\n");
        	}
        	
        	
        }else {
    		currentBee.setTrials(currentBee.getTrials()+1);
//    		System.out.println("Trial:"+ currentBee.getTrials());
    	}
        
//        System.out.println("1 TERMINO DE SEND TO WORK \n");
           
    }
	
	private static double V4(double x)
	   {
	       double s_bin;  
	       s_bin= (2/Math.PI)* Math.atan((Math.PI/10)*x);
	       
	       if(s_bin<0)
	       {
	           s_bin = s_bin*-1;
	       }
	       return s_bin;
	       
	   }
	
    private void setTries(int i) {
		// TODO Auto-generated method stub
		this.Tries = i;
	}

	public int getIndex(int value,int[] aux) {
        int k = 0;
        for(; k < MAX_LENGTH; k++) {
            if(aux[k] == value) {
                break;
            }
        }
        return k;
    }
	
	/* Gets a random number in the range of the parameters
	 *
	 * @param: the minimum random number
	 * @param: the maximum random number
	 * @return: random number
	 */ 
   public int getRandomNumber(int low, int high) {
       return (int)Math.round((high - low) * rand.nextDouble() + low);
   }
	
	 public int getExclusiveRandomNumber(int high, int except) {
	        boolean done = false;
	        int getRand = 0;

	        while(!done) {
	        	getRand = rand.nextInt(high);
	            
	            if(getRand != except){
	                done = true;
	            }
	        }

	        return getRand;     
	    }

	public int getEjecuciones() {
		return this.Ejecuciones;
	}

}
