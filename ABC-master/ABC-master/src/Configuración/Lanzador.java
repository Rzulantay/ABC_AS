package Configuración;

import java.util.Calendar;
import java.util.GregorianCalendar;

import Kernel.ArtificialBeeColony;
import Kernel.DataResult;
import Kernel.InterCambio;
import Kernel.Solucion;
import MCDP.Problemas;
import Configuración.*;

public class Lanzador {

	private int i;

	public boolean ejecutarAplicacion() {

		Log log = new Log();

		// Ve si existe el fichero LOG si no existe lo Crea
		if (log.detectarArchivo()) {

			// Abre el archivo config.txt si no existe crea uno por defecto
			ConfigInicialABC config = new ConfigInicialABC();
			config.detectarArchivo();


			Problemas problemas = new Problemas();
			problemas.procesarArchivos();			

			for (i = 0; i < problemas.getProblemas().size(); i++) {

				log.escribirArchivo("Inicio problema " + i + " " + problemas.getNombreProblema(i));

				
					long time_start, time_end;
					time_start = System.currentTimeMillis();
					time_end = System.currentTimeMillis();
					

						ArtificialBeeColony ABC = (ArtificialBeeColony) config.leerArchivo("archivo");
						time_start = System.currentTimeMillis();
						
						InterCambio sol = ABC.algorithm(problemas.getProblema(i));
						
						
						time_end = System.currentTimeMillis();
					
					
				}
				
				Excel ex = new Excel();
//				ex.writerResultExcel(result, problemas.getNombreProblema(i), resultPob, resultTime,aux.getEjecuciones(),aux.getAbejas());
				
				
			}

		return true;
	}
}
