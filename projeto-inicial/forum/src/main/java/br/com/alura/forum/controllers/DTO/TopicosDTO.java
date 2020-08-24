package br.com.alura.forum.controllers.DTO;

import br.com.alura.forum.modelos.Topico;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class TopicosDTO {

    private Long id;
    private String titulo;
    private String mensagem;
    private LocalDateTime dataCriacao;

    public TopicosDTO (Topico topico) {
        this.id = topico.getId();
        this.titulo = topico.getTitulo();
        this.mensagem = topico.getMensagem();
        this.dataCriacao = topico.getDataCriacao();
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public static Page<TopicosDTO> converter(Page<Topico> topicos) {
        //return topicos.stream().map(TopicosDTO::new).collect(Collectors.toList());
        return topicos.map(TopicosDTO::new);
    }
}
