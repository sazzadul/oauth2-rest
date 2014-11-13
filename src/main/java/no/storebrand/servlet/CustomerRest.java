package no.storebrand.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
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

public class CustomerRest extends HttpServlet {
	private static Logger log = Logger.getLogger(CustomerRest.class);
	private static final long serialVersionUID = -7848101874878462953L;
 
	private static final String LOCATION = "http://localhost:8080/oauth2-rest/oauth/token";
	private static final String CLIENT_ID = "oauth-rest";
	private static final String CLIENT_SECRET = "19Snuse99";
	
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        log.debug("init() called");
    }
 
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	log.debug("service() called");
    	String customerResponse = null;
    	
    	try {
			OAuthClientRequest oAuthRequest = OAuthClientRequest
				.tokenLocation(LOCATION)
                .setGrantType(GrantType.CLIENT_CREDENTIALS)
                .setClientId(CLIENT_ID)
                .setClientSecret(CLIENT_SECRET)
                .buildQueryMessage();
 
			log.debug("About to retrieve AccessToken");
			
            OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
            OAuthClientResponse oAuthClientResp = oAuthClient.accessToken(oAuthRequest);
		
            String accessToken = oAuthClientResp.getParam("access_token");
            
            log.debug("accessToken=" + accessToken);
            
            OAuthClientRequest bearerClientRequest = new OAuthBearerClientRequest("http://localhost:8080/oauth2-rest/rest/customer")
            															.setAccessToken(accessToken)
            															.buildQueryMessage();
    
            bearerClientRequest.addHeader("SM_SSN", "14077629913");
            bearerClientRequest.addHeader("SM_ACTIVEROLE", "customer");
            bearerClientRequest.addHeader("ClientId", "oauth-rest");
            bearerClientRequest.addHeader("SM_DPA", "stb-customer-customer");
            
            OAuthResourceResponse resourceResponse = oAuthClient.resource(bearerClientRequest, OAuth.HttpMethod.GET, OAuthResourceResponse.class);
           
            customerResponse = resourceResponse.getBody();
            log.debug(customerResponse);
            
		} catch (OAuthSystemException e) {
			log.error(e);
		} catch (OAuthProblemException e) {
			log.error(e);
		}
    	response.getWriter().write(customerResponse);
    }
 
    @Override
    public void destroy() {
    	log.debug("destroy() called");
    }
  }