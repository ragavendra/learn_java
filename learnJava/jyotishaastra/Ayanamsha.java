package jyotishaastra;

import java.util.Calendar;
import jyotishaastra.DegMinSec;

public class Ayanamsha {
	public static double ayanamsha(Calendar cal){
		int year = cal.get(Calendar.YEAR);
		int bd = 3;
		// System.out.println("year is " + year);	
		double A = -6.92416f + 16.90709 * (year/1000.0) - 0.757371 * Math.pow((year/ 1000.0), 2);
		// System.out.println("A is " + A);	
		double B = (cal.get(Calendar.MONTH) - 1 + cal.get(Calendar.DATE)/30.0) * 0.0011574074;
		// System.out.println("B is " + B);	
		return A + B;
	}

	// Mesha is Raashi 1
	public static double nirayaana(int raashi, double ayanamsha, int[] grahaGeo) throws Exception {

		// calc actual graha location
		grahaGeo[0] = absGeo(raashi, grahaGeo);

		// add 360 if less than ayanamsha
		if(DegMinSec.degrees(grahaGeo) < ayanamsha)
			grahaGeo[0] = grahaGeo[0] + 360;

		// System.out.println("Deg is " + DegMinSec.degrees(grahaGeo));
		double niraGraha = DegMinSec.degrees(grahaGeo) - ayanamsha;
		return niraGraha;
	}

	public static int absGeo(int raashi, int[] grahaGeo) throws Exception {
		return (grahaGeo[0] + 30 * ( raashi - 1));
	}

	private static double remainingDistance;

	public static String tithi(int soorya[], int chandra[]) throws Exception {
		// Thithi = Chandra - Soorya Sayaana or Nirayaana
		// ar1 and ar3 are Chandra & Soorya'a Sayaana
		chandra[0] = Ayanamsha.absGeo(1, chandra);
		soorya[0] = Ayanamsha.absGeo(4, soorya);

		// if Chandra is less than Soorya, add 360 to it
		if(chandra[0] < soorya[0])
			chandra[0] = chandra[0] + 360;
		double res = DegMinSec.degrees(DegMinSec.minus(soorya, chandra));
		double tithi = res/12;
		String tithis[] = { "Padya", "Vidiya", "Tadiya", "chavithi", "Panchami", "Sashti", "Saptami", "Ashtami", "Navami", "Dasimi", "Ekadasi", "Dwadasi", "Triodasi", "chaturdasi", "Poornima", "Amavaasya" };

		String paksha = "Shukla Suddha";
		double endLimit = ((int) tithi + 1)* 12;
		remainingDistance = endLimit - res;
		// System.out.println("Remaining " + remainingDistance);
		double elapsed = res%12;

		if(tithi == 29){
			paksha = "Krishna Bahula";
			tithi = 15; // which is Amavaasya or last day of month
		}
		else if(tithi > 15){
			paksha = "Krishna Bahula";
			tithi = tithi - 15;
		}

		StringBuilder stg = new StringBuilder();
		return stg.append(paksha).append(" - ").append(tithis[(int) tithi]).append(" - ").append(elapsed).append(" deg have elapsed").toString();
	}

	public static double durationHours(Calendar cal, int rashi, int[] grahaGeo) throws Exception {
		return nirayaana(rashi, ayanamsha(cal), grahaGeo);	
	}

}


