package com.shortener.url;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Bitly {
	
	private static String URL_API 	= "http://api.bit.ly/v3/shorten?login=%s&apiKey=%s&uri=%s&format=txt";
	private static String username  = "o_7srd69blvj";
	private static String apikey 	= "R_5c41fd1874451e42108573031b483fa6";

	
	public static String urlShort(String url_shorten) throws IOException{
		StringBuilder  returnUrl = new StringBuilder();
		
		if(!url_shorten.contains("http://")){
			url_shorten = "http://"+url_shorten;
		}
		
		URL url = new URL(String.format(URL_API, new String[] {username, apikey, url_shorten}));
		URLConnection urlConnection = url.openConnection();

		BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
		
		String decodedString;
        while ((decodedString = in.readLine()) != null) {
        	returnUrl.append(decodedString);

        }
        in.close();
		return returnUrl.toString();
	}
}
