package br.com.alura.forum.config.validation;

public class FormErrorDTO {

    private String campoErro;
    private String mensagem;

    public FormErrorDTO(String campoErro, String mensagem) {
        this.campoErro = campoErro;
        this.mensagem = mensagem;
    }

    public String getCampoErro() {
        return campoErro;
    }

    public String getMensagem() {
        return mensagem;
    }
}
