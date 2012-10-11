package com.shortener.url;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class TinyUrl {
	
	static String URL_API = "http://tinyurl.com/api-create.php?url=";
	
	public static String urlShort(String url_shorten) throws IOException{
		StringBuilder  returnUrl = new StringBuilder();

		if(!url_shorten.contains("http://")){
			url_shorten = "http://"+url_shorten;
		}
		
		URL url = new URL(URL_API.concat(url_shorten));
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
