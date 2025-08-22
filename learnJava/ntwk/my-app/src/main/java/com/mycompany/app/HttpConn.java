package com.mycompany.app;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.io.*;
import org.json.*;

public class HttpConn{
	private static List<String> getAdditionalArgs() {
    List<String> list = new ArrayList<>();
    for( Map.Entry<String,String> e : System.getenv().entrySet() ) {
      list.add( e.getKey() + " = " + e.getValue() );
    }
    return list;
  }

 public static void main(String ags[]) throws Exception{
		if (ags.length != 2) {
			System.err.println("Usage:  java Reverse "
					+ "http://<location of your servlet/script>"
					+ " string_to_reverse");
			System.exit(1);
		}

		String strTorev = URLEncoder.encode(ags[1], "UTF-8");

		// URL url = new URL("https://oracle.com");
		URL url = new URL(ags[0]);
		HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
		urlConn.setDoOutput(true);
		urlConn.setRequestMethod("GET");

		// headers
		// urlConn.setRequestProperty("Content-Type", "application/json");

		// timeouts
		urlConn.setConnectTimeout(3000);

		// data to be avail for reading
		urlConn.setReadTimeout(3000);

		// req will become post if posting data
		Map<String, String> map = new HashMap();
		map.put("param1", "val");

		for ( String s : getAdditionalArgs() ) {
			System.out.println( "Env: " + s );
		}

		// or make json, takes any obj or types
		JSONObject json = new JSONObject("{\"id\": 21,\"title\":\"some title\",\"body\":\"some body\"}");
		System.out.printf("Json sample data is %s\n", json.toString());

		// java.net.ProtocolException - cannot write output after reading input
		// int status = urlConn.getResponseCode();
		/* 
		// more or less the call is made here
		// add req params
		DataOutputStream dataOpStre = new DataOutputStream(urlConn.getOutputStream());
		dataOpStre.writeBytes(ParameterStringBuilder.getParamsString(map));
		dataOpStre.flush();
		dataOpStre.close();
		*/

		// make it a post call
		OutputStreamWriter outStrWr = new OutputStreamWriter(urlConn.getOutputStream());
		outStrWr.write(json.toString());
		outStrWr.close();

		// cookie handling
		String cookHdr = urlConn.getHeaderField("Set-Cookie");
		if(cookHdr != null){
			List<HttpCookie> cookies = HttpCookie.parse(cookHdr);

			Optional<HttpCookie> userCookie = cookies.stream().findAny().filter(cook -> cook.getName().equals("username"));


			CookieManager cookieMgr = new CookieManager();
			cookies.forEach(coo -> cookieMgr.getCookieStore().add(null, coo));

			if(userCookie != null) {
				cookieMgr.getCookieStore().add(null, new HttpCookie("username", "john"));
			}
		}

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

		if(str.toString().charAt(0) == '['){
			JSONArray rootArr = new JSONArray(str.toString());
			System.out.printf("Op is %s", rootArr);
		}
		else{
			JSONObject root = new JSONObject(str.toString());
			System.out.printf("Op is %s", root);
		}

		in.close();

		// after the resp is got, prepare for the next
		urlConn.disconnect();
		urlConn = (HttpURLConnection) url.openConnection();

		// urlConn.setRequestProperty("Cookie", StringUtils.join(cookieMgr.getCookieStore().getCookies(), ";"));
		// cont rem of doing the http method

		// handling redirects
		urlConn.setInstanceFollowRedirects(false);

		// disable redirects for all conns
		HttpURLConnection.setFollowRedirects(false);
		/* 

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
