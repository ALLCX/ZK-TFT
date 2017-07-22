package com.sls.icas;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpClientUtils {

	/** 
	 * post with form
	 */
	public void postForm(String URL, List<NameValuePair> formparams, boolean NeedResponse) {

		String returnstring = "";
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(URL);

		try {
			UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
			httppost.setEntity(uefEntity);
			CloseableHttpResponse response = httpclient.execute(httppost);

			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				try {
					HttpEntity entity = response.getEntity();
					
					returnstring = EntityUtils.toString(entity, "UTF-8");

					if (NeedResponse){
						System.out.println("Return String from backend is: " + returnstring);
					}
				} finally {
					response.close();
				}
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}