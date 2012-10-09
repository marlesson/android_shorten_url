package com.shortener.url;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Migreme {
	
	static String URL_API = "http://migre.me/api.txt?url=";
	
	public static String urlShort(String url_shorten) throws IOException{
		String returnUrl = "";
		URL url = new URL(URL_API.concat(url_shorten));
		URLConnection urlConnection = url.openConnection();

		BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
		
		String decodedString;
        while ((decodedString = in.readLine()) != null) {
        	returnUrl.concat(decodedString);

        }
        in.close();
		return returnUrl;
	}
	   


}
