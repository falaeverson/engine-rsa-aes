package api;

import api.dto.responses.CartaoValidarSenhaResonse;
import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import criptografia.Configuracoes;
import com.sun.jersey.api.json.JSONConfiguration;

import javax.ws.rs.core.MediaType;

public class ApiCartao {

    private Client client;

    private WebResource webResource;

    private Gson json = new Gson();

    public ApiCartao(){

        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);

        client = Client.create(clientConfig);

        webResource = client.resource(Configuracoes.HOST + Configuracoes.BASE_PATH + "/cartoes/3253558/validar-senha");
        webResource.accept(MediaType.APPLICATION_JSON);
        webResource.type(MediaType.APPLICATION_XML);

    }

    //public CartaoValidarSenhaResonse validarSenha(String senha){
    public String validarSenha(String senha){

        ClientResponse response = webResource.header(Configuracoes.HEADER_ACCESS_TOKEN_NAME, Configuracoes.ACCESS_TOKEN_VALUE).post(ClientResponse.class, senha);

        String output = response.getEntity(String.class);

        System.out.println(output);
        return output;
        //return json.fromJson(output, CartaoValidarSenhaResonse.class);

    }

}
