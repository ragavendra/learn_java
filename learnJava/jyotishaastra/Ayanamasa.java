package jyotishaastra;

import java.util.Calendar;
import jyotishaastra.DegMinSec;

public class Ayanamasa {
	public static double calculate(Calendar cal){
		int year = cal.get(Calendar.YEAR);
		// System.out.println("year is " + year);	
		double A = -6.92416f + 16.90709 * (year/1000.0) - 0.757371 * Math.pow((year/ 1000.0), 2);
		// System.out.println("A is " + A);	
		double B = (cal.get(Calendar.MONTH) - 1 + cal.get(Calendar.DATE)/30.0) * 0.0011574074;
		// System.out.println("B is " + B);	
		return A + B;
	}

	// Mesha is Raashi 1
	public static double nirayaana(int raashi, double ayanamasa, int[] grahaGeo) throws Exception {

		// calc actual graha location
		grahaGeo[0] = absGeo(raashi, grahaGeo);

		// add 360 if less than ayanamasa
		if(DegMinSec.degrees(grahaGeo) < ayanamasa)
			grahaGeo[0] = grahaGeo[0] + 360;

		// System.out.println("Deg is " + DegMinSec.degrees(grahaGeo));
		double niraGraha = DegMinSec.degrees(grahaGeo) - ayanamasa;
		return niraGraha;
	}

	public static int absGeo(int raashi, int[] grahaGeo) throws Exception {
		return (grahaGeo[0] + 30 * ( raashi - 1));
	}


}


