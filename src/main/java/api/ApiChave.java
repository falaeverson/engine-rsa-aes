package api;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import criptografia.Configuracoes;
import api.dto.responses.ChaveAESReponse;

import javax.ws.rs.core.MediaType;

public class ApiChave {

    private Client client = Client.create();;

    private WebResource webResource;

    private Gson json = new Gson();

    public ApiChave(){

        webResource = client.resource(Configuracoes.HOST + Configuracoes.BASE_PATH + "/criptografia/chave");
        webResource.accept(MediaType.APPLICATION_JSON);
        webResource.type(MediaType.APPLICATION_XML);

    }

    public ChaveAESReponse chaveAES(){

        ClientResponse response = webResource.header(Configuracoes.HEADER_ACCESS_TOKEN_NAME, Configuracoes.ACCESS_TOKEN_VALUE).get(ClientResponse.class);

        String output = response.getEntity(String.class);

        return json.fromJson(output, ChaveAESReponse.class);

    }

}
