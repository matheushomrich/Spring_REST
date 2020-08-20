package br.com.alura.forum.controllers;

import br.com.alura.forum.controllers.DTO.TopicosDTO;
import br.com.alura.forum.controllers.form.TopicoForm;
import br.com.alura.forum.modelos.Curso;
import br.com.alura.forum.modelos.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

@RestController
//@Controller
@RequestMapping("/topicos")
public class TopicosController {

    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private CursoRepository cursoRepository;

    @GetMapping
    //@RequestMapping(value = "/topicos", method = RequestMethod.GET)
    //@ResponseBody
    public List<TopicosDTO> listaTopicos(String nomeCurso) {

        //System.out.println(nomeCurso);

        if(nomeCurso == null) {
            List<Topico> topicos = topicoRepository.findAll();
            return TopicosDTO.converter(topicos);
        } else {
            List<Topico> topicos = topicoRepository.findByCursoNome(nomeCurso);
            return TopicosDTO.converter(topicos);
        }
        //Topico topico = new Topico("Spring", "Spring error", new Curso("Spring Boot", "Programação"));
        //return TopicosDTO.converter(Arrays.asList(topico, topico, topico));
    }

    @PostMapping
    //@RequestMapping(value = "/topicos", method = RequestMethod.POST)
    public ResponseEntity<TopicosDTO> cadastrar(@RequestBody TopicoForm topicoForm, UriComponentsBuilder uriComponentsBuilder) {

        Topico topico = topicoForm.converter(cursoRepository);
        topicoRepository.save(topico);

        URI uri = uriComponentsBuilder.path("topicos/(id)").buildAndExpand(topico.getId()).toUri();

        return ResponseEntity.created(uri).body(new TopicosDTO(topico));
    }
}
