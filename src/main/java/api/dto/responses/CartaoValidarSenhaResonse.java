package api.dto.responses;

import lombok.Data;

@Data
public class CartaoValidarSenhaResonse {

    private String mensagem;

    private Integer idStatusCartao;

    private String statusCartao;

    private Integer quantidadeTentativas;

    private Integer quantidadeMaximaTentativas;

}
