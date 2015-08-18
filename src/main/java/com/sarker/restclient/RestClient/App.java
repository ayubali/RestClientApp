package com.sarker.restclient.RestClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;

import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 * Hello world!
 * 
 */
public class App {
	public static void main(String[] args) {
		try {

			// create HTTP Client
			HttpClient httpClient = HttpClientBuilder.create().build();

			// Create new getRequest with below mentioned URL
			HttpGet getRequest = new HttpGet(
					"http://localhost:8884/RESTfulCRUD/rest/todos");

			// Add additional header to getRequest which accepts application/xml
			// data
			getRequest.addHeader("accept", "application/json");

			// Execute your request and catch response
			HttpResponse response = httpClient.execute(getRequest);

			// Check for HTTP response code: 200 = success
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatusLine().getStatusCode());
			}

			// Get-Capture Complete application/xml body response
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(response.getEntity().getContent())));
			String output;
			System.out.println("============Output:============");

			// Simply iterate through XML response and show on console.
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

			System.out.println(".......................................");

			HttpPost postRequest = new HttpPost(
					"http://localhost:8884/RESTfulCRUD/rest/todos/post");

			StringEntity input = new StringEntity(
					"{\"id\":\"10\",\"summary\":\"Do something\",\"description\":\"Read complete http://www.vogella.com\"}");
			input.setContentType("application/json");
			postRequest.setEntity(input);

			response = httpClient.execute(postRequest);

			if (response.getStatusLine().getStatusCode() != 201) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatusLine().getStatusCode());
			}

			br = new BufferedReader(new InputStreamReader(
					(response.getEntity().getContent())));

			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static URI getBaseURI() {
		return UriBuilder.fromUri("http://localhost:8884/RESTfulCRUD").build();
	}
}
