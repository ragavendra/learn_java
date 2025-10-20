package org.jyothishaastra;

import java.util.Calendar;

// import jyothishaastra.DegMinSec;

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

	static double remainingDistance;
    private static int tithiSector;
    private static double tithiDeg;

	public static String tithi(int soorya[], int chandra[], int suryaRaashi, int chandraRaashi) throws Exception {
		// Thithi = Chandra - Soorya Sayaana or Nirayaana
		// ar1 and ar3 are Chandra & Soorya'a Sayaana
		chandra[0] = Ayanamsha.absGeo(chandraRaashi, chandra);
		soorya[0] = Ayanamsha.absGeo(suryaRaashi, soorya);
        System.out.printf("Abs geo of Surya is %d & Chandra is %d\n", soorya[0], chandra[0]);

		// if Chandra is less than Soorya, add 360 to it
		if(chandra[0] < soorya[0])
			chandra[0] = chandra[0] + 360;

		tithiDeg = DegMinSec.degrees(DegMinSec.minus(soorya, chandra));
		double tithi = tithiDeg/12;
		String tithis[] = { "Padya", "Vidiya", "Tadiya", "chavithi", "Panchami", "Sashti", "Saptami", "Ashtami", "Navami", "Dasimi", "Ekadasi", "Dwadasi", "Triodasi", "chaturdasi", "Poornima", "Amavaasya" };

        // Shukla Suddha tithi for less than 15
		String paksha = "Shukla Suddha";

		double endLimit = ((int) tithi + 1)* 12;
		remainingDistance = endLimit - tithiDeg;
		 System.out.println("Remaining " + remainingDistance);
		double elapsed = tithiDeg%12;
        tithiSector = (int) Math.round(tithi);

		if(tithi == 29){
			paksha = "Krishna Bahula";
			tithi = 15; // which is Amavaasya or last day of month
		}
		else if(tithi > 15){
			paksha = "Krishna Bahula";
			tithi = tithi - 15;
		}

        return "%s - %s - %4.9f deg have elapsed".formatted(paksha, tithis[(int) tithi], elapsed);
//      StringBuilder stg = new StringBuilder();
//		return stg.append(paksha).append(" - ").append(tithis[(int) tithi]).append(" - ").append(elapsed).append(" deg have elapsed").toString();
	}

    public static double tithiEnd(int[] surMot, int[] chaMot, double remainingDistance) throws Exception {
//        return DegMinSec.degrees(new int[]{tithiSector * 12, 0, 0}) - tithiDeg;
        System.out.printf("1 is %4.9f\n", DegMinSec.degrees(chaMot) * 60);
        System.out.printf("2 is %4.9f\n", DegMinSec.degrees(surMot) * 60);
        System.out.printf("3 is %4.9f\n", DegMinSec.toMinutes(remainingDistance));
        return (remainingDistance/ (DegMinSec.degrees(chaMot) - DegMinSec.degrees(surMot) ))  * 24;
    }

	public static double durationHours(Calendar cal, int rashi, int[] grahaGeo) throws Exception {
		return nirayaana(rashi, ayanamsha(cal), grahaGeo);	
	}

}


