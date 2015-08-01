package de.intranda.digiverso.model.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpDownloadUtility {

	public static String downloadFile(String inUrl, String login, String password) throws IOException {
		String result = null;
		URL url = new URL(inUrl);
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		String userpass = login + ":" + password;
		String basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes());
		httpConn.setRequestProperty("Authorization", basicAuth);
		int responseCode = httpConn.getResponseCode();
		if (responseCode == HttpURLConnection.HTTP_OK) {
			InputStream inputStream = httpConn.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}
			br.close();
			inputStream.close();
			result = sb.toString();
		} else {
			System.out.println("No file to download. Server replied HTTP code: " + responseCode);
		}
		httpConn.disconnect();
		return result;
	}
}