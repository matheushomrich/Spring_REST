package br.com.alura.forum.controllers;

import br.com.alura.forum.controllers.DTO.TopicosDTO;
import br.com.alura.forum.modelos.Curso;
import br.com.alura.forum.modelos.Topico;
import br.com.alura.forum.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
//@Controller
public class TopicosController {

    @Autowired
    private TopicoRepository topicoRepository;

    @RequestMapping("/topicos")
    //@ResponseBody
    public List<TopicosDTO> listaTopicos() {

        List<Topico> topicos = topicoRepository.findAll();

        //Topico topico = new Topico("Spring", "Spring error", new Curso("Spring Boot", "Programação"));
        //return TopicosDTO.converter(Arrays.asList(topico, topico, topico));

        return TopicosDTO.converter(topicos);
    }
}
