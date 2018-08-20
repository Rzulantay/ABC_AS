package Configuración;

import java.util.Calendar;
import java.util.Date;

public class Reloj {

	private int annio;
	private int mes;
	private int dia;
	private int hora;
	private int minuto;
	private int segundo;
	private int miliseg;
	
	private String obtenerFecha(){
		
		Calendar c = Calendar.getInstance();
		this.annio = c.get(Calendar.YEAR);
		this.mes = c.get(Calendar.MONTH);
		this.dia = c.get(Calendar.DATE);
		
		String fecha = Integer.toString(this.annio)+"/"+Integer.toString(this.mes)+"/"+Integer.toString(this.dia)+"  ";
		
		return fecha;
	}
	private String obtenerHora(){
		
		Calendar c = Calendar.getInstance();
		this.hora = c.get(Calendar.HOUR_OF_DAY);
		this.minuto = c.get(Calendar.MINUTE);
		this.segundo = c.get(Calendar.SECOND);
		this.miliseg = c.get(Calendar.MILLISECOND);
		
		String hora = Integer.toString(this.hora)+":"+Integer.toString(this.minuto)+":"+Integer.toString(this.segundo)+":"+Integer.toString(this.miliseg)+"  ";
		
		return hora;
	}
	public String obtenerTiempo(){
		
		return this.obtenerFecha()+this.obtenerHora();
	}
	
	public Long obtenerHoraMili(){
		
		Date fecha = new Date();
		return fecha.getTime();
	}
}
