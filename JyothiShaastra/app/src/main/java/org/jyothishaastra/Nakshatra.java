package org.jyothishaastra;

import java.util.Calendar;

public class Nakshatra {
	static String nakshatras[] = { "Ashwini", "Bharani", "Krithika", "Rohini", "Mrigashira", "Aradra", "Punarvasu", "Pushya", "Ashlesha", "Magha", "Pubha", "Uttara", "Hashta", "Chitta", "Swathi", "Vishakha", "Anuradha", "Jyeshta", "Moola", "Purvashaada", "Uttarashaada", "Shravana", "Dhanishta", "Shathabisha", "Poorvabadha", "Uttarabadha", "Revathi" };

	private static int nakshaIndex;

	// public static double (int[] chandraNiraayana){
	// raashi starting from 0 for Mesha
	public static String byWholeDegrees(int[] chandraWholeDeg, int raashi){
		nakshaIndex = chandraWholeDeg[0]/27;
		return nakshatras[nakshaIndex];
	}
}
