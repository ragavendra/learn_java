package ntwk;
import java.net.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.function.*;
// import java.util.Date;
import java.util.Calendar;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
// import java.lang.ProcessBuilder;
import org.json.*;

// Usage - java -cp lib/json-20250517.jar Sunrise.java
public class Sunrise {
	private static List<String> getAdditionalArgs() {
		List<String> list = new ArrayList<>();
		for( Map.Entry<String,String> e : System.getenv().entrySet() ) {
			list.add( e.getKey() + " = " + e.getValue() );
		}
		return list;
	}

	static boolean PlayFile() throws IOException, InterruptedException, ExecutionException, TimeoutException {
		boolean isWindows = System.getProperty("os.name")
			.toLowerCase().startsWith("windows");

		ProcessBuilder builder = new ProcessBuilder();

		if (isWindows) {
			builder.command("cmd.exe", "/c", "dir");
		} else {
			builder.command("sh", "-c", "ls");
		}
		builder.directory(new File(System.getProperty("user.home")));
		Process process = builder.start();
		ExecutorService executorService = Executors.newCachedThreadPool();
		StreamGobbler streamGobbler = 
			new StreamGobbler(process.getInputStream(), System.out::println);
		Future<?> future = executorService.submit(streamGobbler);

		int exitCode = process.waitFor();
		future.get(10, TimeUnit.SECONDS);
		if(exitCode == 0){
			return true;
		}

		return false;
		// assertDoesNotThrow(() -> future.get(10, TimeUnit.SECONDS));
		// assertEquals(0, exitCode); 
	}

	public static void main(String ags[]) throws Exception {
		/* 
		   if (ags.length != 2) {
		   System.err.println("Usage:  java Reverse "
		   + "http://<location of your servlet/script>"
		   + " string_to_reverse");
		   System.exit(1);
		   }

		   String strTorev = URLEncoder.encode(ags[1], "UTF-8");
		   */
		String jsonR = GetSunrise();
		boolean resp = CheckIfItsTime(jsonR);
		if(resp)
			PlayFile();
	}

	public static boolean CheckIfItsTime(String jsonResp) throws Exception {
		JSONObject json = new JSONObject(jsonResp);
		// JSONObject json = new JSONObject("{\"tzid\":\"America/Vancouver\",\"results\":{\"sunrise\":\"6:57:36 AM\",\"solar_noon\":\"1:04:11 PM\",\"day_length\":\"12:13:10\",\"astronomical_twilight_end\":\"8:56:57 PM\",\"astronomical_twilight_begin\":\"5:11:25 AM\",\"sunset\":\"7:10:46 PM\",\"civil_twilight_end\":\"7:40:52 PM\",\"nautical_twilight_end\":\"8:18:16 PM\",\"civil_twilight_begin\":\"6:27:30 AM\",\"nautical_twilight_begin\":\"5:50:06 AM\"},\"status\":\"OK\"}");

		var res = json.getJSONObject("results");
		// res.getString("sunrise");
		System.out.printf("Op is %s\n", res.getString("sunrise"));

		// java.util.Date date = new java.util.Date(res.getString("sunrise"));
		String[] splTime = res.getString("sunrise").split(" ")[0].split(":");
		// System.out.printf("Split time is %s\n", splTime[1]);

		// public Date(int year, int month, int date, int hrs, int min) {
		var currDt = Calendar.getInstance();
		java.util.Date date = new java.util.Date(currDt.get(Calendar.YEAR), currDt.get(Calendar.MONTH),currDt.get(Calendar.DATE), Integer.parseInt(splTime[0]),Integer.parseInt(splTime[1]),Integer.parseInt(splTime[2]));
		// System.out.printf("Parsed date is %s\n", date.toString());

		if(date.getHours() == currDt.get(Calendar.HOUR_OF_DAY)){
			if(date.getMinutes() == currDt.get(Calendar.MINUTE)){
				System.out.printf("Play file %s\n", res.toString());
				return true;
			}
			/* 
			   else
			   System.out.printf("Minutes don't match.\n", res.toString());
			   */
		}
		return false;
		/* 
		   else
		   System.out.printf("Hours don't match %s.\n", date.getHours());
		   */


		}

	public static String GetSunrise() throws Exception {

		// URL url = new URL("https://oracle.com");
		URL url = new URL("https://api.sunrise-sunset.org/json?lat=49.2135640568696&lng=-122.92188251169758&date=today&tzid=America/Vancouver");
		// date=\"@\"\"-\"?[0-9]+");
		// URL url = new URL(ags[0]);
		HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
		urlConn.setDoOutput(true);
		urlConn.setRequestMethod("GET");

		// headers
		// urlConn.setRequestProperty("Content-Type", "application/json");

		// timeouts
		urlConn.setConnectTimeout(3000);

		// data to be avail for reading
		urlConn.setReadTimeout(3000);

		// get resp status and data
		int status = urlConn.getResponseCode();
		System.out.printf("Status is %d\n", status);

		InputStreamReader inStrRdr;

		if(status > 299) {
			inStrRdr = new InputStreamReader(urlConn.getErrorStream());
		}
		else {
			inStrRdr = new InputStreamReader(urlConn.getInputStream());
		}

		BufferedReader in = new BufferedReader(inStrRdr);
		StringBuffer str = new StringBuffer();
		String strDec;

		while((strDec = in.readLine()) != null){
			// System.out.println(strDec);
			str.append(strDec);
		}

		/* 
		   if(str.toString().charAt(0) == '['){
		   JSONArray rootArr = new JSONArray(str.toString());
		// System.out.printf("Op is %s", rootArr);
		   }
		   else{
		   JSONObject root = new JSONObject(str.toString());
		   System.out.printf("Op is %s", root);
		   }
		   */

		in.close();

		// after the resp is got, prepare for the next
		urlConn.disconnect();

		return str.toString();

		/* 
		// handling redirects
		urlConn.setInstanceFollowRedirects(false);

		// disable redirects for all conns
		HttpURLConnection.setFollowRedirects(false);

		URLConnection urlConn = url.openConnection();
		urlConn.setDoOutput(true);

		OutputStreamWriter outStrWr = new OutputStreamWriter(urlConn.getOutputStream());
		outStrWr.write("string=" + strTorev);
		outStrWr.close();

		BufferedReader in = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
		String strDec;

		while((strDec = in.readLine()) != null){
		System.out.println(strDec);
		}

		in.close();
		*/
	}
	}

	class StreamGobbler implements Runnable {
		private InputStream inputStream;
		private Consumer<String> consumer;

		public StreamGobbler(InputStream inputStream, Consumer<String> consumer) {
			this.inputStream = inputStream;
			this.consumer = consumer;
		}

		@Override
		public void run() {
			new BufferedReader(new InputStreamReader(inputStream)).lines()
				.forEach(consumer);
		}
	}


	class ParameterStringBuilder {
		public static String getParamsString(Map<String, String> params) 
				throws UnsupportedEncodingException{
				StringBuilder result = new StringBuilder();

				for (Map.Entry<String, String> entry : params.entrySet()) {
					result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
					result.append("=");
					result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
					result.append("&");
				}

				String resultString = result.toString();
				return resultString.length() > 0
					? resultString.substring(0, resultString.length() - 1)
					: resultString;
		}
	}
