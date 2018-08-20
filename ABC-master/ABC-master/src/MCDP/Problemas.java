package MCDP;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import Configuración.ExtractorRutas;
import Configuración.Log;
import Configuración.ManejoArchivo;


public class Problemas implements ManejoArchivo {
	
	
	private ArrayList<ModelMCDP> problemas;
	
	public Problemas(){
		problemas = new ArrayList<ModelMCDP>();
	}
	// Procesar Archivos
	public void procesarArchivos(){
		String ruta = "System/Data/";
		Log log = new Log();
		
		ExtractorRutas ext = new ExtractorRutas(ruta,"Carga MCDP");
		log.escribirArchivo("Objeto ExtratorRutas Creado");
		
		for(int i=0;i<ext.getDimension();i++){
			
			String archivo = ext.obtenerNombre();
			
			if(this.leerArchivo(ruta+archivo)!=null){
				log.escribirArchivo("Archivo: "+archivo+" leido con exito");
			}else{
				log.escribirArchivo("Error al leer el Archivo: "+archivo);
			}
		}
	}
	

	@SuppressWarnings("unused")
	public Object leerArchivo(String archivo) {
		// TODO Auto-generated method stub
		
		Log log = new Log();
		ModelMCDP model = null;
		
		int M=0,P=0,C=0,Mmax=0,Mmin=0,Sum=0;
		int A[][] = null;
		
		try {
			
			String cadena = null;
			int cont1 = 0, i = 0;
			
			
			FileReader f = new FileReader(archivo);
			BufferedReader b = new BufferedReader(f);
			
			log.escribirArchivo("Buffer de lectura para el archivo: "+archivo+" creados");
			
			
			while((cadena = b.readLine())!=null){
				
				if(cont1 == 0){
					StringTokenizer st = new StringTokenizer(cadena," ");
					int cont2 = 0; 
					while(st.hasMoreTokens()){
						
						String aux = st.nextToken();
						StringTokenizer stAux = new StringTokenizer(aux,"=");
						int cont3 = 0;
						while(stAux.hasMoreTokens()){
							
							String valor = stAux.nextToken();
							if(cont3 == 1){
								// voy viendo que parte del txt estoy
								switch(cont2){
								
									case 0: M = Integer.parseInt(valor);
											break;
									
									case 1: P = Integer.parseInt(valor);
											break;
											
									case 2: C = Integer.parseInt(valor);
											break;
									
									case 3: Mmax = Integer.parseInt(valor);
											break;
									
									case 4: Mmin = Integer.parseInt(valor);
											break;
											
									case 5: Sum  = Integer.parseInt(valor);
											// Al final de la sentencia Declaro la matriz A 
											A = new int[M][P];
											break;
											
								}
							}
							cont3++;
						} 
						cont2++;
					}
					cont1++;
				}else{
					
					StringTokenizer st = new StringTokenizer(cadena," ");
					int j = 0;
					while(st.hasMoreTokens()){
						int valor = Integer.parseInt(st.nextToken());
						A[i][j] = valor;
						j++;
					}
					i++;
				}
				
			}
			
			b.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			log.escribirArchivo("No existe el archivo: "+archivo+" solicitado");
			e.printStackTrace();
		
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.escribirArchivo("Se producido un error de lectura en el archivo: "+archivo+" solicitado");
			e.printStackTrace();
		}
		
		model = new ModelMCDP(M,P,C,Mmax,Sum,A,archivo);
		this.problemas.add(model);

		return model;
		
	}
	
	@SuppressWarnings("unused")
	public Object leerArchivo2(String archivo) {
		// TODO Auto-generated method stub
		
		Log log = new Log();
		ModelMCDP model = null;
		
		int M=0,P=0,C=0,Mmax=0,Mmin=0,Sum=0;
		int A[][] = null;
		
		try {
			
			String cadena = null;
			int cont1 = 0, i = 0;
			
			
			FileReader f = new FileReader(archivo);
			BufferedReader b = new BufferedReader(f);
			
			log.escribirArchivo("Buffer de lectura para el archivo: "+archivo+" creados");
			
			
			while((cadena = b.readLine())!=null){
				
				if(cont1 == 0){
					StringTokenizer st = new StringTokenizer(cadena," ");
					int cont2 = 0; 
					while(st.hasMoreTokens()){
						String aux = st.nextToken();
						StringTokenizer stAux = new StringTokenizer(aux,"=");
						String valor = stAux.nextToken();
							if(cont1 == 0){
								// voy viendo que parte del txt estoy
								switch(cont2){
								
									case 0: M = Integer.parseInt(valor);
											break;
									
									case 1: P = Integer.parseInt(valor);
											break;
											
									case 2: C = Integer.parseInt(valor);
											break;
									
									case 3: Mmax = Integer.parseInt(valor);
											break;
									
								}
								// Al final de la sentencia Declaro la matriz A 
								A = new int[M][P];
						
						
						} 
						cont2++;
					}
					cont1++;
				}else{
					
					StringTokenizer st = new StringTokenizer(cadena," ");
					int j = 0;
					while(st.hasMoreTokens()){
						int valor = Integer.parseInt(st.nextToken());
						//log.escribirArchivo("valor : "+valor);
						A[i][j] = valor;
						j++;
					}
					i++;
				}
				
			}
			
			b.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			log.escribirArchivo("No existe el archivo: "+archivo+" solicitado");
			e.printStackTrace();
		
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.escribirArchivo("Se producido un error de lectura en el archivo: "+archivo+" solicitado");
			e.printStackTrace();
		}
		
		model = new ModelMCDP(M,P,C,Mmax,Sum,A,archivo);
		this.problemas.add(model);

		return model;
		
	}

	
	public ArrayList<ModelMCDP> getProblemas() {
		return problemas;
	}

	public void setProblemas(ArrayList<ModelMCDP> problemas) {
		this.problemas = problemas;
	}
	
	public ModelMCDP getProblema(int index){
		
		return this.problemas.get(index);
	
	}
	
	public String getNombreProblema(int index){
		
		return this.problemas.get(index).getArchivo();
	
	}
	
	
	/********************************************************************************/
	/******************************* METODOS NO UTILIZADOS **************************/
	/********************************************************************************/
	
	public void escribirArchivo(String text) {
		// TODO Auto-generated method stub
		// Metodo no utilizado
		return;
	}
	/******************************************************************************/
	
	public boolean detectarArchivo() {
		// TODO Auto-generated method stub
		return false;
	}
	/******************************************************************************/
	/******************************************************************************/
	/******************************************************************************/
}
