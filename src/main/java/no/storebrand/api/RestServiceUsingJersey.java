/**
 * 
 */
package no.storebrand.api;

import java.net.URI;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import org.springframework.stereotype.Component;

//import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Component
@Path(value="/")
public class RestServiceUsingJersey {

	@Path("/message")
	@GET
	public Response message() {
		return Response.status(Status.ACCEPTED).entity("Hello Jersy Rest Spring").build();
	}
	
	@Path("/customer")
	@GET
	public Response customer() {
		URI uri = UriBuilder.fromUri("http://client-u:12024/cm-rest-api/me/customer").build();
		return Response.seeOther(uri).build();
	}
}
