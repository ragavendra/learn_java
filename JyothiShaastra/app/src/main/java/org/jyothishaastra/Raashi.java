package org.jyothishaastra;

import java.util.Calendar;

public class Raashi {
	static String rashis[] = { "Mesha", "Vrushabha", "Mithuna", "Karkataka", "Simha", "Kanya", "Thula", "Vrishika", "Mesha", "Kumbha", "Meena" };

	private static int rashiIndex;

	// public static double (int[] chandraNiraayana){
	// raashi starting from 0 for Mesha
	public static String byWholeDegrees(int[] chandraWholeDeg, int raashi){
		rashiIndex = chandraWholeDeg[0]/12;
		return rashis[rashiIndex];
	}
}
