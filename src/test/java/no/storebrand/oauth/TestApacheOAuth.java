package no.storebrand.oauth;

import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthBearerClientRequest;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthClientResponse;
import org.apache.oltu.oauth2.client.response.OAuthResourceResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;

public class TestApacheOAuth {
	private static final String LOCATION = "http://localhost:8006/oauth2-rest/oauth/token";
	private static final String CLIENT_ID = "oauth-rest";
	private static final String CLIENT_SECRET = "19Snuse99";
	
	public static void main(String[] args) {
		System.out.println("TestApacheOAuth....");
		try {
				OAuthClientRequest request = OAuthClientRequest
					.tokenLocation(LOCATION)
	                .setGrantType(GrantType.CLIENT_CREDENTIALS)
	                .setClientId(CLIENT_ID)
	                .setClientSecret(CLIENT_SECRET)
	                .buildQueryMessage();
	 
	            OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
	            OAuthClientResponse oAuthClientResp = oAuthClient.accessToken(request);
			
	            String accessToken = oAuthClientResp.getParam("access_token");
	            String tokenType = oAuthClientResp.getParam("token_type");
	            String expiresIn = oAuthClientResp.getParam("expires_in");
	            String scope = oAuthClientResp.getParam("scope");
	            
	            System.out.println("accessToken=" + accessToken);
	            System.out.println("tokenType=" + tokenType);
	            System.out.println("expiresIn=" + expiresIn);
	            System.out.println("scope=" + scope);
	            
	            OAuthClientRequest bearerClientRequest = new OAuthBearerClientRequest("http://localhost:8006/oauth2-rest/rest/customer")
	            															.setAccessToken(accessToken)
	            															.buildQueryMessage();
	    
	            bearerClientRequest.addHeader("SM_SSN", "14077629913");
	            bearerClientRequest.addHeader("SM_ACTIVEROLE", "customer");
	            bearerClientRequest.addHeader("ClientId", "oauth-rest");
	            bearerClientRequest.addHeader("SM_DPA", "stb-customer-customer");
	            
	            OAuthResourceResponse resourceResponse = oAuthClient.resource(bearerClientRequest, OAuth.HttpMethod.GET, OAuthResourceResponse.class);
	            System.out.println(resourceResponse.getBody());
	            
		} catch (OAuthSystemException e) {
			e.printStackTrace();
		} catch (OAuthProblemException e) {
			e.printStackTrace();
		}
	}
}
