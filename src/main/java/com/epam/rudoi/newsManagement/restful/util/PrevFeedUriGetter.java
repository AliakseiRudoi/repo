package com.epam.rudoi.newsManagement.restful.util;

import java.io.IOException;

import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.request.OAuthBearerClientRequest;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthResourceResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;

public class PrevFeedUriGetter {

	public static OAuthResourceResponse getPrevFeedJson(String prevFeedUri, OAuthClient oAuthClient) {
		
		  OAuthClientRequest bearerClientRequest;
		  OAuthResourceResponse resourceResponsePrevFeedUri = null;
		try {
			bearerClientRequest = new OAuthBearerClientRequest(prevFeedUri).buildQueryMessage();
		       resourceResponsePrevFeedUri = oAuthClient.resource(bearerClientRequest, OAuth.HttpMethod.GET, OAuthResourceResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resourceResponsePrevFeedUri;
	}
	
	public static String parseFeedJsonToGetPrevUri(String feedJson) {
		String prevFeedUri = null;
		JsonFactory jfactory = new JsonFactory();
		JsonParser jParser = null;
		try {
			jParser = jfactory.createJsonParser(feedJson);
			
			while (jParser.nextToken() != null) {

				String fieldname = jParser.getCurrentName();
				if ("next".equals(fieldname)) {
					jParser.nextToken();
					
					prevFeedUri = jParser.getText();
				}

			}

		} catch (IOException e1) {

			e1.printStackTrace();
		}

		return prevFeedUri;
	}

}
