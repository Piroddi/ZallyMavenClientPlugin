package apliLinterPlugin.client;

import apliLinterPlugin.client.Entities.Request.ApiRequestEntity;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URL;

@Named
@Singleton
public class ApiLinterClient {

    Response invokeApiServer(ApiRequestEntity apiRequestEntity, URL apiLinterServerUrl) {

        Client client = ClientBuilder.newClient( );
        WebTarget webTarget = client.target(apiLinterServerUrl.toString()).path("api-violations");
        Invocation.Builder invocationBuilder =  webTarget.request(MediaType.APPLICATION_JSON);
        try {
            return invocationBuilder.post(Entity.entity(apiRequestEntity, MediaType.APPLICATION_JSON));
        } catch (Exception e){
            Response.ResponseBuilder response = Response.status(500);
            response.entity(e.getMessage());
            return response.build();
        }

    }
}
