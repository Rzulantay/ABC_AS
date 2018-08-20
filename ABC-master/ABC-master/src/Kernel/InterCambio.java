package Kernel;

import java.util.ArrayList;

import Kernel.Flor;
import Kernel.Solucion;

 public class InterCambio{
	
	/* PARAMETROS INTERCAMBIO*/
	
	private int MejorSol;
	private int NP;
	private int NumPoblacion;
	private ArrayList<Solucion> flor = new ArrayList<Solucion>();
	private ArrayList<Solucion> florMutan = new ArrayList<Solucion>();
	private ArrayList<Flor> MutanFlor = new ArrayList<Flor>();
	private int [] tries;
	private Solucion solucion=null;
	
	
	public Solucion getSolucion() {
		return solucion;
	}


	public void setSolucion(Solucion solucion) {
		this.solucion = solucion;
	}


	public InterCambio(){
		}
	
	public InterCambio(Solucion gBest){
		 this.solucion = gBest;
	}
	
	
	public InterCambio(int MejorSol,int np){
		 this.setMejorSol(MejorSol);
		 this.setNp(np);
	}
	
	public ArrayList<Solucion> getFlorMutan() {
		return florMutan;
	}

	public void setFlorMutan(ArrayList<Solucion> florMutan) {
		this.florMutan = florMutan;
	}

	public int getMejorSol() {
		return MejorSol;
	}

	public void setMejorSol(int mejorSol) {
		this.MejorSol = mejorSol;
	}

	public ArrayList<Solucion> getFlor() {
		return flor;
	}

	public void setFlor(ArrayList<Solucion> flor) {
		flor.ensureCapacity(flor.size());
		this.flor = flor;
	}

	public ArrayList<Flor> getMutanFlor() {
		return MutanFlor;
	}

	public void setMutanFlor(ArrayList<Flor> mutanFlor) {
		MutanFlor.ensureCapacity(mutanFlor.size());
		this.MutanFlor = mutanFlor;
	}

	public void settrial(int[] tries) {
		this.tries = tries;
		
	}

	public int[] gettries() {
		return this.tries;
	}

	public void setNp(int np) {
		this.NP = np;
		
	}
	 
	public int getNp() {
		return this.NP;
	}

	public void setNumPoblacion(int size) {
		 this.NumPoblacion = size;
		
	}

	
 }
