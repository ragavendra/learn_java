import java.util.Arrays;

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
		return Math.signum(degree) * (Math.abs(degree) + (minutes / 60.0) + (seconds / 3600.0));
	}

	// get in degrees, minutes and seconds, can be used as ar[0]-ar[1]-ar[2]
	public static Object[] getGeoCoords(int degree, int minutes, int seconds) {

		double resDegrees = degrees(degree, minutes, seconds);
		return getGeoCoordsFromDegree(resDegrees);
	}

	public static Object[] getGeoCoordsFromDegree(double resDegrees) {
		int d = (int)resDegrees;  // Truncate the decimals
		double t1 = (resDegrees - d) * 60;
		int m = (int)t1;
		double s = (t1 - m) * 60.0;

		Object ar[] = { d, m, (int) s};

		return ar;
	}

	public static String getGeoCoordsAsStr(int degree, int minutes, int seconds) {
		Object arr[] = getGeoCoords(degree, minutes, seconds);

		return String.format("%s deg - %s min - %s sec", arr[0], arr[1], arr[2]); 
	}

	public static Object[] add(Object dms1[], Object dms2[]) {
		double d1 = degrees(Integer.parseInt(dms1[0].toString()), Integer.parseInt(dms1[1].toString()), Integer.parseInt(dms1[2].toString()));
		double d2 = degrees(Integer.parseInt(dms2[0].toString()), Integer.parseInt(dms2[1].toString()), Integer.parseInt(dms2[2].toString()));

		double res = d1 + d2;

		// if more than 360 subtract it from 360
		if(res > 360)
			return minus(getGeoCoordsFromDegree(res), getGeoCoordsFromDegree(degrees(360, 0, 0)));

		return getGeoCoordsFromDegree(res);
	}

	public static Object[] minus(Object dms1[], Object dms2[]) {
		double d1 = degrees(Integer.parseInt(dms1[0].toString()), Integer.parseInt(dms1[1].toString()), Integer.parseInt(dms1[2].toString()));
		double d2 = degrees(Integer.parseInt(dms2[0].toString()), Integer.parseInt(dms2[1].toString()), Integer.parseInt(dms2[2].toString()));
		// swap
		if(d1 > d2){
			double d = d2;
			d2 = d1;
			d1 = d;
		}

		double res = d2 - d1;
		return getGeoCoordsFromDegree(res);
	}

	public static void main(String ags[]) throws Exception {
		if (ags.length != 3) {
			System.err.println("Usage:  java DegMinSec deg min sec");
			System.exit(1);
		}

		System.out.printf("Deg is %s\n", DegMinSec.degrees(Integer.parseInt(ags[0].toString()), Integer.parseInt(ags[1].toString()), Integer.parseInt(ags[2].toString()))); 
		System.out.printf("Get Coords in Str is %s \n", DegMinSec.getGeoCoordsAsStr(Integer.parseInt(ags[0].toString()), Integer.parseInt(ags[1].toString()), Integer.parseInt(ags[2].toString()))); 
		System.out.printf("Get Coords as obj arr is %s\n", Arrays.toString(DegMinSec.getGeoCoords(Integer.parseInt(ags[0].toString()), Integer.parseInt(ags[1].toString()), Integer.parseInt(ags[2].toString())))); 
		Object ar[] = { 284, 40, 20 };
		System.out.printf("Add arr is %s\n", Arrays.toString(DegMinSec.add(ags, ar))); 
		System.out.printf("Minus arr is %s\n", Arrays.toString(DegMinSec.minus(ags, ar))); 

	}
}
