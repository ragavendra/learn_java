package org.jyothishaastra;

import java.util.Calendar;

// import jyothishaastra.DegMinSec;

public class Tithi {
	static String nakshatras[] = { "Ashwini", "Bharani", "Krithika", "Rohini", "Mrigashira", "Aradra", "Punarvasu", "Pushya", "Ashlesha", "Magha", "Pubha", "Uttara", "Hashta", "Chitta", "Swathi", "Vishakha", "Anuradha", "Jyeshta", "Moola", "Purvashaada", "Uttarashaada", "Shravana", "Dhanishta", "Shathabisha", "Poorvabadha", "Uttarabadha", "Revathi" };

	private static int nakshaIndex;

	// public static double (int[] chandraNiraayana){
	// raashi starting from 0 for Mesha
	public static String byWholeDegrees(int[] chandraWholeDeg, int raashi){
		nakshaIndex = chandraWholeDeg[0]/27;
		return nakshatras[nakshaIndex];
	}

	static double remainingDistance;
    private static int tithiSector;
    private static double tithiDeg;

	// raashi no. like Mesha is 1.
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

	// end time in hours
    public static double tithiEnd(int[] surMot, int[] chaMot, double remainingDistance) throws Exception {
//        return DegMinSec.degrees(new int[]{tithiSector * 12, 0, 0}) - tithiDeg;
		/* 
        System.out.printf("1 is %4.9f\n", DegMinSec.degrees(chaMot) * 60);
        System.out.printf("2 is %4.9f\n", DegMinSec.degrees(surMot) * 60);
        System.out.printf("3 is %4.9f\n", DegMinSec.toMinutes(remainingDistance));
		*/
		// endTime = RD/ (DMC - DMS) * 24
        return (remainingDistance/ (DegMinSec.degrees(chaMot) - DegMinSec.degrees(surMot) ))  * 24;
    }

}
