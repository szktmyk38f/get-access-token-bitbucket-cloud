package com.szktmyk.controller;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

/** 
 * 
 * @author szktmyk38f
 *
 */
@RestController
@CrossOrigin(origins = "*") 
public class GetAccessTokenContoller {
	
	private static final String GRANT_TYPE = "client_credentials";
	private static final String CLIENT_ID = "XXXXXXXXXXXXXXXXXX";
	private static final String CLIENT_SECRET = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";

	/** 
	 * 
	 * @return String access_token
	 *
	 */
	@GetMapping("/getAccessToken")
    @ResponseBody
	public String getAccessToken() {
        RestTemplate restTemplate = new RestTemplate();
        String request = "grant_type=" + GRANT_TYPE;
		
        String plainCreds = CLIENT_ID + ":" + CLIENT_SECRET;
        String userPassword = new String(Base64.getEncoder().encode(plainCreds.getBytes()));
        StringBuilder basicAuth = new StringBuilder();
        basicAuth.append("Basic ").append(userPassword);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", basicAuth.toString());
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
        String url = "https://bitbucket.org/site/oauth2/access_token";
        HttpEntity<String> entity = new HttpEntity<String>(request, headers);
		
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        String body = response.getBody();
        JSONObject jObject = new JSONObject(body);
        String accessToken = jObject.getString("access_token");
        return accessToken;
	}
}
