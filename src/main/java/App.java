import api.ApiCartao;
import api.ApiChave;
import criptografia.Configuracoes;
import criptografia.GerenciadorAES;
import criptografia.GerenciadorRSA;
import api.dto.responses.ChaveAESReponse;

import javax.crypto.SecretKey;
import java.security.PrivateKey;
import java.util.Base64;

public class App {

     public static void main(String[] args) {

          ApiClient apiClient = new ApiClient();
          apiClient.setBasePath(Configuracoes.HOST);
          ((ApiKeyAuth) apiClient.getAuthentication("access_token")).setApiKey(Configuracoes.TOKEN);

               GerenciadorRSA engineRSA = new GerenciadorRSA();
               GerenciadorAES engineEAS = new GerenciadorAES();

               // Carregando Chave Privada
               PrivateKey privateKey = engineRSA.carregaChavePrivada(Configuracoes.PRIVATE_KEY_EMISSOR);

               // Busca a chave AES na API do PIER
               ChaveAESReponse chaveAES = new ApiChave().chaveAES();

               // Decodifica Base64, descriptografa e carrega a chave AES
               byte[] secretKeyTemp = Base64.getDecoder().decode(chaveAES.getConteudo());
               byte[] secretKeyByte = engineRSA.descriptografar(secretKeyTemp, privateKey);
               SecretKey secretKey = engineEAS.carregaChaveAES(secretKeyByte);

               // ------------------------CRIPTOGRAFANDO CONTEÚDO------------------------
               byte[] objetoCriptografado = engineEAS.criptografar("{\"senha\":\"1234\"} ".getBytes(), secretKey);
               String bodyCriptografado = Base64.getEncoder().encodeToString(objetoCriptografado);
               System.out.println(bodyCriptografado);

               // Valida senha do cartão no PIER
               String responseSenhaCriptografada = new ApiCartao().validarSenha(bodyCriptografado);

               // ------------------------DE CRIPTOGRAFANDO CONTEÚDO------------------------
               byte[] dadosDecodeBase64 = Base64.getDecoder().decode(responseSenhaCriptografada.getBytes()); //Base64.getDecoder().decode(bodyCriptografado.getBytes());
               byte[] dadosDescriptografados = engineEAS.decriptografar(dadosDecodeBase64, secretKey);
               System.out.println(new String(dadosDescriptografados, "UTF-8"));

          } catch (Exception e) {
               e.printStackTrace();
          }

     }

}
