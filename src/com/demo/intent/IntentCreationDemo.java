package com.demo.intent;
import java.io.BufferedReader;
import java.io.IOException;

//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

public class IntentCreationDemo
{
	public static void main(String[] args) throws IOException 
	{
		
		IntentCreationDemo intent=new IntentCreationDemo();
		//intent.getIntent();
		intent.createIntent();
		//intent.createEntity();
		//intent.updateIntent();
	}
	
	public String getIntent() throws IOException
	{
		String output="";
		URL url = new URL("https://api.dialogflow.com/v1/intents/1c0b1951-8f14-478e-9844-083a4fdecc8d?v=20150910");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Authorization","Bearer f8b2c6b1109543548e550bb6e2945c39");//AE gmail account
		//conn.setRequestProperty("Authorization","Bearer 7008d7c378d94e60bd0ae6907aca33d7");//my gmaill acnt
		conn.setRequestProperty("Content-Type", "application/json");
		
		if (conn.getResponseCode() == HttpURLConnection.HTTP_CREATED) {
			throw new RuntimeException("Failed : HTTP error code : "
				+ conn.getResponseCode());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));

		String json="";
		
		while ((output = br.readLine()) != null) {
			//System.out.println("Output from pizza webservice: "+output);
			json=json+output;
		}
		System.out.println(json);
		
		ObjectMapper mapper1 = new ObjectMapper();
	    JsonNode actualObj = mapper1.readTree(json);
	    String intentName = actualObj.get("name").toString();
	    System.out.println("Intent name : "+intentName);
	    /*JsonNode actualObj1= mapper1.readTree(jsonNode1);
	    String parameter=actualObj1.get("parameters").toString();
	    System.out.println(parameter);*/
		return json;
	
	}
	
	public String createIntent() throws IOException
	{
		MediaType mediaType = MediaType.parse("application/json");
		OkHttpClient client = new OkHttpClient();
		RequestBody body = RequestBody.create(mediaType, "{\r\n  \"contexts\": [\r\n    \"shop\"\r\n  ],\r\n  \"events\": [],\r\n  \"fallbackIntent\": false,\r\n  \"name\": \"add-to-list\",\r\n  \"priority\": 500000,\r\n  \"responses\": [\r\n    {\r\n      \"action\": \"add.list\",\r\n      \"affectedContexts\": [\r\n        {\r\n          \"lifespan\": 5,\r\n          \"name\": \"shop\",\r\n          \"parameters\": {}\r\n        },\r\n        {\r\n          \"lifespan\": 5,\r\n          \"name\": \"chosen-fruit\",\r\n          \"parameters\": {}\r\n        }\r\n      ],\r\n      \"defaultResponsePlatforms\": {\r\n        \"google\": true\r\n      },\r\n      \"messages\": [\r\n        {\r\n          \"platform\": \"google\",\r\n          \"textToSpeech\": \"Okay. How many $fruit?\",\r\n          \"type\": \"simple_response\"\r\n        },\r\n        {\r\n          \"speech\": \"Okay how many $fruit?\",\r\n          \"type\": 0\r\n        }\r\n      ],\r\n      \"parameters\": [\r\n        {\r\n          \"dataType\": \"@fruit\",\r\n          \"isList\": true,\r\n          \"name\": \"fruit\",\r\n          \"prompts\": [\r\n            \"I didn't get that. What fruit did you want?\"\r\n          ],\r\n          \"required\": true,\r\n          \"value\": \"$fruit\"\r\n        }\r\n      ],\r\n      \"resetContexts\": false\r\n    }\r\n  ],\r\n  \"templates\": [\r\n    \"@fruit:fruit \",\r\n    \"Add @fruit:fruit \",\r\n    \"I need @fruit:fruit \"\r\n  ],\r\n  \"userSays\": [\r\n    {\r\n      \"count\": 0,\r\n      \"data\": [\r\n        {\r\n          \"alias\": \"fruit\",\r\n          \"meta\": \"@fruit\",\r\n          \"text\": \"oranges\",\r\n          \"userDefined\": true\r\n        }\r\n      ]\r\n    },\r\n    {\r\n      \"count\": 0,\r\n      \"data\": [\r\n        {\r\n          \"text\": \"Add \"\r\n        },\r\n        {\r\n          \"alias\": \"fruit\",\r\n          \"meta\": \"@fruit\",\r\n          \"text\": \"bananas\",\r\n          \"userDefined\": true\r\n        }\r\n      ]\r\n    },\r\n    {\r\n      \"count\": 0,\r\n      \"data\": [\r\n        {\r\n          \"text\": \"I need \"\r\n        },\r\n        {\r\n          \"alias\": \"fruit\",\r\n          \"meta\": \"@fruit\",\r\n          \"text\": \"apples\",\r\n          \"userDefined\": true\r\n        }\r\n      ]\r\n    }\r\n  ],\r\n  \"webhookForSlotFilling\": false,\r\n  \"webhookUsed\": false\r\n}");
		
		Request request = new Request.Builder()
		  .url("https://api.dialogflow.com/v1/intents?v=20150910")
		  .post(body)
		  .addHeader("content-type", "application/json")
		   .addHeader("authorization", "Bearer f8b2c6b1109543548e550bb6e2945c39")
		  //.addHeader("authorization", "Bearer 7008d7c378d94e60bd0ae6907aca33d7")
		  .addHeader("cache-control", "no-cache")
		  .addHeader("postman-token", "b3b9f9fb-9f2c-214e-13d6-beffee22a2ab")
		  .build();
		
		Response response = client.newCall(request).execute();
		
		String intentJson=response.body().string();
		System.out.println("\nResponse from dialogflow : \n"+intentJson);
		System.out.println("Intent created successfully");
		return intentJson;
		
	}
	
	public String updateIntent() throws IOException
	{
		OkHttpClient client = new OkHttpClient();

		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, "{\r\n  \"contexts\": [\r\n    \"shop\"\r\n  ],\r\n  \"events\": [],\r\n  \"fallbackIntent\": false,\r\n  \"name\": \"add-to-list\",\r\n  \"priority\": 500000,\r\n  \"responses\": [\r\n    {\r\n      \"action\": \"add.list\",\r\n      \"affectedContexts\": [\r\n        {\r\n          \"lifespan\": 5,\r\n          \"name\": \"shop\",\r\n          \"parameters\": {}\r\n        },\r\n        {\r\n          \"lifespan\": 5,\r\n          \"name\": \"chosen-fruit\",\r\n          \"parameters\": {}\r\n        },\r\n        {\r\n          \"lifespan\": 5,\r\n          \"name\": \"fruitcolor\",\r\n          \"parameters\": {}\r\n        }\r\n      ],\r\n      \"defaultResponsePlatforms\": {\r\n        \"google\": true\r\n      },\r\n      \"messages\": [\r\n        {\r\n          \"platform\": \"google\",\r\n          \"textToSpeech\": \"Okay. How many $fruit?\",\r\n          \"type\": \"simple_response\"\r\n        },\r\n        {\r\n          \"speech\": \"Okay how many $fruit?\",\r\n          \"type\": 0\r\n        }\r\n      ],\r\n      \"parameters\": [\r\n        {\r\n          \"dataType\": \"@fruit\",\r\n          \"isList\": true,\r\n          \"name\": \"fruit\",\r\n          \"prompts\": [\r\n            \"I didn't get that. What fruit did you want?\"\r\n          ],\r\n          \"required\": true,\r\n          \"value\": \"$fruit\"\r\n        }\r\n      ],\r\n      \"resetContexts\": false\r\n    }\r\n  ],\r\n  \"templates\": [\r\n    \"@fruit:fruit \",\r\n    \"Add @fruit:fruit \",\r\n    \"I need @fruit:fruit \"\r\n  ],\r\n  \"userSays\": [\r\n    {\r\n      \"count\": 0,\r\n      \"data\": [\r\n        {\r\n          \"alias\": \"fruit\",\r\n          \"meta\": \"@fruit\",\r\n          \"text\": \"oranges\",\r\n          \"userDefined\": true\r\n        }\r\n      ]\r\n    },\r\n    {\r\n      \"count\": 0,\r\n      \"data\": [\r\n        {\r\n          \"text\": \"Add \"\r\n        },\r\n        {\r\n          \"alias\": \"fruit\",\r\n          \"meta\": \"@fruit\",\r\n          \"text\": \"bananas\",\r\n          \"userDefined\": true\r\n        }\r\n      ]\r\n    },\r\n    {\r\n      \"count\": 0,\r\n      \"data\": [\r\n        {\r\n          \"text\": \"I need \"\r\n        },\r\n        {\r\n          \"alias\": \"fruit\",\r\n          \"meta\": \"@fruit\",\r\n          \"text\": \"apples\",\r\n          \"userDefined\": true\r\n        }\r\n      ]\r\n    }\r\n  ],\r\n  \"webhookForSlotFilling\": false,\r\n  \"webhookUsed\": false\r\n}");
		Request request = new Request.Builder()
		  .url("https://api.dialogflow.com/v1/intents/1c0b1951-8f14-478e-9844-083a4fdecc8d?v=20150910")
		  .put(body)
		  .addHeader("content-type", "application/json")
		  .addHeader("authorization", "Bearer f8b2c6b1109543548e550bb6e2945c39")
		  .addHeader("cache-control", "no-cache")
		  .addHeader("postman-token", "af6fe36d-4626-acaa-8f28-95bcc409aab2")
		  .build();

		Response response = client.newCall(request).execute();
		String updatedIntent=response.body().string();
		System.out.println("\nupdated intent :\n"+updatedIntent);
		
		return updatedIntent;
	}
	
	public String createEntity() throws IOException
	{
			String entityJson="";
		
		try
		{
			OkHttpClient client = new OkHttpClient();

			MediaType mediaType = MediaType.parse("application/json");
			RequestBody body = RequestBody.create(mediaType, "{\r\n  \"entries\": [{\r\n  \"synonyms\": [\"JDK\", \"java 1.8\"],\r\n      \"value\": \"java\"\r\n    },\r\n    { \r\n     \"synonyms\": [\"MS-OFFICE\", \"micrpsoft office\"],\r\n      \"value\": \"MS office\"\r\n    }\r\n  ],\r\n  \"name\": \"Software\"\r\n}");

			Request request = new Request.Builder()
			.url("https://api.dialogflow.com/v1/entities?v=20150910")
			.post(body)
			.addHeader("content-type", "application/json")
			.addHeader("authorization", "Bearer f8b2c6b1109543548e550bb6e2945c39")
			//.addHeader("authorization", "Bearer 7008d7c378d94e60bd0ae6907aca33d7")
			.addHeader("cache-control", "no-cache")
			.addHeader("postman-token", "288ee961-004c-a70f-a81e-ac262c663c2f")
			.build();

			Response response = client.newCall(request).execute();
			entityJson=response.body().string();
			System.out.println("\nResponse from dialogflow :\n"+entityJson);
			System.out.println("Entity created successfully");
		}
		catch(Exception e)
		{
				e.printStackTrace();
		}
		
		return entityJson;
		
	}
	
}
