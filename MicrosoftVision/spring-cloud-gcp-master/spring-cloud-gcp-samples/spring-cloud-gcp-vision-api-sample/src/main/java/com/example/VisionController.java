
package com.example;

import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.*;
import com.google.protobuf.ByteString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import org.apache.log4j.Logger;

@SuppressWarnings("unused")
@RestController
public class VisionController {

	final String ROOT_URI = "http://localhost:8080";
	Logger logger = Logger.getLogger(VisionController.class);
	JSONObject json = new JSONObject();
	JSONArray jsonarray = new JSONArray();
	String jsonString = new String();

	String imageToAnalyze ="https://upload.wikimedia.org/wikipedia/commons/1/12/Broadway_and_Times_Square_by_night.jpg";
	//String imageToAnalyze="C:\\Users\\admin\\Pictures\\error1.jpg";
	String subscriptionKey = "9ac2985b0a87450f8500f61e89baae13";
	String uriBase = "https://westcentralus.api.cognitive.microsoft.com/vision/v2.0/analyze";

	@SuppressWarnings("unchecked")
	@RequestMapping("/vision2")
	public MSVision vision(String imageToAnalyze) throws Exception {
		imageToAnalyze ="https://upload.wikimedia.org/wikipedia/commons/1/12/Broadway_and_Times_Square_by_night.jpg";
		//imageToAnalyze="C:\\Users\\admin\\Pictures\\error1.jpg";
		// public String uploadImage(String imageUrl) throws Exception {
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();

		// try {
		URIBuilder builder = new URIBuilder(uriBase);

		// Request parameters. All of them are optional.
		builder.setParameter("visualFeatures", "Categories,Description,Color");
		builder.setParameter("language", "en");

		// Prepare the URI for the REST API call.
		URI uri = builder.build();
		HttpPost request = new HttpPost(uri);
		
		// Request headers.
		request.setHeader("Content-Type", "application/octet-stream"); // For Local File System Image
		//request.setHeader("Content-Type", "application/json");
		request.setHeader("Ocp-Apim-Subscription-Key", subscriptionKey);

		// Request body.
		File file = new File("C:\\Users\\admin\\Pictures\\error1.jpg");
		FileEntity reqEntity = new FileEntity(file);
		request.setEntity(reqEntity);
		
//		StringEntity requestEntity = new StringEntity("{\"url\":\"" + imageToAnalyze + "\"}");
//		request.setEntity(requestEntity);

		// Make the REST API call and get the response entity.
		HttpResponse response = httpClient.execute(request);
		HttpEntity entity = response.getEntity();

		// if (entity != null) {
		if (entity != null) {
			// Format and display the JSON response.
			//String jsonString = EntityUtils.toString(entity);
			jsonString = EntityUtils.toString(entity);
			//json = new JSONObject(jsonString);
			System.out.println("REST Response:\n");
			//System.out.println(json);
			System.out.println("JsonString " + jsonString);
			// op = json.toString();
		}
		logger.info("EntityResponse Displayed");
		System.out.println(jsonString);
		// } catch (Exception e) {
		// Display error message.
		// System.out.println("Exception is "+e.getMessage());
		// }
		
		//return new ComputerVision(1,json,imageToAnalyze);
		return new MSVision(91,imageToAnalyze, jsonString);

	}

	public MSVision vision() {
		return new MSVision(9, imageToAnalyze, jsonString);
	}

}
