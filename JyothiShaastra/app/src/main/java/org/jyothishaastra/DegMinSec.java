package org.jyothishaastra;
import java.util.Arrays;
import java.util.Calendar;

public class DegMinSec {

/* 
		// calc deg-min-sec to deg 
		dd = Math.signum(d) * (Math.abs(d) + (m / 60.0) + (s / 3600.0));

		// res is in "ddd.mmss" format
		jshell> decimal res = Math.signum(6) * (Math.abs(6) + (18 / 60.0) + (34 / 3600.0));
		$48 ==> 6.309444444444444

		// the res can be added to some other no, then do below to get d-m-s

		d = (int)dd;  // Truncate the decimals
		t1 = (dd - d) * 60;
		m = (int)t1;
		s = (t1 - m) * 60;
*/

	// res is in "ddd.mmss" format
	// get in degrees
	public static double degrees(int degree, int minutes, int seconds){
        int abs = Math.abs(degree);

        if(degree == 0)
            degree = 1;

		return Math.signum(degree) * (abs + (minutes / 60.0) + (seconds / 3600.0));
	}

	public static double degrees(int arr[]){
		return degrees(arr[0], arr[1], arr[2]);
	}

	public static double toMinutes(double degrees){
        int no = (int) degrees;
        double result = no * 60 + (degrees - no) * 60;
        return result;
	}

	// get in degrees, minutes and seconds, can be used as ar[0]-ar[1]-ar[2]
	public static int[] getGeoCoords(int degree, int minutes, int seconds) {

		double resDegrees = degrees(degree, minutes, seconds);
		return getGeoCoordsFromDegree(resDegrees);
	}

	public static int[] getGeoCoords(int arr[]) {
		return getGeoCoords(arr[0], arr[1], arr[2]);
	}

	public static int[] getGeoCoordsFromDegree(double resDegrees) {
		int d = (int)resDegrees;  // Truncate the decimals
		double t1 = (resDegrees - d) * 60;
		int m = (int)t1;
		double s = (t1 - m) * 60.0;
		int sec = (int) Math.round(s);
		if (sec == 60){
			m = m+1;
			sec =0;
			// System.out.println("In seconds minu loop");
		}

		if (m > 60) {
			d = d++;
			m = m - 60;
			System.out.println("In minutes minu loop");
		}

		int ar[] = { d, m, sec};

		return ar;
	}

	public static int[] add(int dms1[], int dms2[]) {
		double d1 = degrees(dms1);
		double d2 = degrees(dms2);

		double res = d1 + d2;
		// System.out.println("Add is " + res);	

		// if more than 360 subtract it from 360
		if(res > 360)
			return minus(getGeoCoordsFromDegree(res), getGeoCoordsFromDegree(degrees(360, 0, 0)));

		return getGeoCoordsFromDegree(res);
	}

	public static int[] minus(int dms1[], int dms2[]) {
		double d1 = degrees(dms1);
		double d2 = degrees(dms2);

		// swap
		if(d1 > d2){
			double d = d2;
			d2 = d1;
			d1 = d;
		}

		double res = d2 - d1;
		// System.out.println("Sub is " + res);	

		return getGeoCoordsFromDegree(res);
	}

	public static int absGeo(int raashi, int[] grahaGeo) throws Exception {
		return (grahaGeo[0] + 30 * ( raashi - 1));
	}

	/* 
	   javac jyotishaastra/Ayanamsha.java
	   java jyotishaastra/DegMinSec.java
	   */
	public static void main(String ags[]) throws Exception {

		// India Ephemersis doesnt have Ayanamsha
		Calendar date = Calendar.getInstance();
		// date.set(2009, 7, 15); // for 15 July 2009
		double ayanamsha = Ayanamsha.ayanamsha(date);
		System.out.printf("Aya is %s degrees\n", ayanamsha);	

		// Mesha is 1
		int surRaashi = 7;
		int chaRaashi = 8;
		int swissEphermesisMoon[] = { 4, 7, 00 };
		int ar1[] = { 4, 7, 0 }; // of next day's which is in Mesha raashi 9
		int ar2[] = { 16, 1, 0 }; // of next day's which is in Mesha raashi 9
		int swissEphermesisSun[] = { 28, 50, 42 };
		int ar3[] = { 28, 50, 42 };
		int ar4[] = { 29, 50, 25 };

		int chandraAbs[] = swissEphermesisMoon;
		chandraAbs[0] = DegMinSec.absGeo(chaRaashi, swissEphermesisMoon);

		int sooryaAbs[] = swissEphermesisSun;
		sooryaAbs[0] = DegMinSec.absGeo(surRaashi, swissEphermesisSun);
        System.out.printf("Abs geo of Surya is %d & Chandra is %d\n", sooryaAbs[0], chandraAbs[0]);

		System.out.printf("Tithi is %s and remaining distance is %4.9f.\n", Tithi.tithi(chandraAbs, sooryaAbs), Tithi.remainingDistance);

		/*uncomment when Chandra has moved to next Raashi 
		  ar1[0] = DegMinSec.absGeo(chaRaashi, ar1);
		  chaRaashi = chaRaashi + 1; // since it is moving to Mesha 9 raashi
		  ar2[0] = DegMinSec.absGeo(chaRaashi, ar2);
		*/

        int chaMot[] = DegMinSec.minus(ar2, ar1);
		// System.out.printf("Daily motion of Chandra is %s\n", Arrays.toString(chaMot));

        int surMot[] = DegMinSec.minus(ar4, ar3);
		// System.out.printf("Daily motion of Soorya is %s\n", Arrays.toString(surMot));

        // Time taken to cover remainingDistance
        System.out.printf("Tithi ends at %s from 5:30 IST or minus from 7am in PST?\n", Arrays.toString(DegMinSec.getGeoCoordsFromDegree(Tithi.end(surMot, chaMot, Tithi.remainingDistance))));

		System.out.printf("Karana is %s and remaining distance is %4.9f.\n", Karana.karana(chandraAbs, sooryaAbs), Karana.remainingDistance);
        // System.out.printf("Karana ends at %s from 5:30 IST or minus from 7am in PST?\n", Arrays.toString(DegMinSec.getGeoCoordsFromDegree(Karana.tithiEnd(surMot, chaMot, Tithi.remainingDistance))));
	
		int surNir[] = DegMinSec.getGeoCoordsFromDegree(Ayanamsha.nirayaana(ayanamsha, sooryaAbs));
		// System.out.printf("Soorya niraayana on %s from Swiss is %s\n", date.getTime(), Arrays.toString(surNir));	

		int[] chaNir = DegMinSec.getGeoCoordsFromDegree(Ayanamsha.nirayaana(ayanamsha, chandraAbs));
		// System.out.printf("Chandra niraayana on %s from Swiss is %s\n", date.getTime(), Arrays.toString(chaNir));	

		System.out.printf("Raashi is %s and remaining distance is %4.9f.\n", Raashi.raashi(chandraAbs), Raashi.remainingDistance);
        // System.out.printf("Raashi ends at %s from 5:30 IST or minus from 7am in PST?\n", Arrays.toString(DegMinSec.getGeoCoordsFromDegree(Raashi.tithiEnd(surMot, chaMot, Tithi.remainingDistance))));
		System.out.printf("Nakshatra is %s and remaining distance is %4.9f.\n", Nakshatra.nakshatra(chandraAbs), Nakshatra.remainingDistance);
        System.out.printf("Nakshatra ends at %s from 5:30 IST or minus from 7am in PST?\n", Arrays.toString(DegMinSec.getGeoCoordsFromDegree(Nakshatra.end(chaMot, Tithi.remainingDistance))));
	}

	public static void mainThisClass(String ags[]) throws Exception {
		/* 
		   if (ags.length != 3) {
		   System.err.println("Usage:  java DegMinSec deg min sec");
		   System.exit(1);
		   }
		   */

		// int ar1[] = { Integer.parseInt(ags[0]), Integer.parseInt(ags[1]), Integer.parseInt(ags[2])};
		int ar1[] = { 377, 41, 0 };
		System.out.printf("Deg is %s\n", DegMinSec.degrees(ar1)); 
		System.out.printf("Get Coords as arr is %s\n", Arrays.toString(DegMinSec.getGeoCoords(ar1))); 
		// int ar2[] = { 284, 40, 20 };
		// int ar2[] = { 344, 33, 15 };
		// int ar2[] = { 164, 10, 9 };
		// int ar2[] = { 112, 39, 28 };
		int ar2[] = { 265, 1, 32 };
		System.out.printf("Deg of ar2 is %s\n", DegMinSec.degrees(ar2)); 
		System.out.printf("Add arr is %s\n", Arrays.toString(DegMinSec.add(ar1, ar2))); 
		System.out.printf("Minus arr is %s\n", Arrays.toString(DegMinSec.minus(ar1, ar2))); 
		/* 
		// int ar2[] = { 344, 33, 15 };
		[ntwk]$ java DegMinSec.java 383 52 33
		Deg is 383.87583333333333
		Get Coords as arr is [383, 52, 32]
		Add arr is [368, 25, 47]
		Minus arr is [39, 19, 17]

		// int ar2[] = { 344, 33, 15 };
		[ntwk]$ java DegMinSec.java 479 15 13
		Deg is 479.2536111111111
		Get Coords as arr is [479, 15, 13]
		Add arr is [283, 25, 21]
		Minus arr is [315, 5, 3]
		*/
	}
}
