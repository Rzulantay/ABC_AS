package Configuración;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import Kernel.DataResult;
import MCDP.Problemas;
import jdk.internal.org.objectweb.asm.tree.analysis.Value;

public class Excel {

	private boolean ONExcel=false;

	public void writerResultExcel(int [][] MejorFitness, String nombreXls, int[][] resultPob, long[][] resultTime,int Ejecuciones,int Abejas) {
		// TODO Auto-generated method stub
		
		String nombreArchivo = null;
		
		
		/* EXTRACCIÓN DEL NOMBRE DEL PROBLEMA */
		
		StringTokenizer st = new StringTokenizer(nombreXls,".");
		int cont1 = 0;
		long cont4=0,cont3 = 0;
		while(st.hasMoreTokens()){
			String valor = st.nextToken();
			
			if(cont1 == 0){
				
				StringTokenizer staux = new StringTokenizer(valor,"/");
				int cont2 = 0;
				while(staux.hasMoreTokens()){
				
					String valoraux = staux.nextToken();
					
					if(cont2 == 2){
						
						nombreArchivo = valoraux;
					}
					cont2++;	
				}
			}
			cont1++;
		}
		
		/* FIN DE LA EXTRACCIÓN */
		
		
		@SuppressWarnings("resource")
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Reporte de Fitness");
		
		
		/* Se crea el archivo */ 
		File archivo = new File(nombreArchivo+".xls");
		
		Row fila;
		Cell celda;
		
		
		for(int j=0;j<Abejas;j++){
			fila = sheet.createRow(j);
			
			for(int i=0;i<Ejecuciones;i++){
				celda = fila.createCell(i);
				
				if(ONExcel)
					System.out.println("[Excel] ---> i "+i+" j "+j+"res "+MejorFitness[i][j]);
				
				celda.setCellValue(MejorFitness[i][j]);
				
			}
		}
		
		fila = sheet.createRow(resultPob[0].length+1);
		
		for(int j=0;j<Abejas;j++){
			cont3=0;
			for(int i=0;i<Ejecuciones;i++){
				//cont3=MejorFitness[j][i]+cont3;
				//System.out.println(cont3);
			}
			
			celda = fila.createCell(j);
			celda.setCellValue(cont3/MejorFitness[0].length);
			cont4=(cont3/MejorFitness[0].length)+cont4;
		}
		
		celda = fila.createCell(MejorFitness.length);
		celda.setCellValue(cont4/31);
		
		HSSFSheet sheet2 = workbook.createSheet("Reporte de Poblacion");
		
		for(int i=0;i<resultPob[0].length;i++){
			fila = sheet2.createRow(i);
			
			for(int j=0;j<resultPob.length;j++){
				celda = fila.createCell(j);
				celda.setCellValue(resultPob[j][i]);
			}
		}
		
		fila = sheet2.createRow(resultPob[0].length+1);
		cont4=0;
		for(int j=0;j<resultPob.length;j++){
			cont3=0;
			for(int i=0;i<resultPob[0].length;i++){
				cont3=resultPob[j][i]+cont3;
				//System.out.println(cont3);
			}
			
			celda = fila.createCell(j);
			celda.setCellValue(cont3/resultPob[0].length);
			cont4=(cont3/resultPob[0].length)+cont4;
		}
		
		celda = fila.createCell(resultPob.length);
		celda.setCellValue(cont4/31);
		
		
		
		
		
		
		HSSFSheet sheet3 = workbook.createSheet("Reporte de tiempo");
		
		for(int i=0;i<resultTime[0].length;i++){
			fila = sheet3.createRow(i);
			
			
			for(int j=0;j<resultTime.length;j++){
				celda = fila.createCell(j);
				celda.setCellValue(resultTime[j][i]);
			}
			
		}
		
		fila = sheet3.createRow(resultTime[0].length+1);
		cont4=0;
		for(int j=0;j<resultTime.length;j++){
			cont3=0;
			for(int i=0;i<resultTime[0].length;i++){
				cont3=resultTime[j][i]+cont3;
				//System.out.println(cont3);
			}
			celda = fila.createCell(j);
			celda.setCellValue(cont3);
			cont4=cont3+cont4;
		}
		
		celda = fila.createCell(resultTime.length);
		celda.setCellValue(cont4/resultTime.length);
		
		
		
		/* Escribimos el archivo */
		try {
		      FileOutputStream out = new FileOutputStream(archivo);
		      workbook.write(out);
		      out.close();

		      System.out.println("Archivo creado exitosamente!");

		} catch (IOException e) {
		      System.out.println("Error de escritura");
		      e.printStackTrace();
		}
		
		
	}

	public void writerResultExcelFinish(int[][] MejorFitness, Problemas problemas, int[][] resultPob, long[][] resultTime) {
		// TODO Auto-generated method stub
		
		String nombreArchivo = null;
		
		@SuppressWarnings("resource")
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Reporte Final");
		
		
		/* Se crea el archivo */ 
		File archivo = new File("ResultadoFinal"+".xls");
		
		Row fila;
		Cell celda;
		
		for(int i=0;i<MejorFitness[0].length;i++){
			fila = sheet.createRow(i);
			
			for(int j=0;j<MejorFitness.length;j++){
				celda = fila.createCell(j);
				celda.setCellValue(MejorFitness[j][i]);
			}
		}
		
		fila = sheet.createRow(resultPob[0].length+1);
		
		
		/* Escribimos el archivo */
		try {
		      FileOutputStream out = new FileOutputStream(archivo);
		      workbook.write(out);
		      out.close();

		      System.out.println("Archivo creado exitosamente!");

		} catch (IOException e) {
		      System.out.println("Error de escritura");
		      e.printStackTrace();
		}
		
	}

	public void writerResultExcelFinish(DataResult resultados,int [][] MejorFitness, String nombreXls, int[][] resultPob, long[][] resultTime) {
		// TODO Auto-generated method stub
		
		String nombreArchivo = "hola";
		
		
		/* EXTRACCIï¿½N DEL NOMBRE DEL PROBLEMA */
		
		StringTokenizer st = new StringTokenizer(nombreArchivo,".");
		int cont1 = 0;
		long cont4=0,cont3 = 0;
		while(st.hasMoreTokens()){
			String valor = st.nextToken();
			
			if(cont1 == 0){
				
				StringTokenizer staux = new StringTokenizer(valor,"/");
				int cont2 = 0;
				while(staux.hasMoreTokens()){
				
					String valoraux = staux.nextToken();
					
					if(cont2 == 2){
						
						nombreArchivo = valoraux;
					}
					cont2++;	
				}
			}
			cont1++;
		}
		
		/* FIN DE LA EXTRACCIï¿½N */
		
		
		@SuppressWarnings("resource")
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Reporte de Fitness");
		
		
		/* Se crea el archivo */ 
		File archivo = new File(nombreArchivo+".xls");
		
		Row fila;
		Cell celda;
		
	
		for (int k=0;k<resultados.getPromedioMejorSol().size();k++) {
			fila = sheet.createRow(k);
				
				
				celda = fila.createCell(0);
				celda.setCellValue(resultados.getPromedioMejorSol().get(k));
				celda = fila.createCell(1);
				celda.setCellValue(resultados.getPromedioPoblación().get(k));
				
		}
		
		/* Escribimos el archivo */
		try {
		      FileOutputStream out = new FileOutputStream(archivo);
		      workbook.write(out);
		      System.out.println("Archivo creado exitosamente!");

		} catch (IOException e) {
		      System.out.println("Error de escritura");
		      e.printStackTrace();
		}
		
	}
	
}
