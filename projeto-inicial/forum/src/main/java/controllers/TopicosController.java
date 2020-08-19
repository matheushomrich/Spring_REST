package controllers;

import controllers.DTO.TopicosDTO;
import modelos.Curso;
import modelos.Topico;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class TopicosController {

    @RequestMapping("/topicos")
    public List<TopicosDTO> listaTopicos() {

        Topico topico = new Topico("Spring", "Spring error", new Curso("Spring Boot", "Programação"));

        return TopicosDTO.converter(Arrays.asList(topico, topico, topico));
    }
}
