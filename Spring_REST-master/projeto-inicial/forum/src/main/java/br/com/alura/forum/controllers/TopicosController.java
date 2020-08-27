package br.com.alura.forum.controllers;

import br.com.alura.forum.controllers.DTO.DetalhesTopicoDTO;
import br.com.alura.forum.controllers.DTO.TopicosDTO;
import br.com.alura.forum.controllers.form.TopicoForm;
import br.com.alura.forum.controllers.form.UpdateTopicoForm;
import br.com.alura.forum.modelos.Curso;
import br.com.alura.forum.modelos.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
//@Controller
@RequestMapping("/topicos")
public class TopicosController {

    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private CursoRepository cursoRepository;

    @GetMapping
    @Cacheable(value = "listaDeTopicos")
    //@RequestMapping(value = "/topicos", method = RequestMethod.GET)
    //@ResponseBody
    public Page<TopicosDTO> listaTopicos(@RequestParam(required = false) String nomeCurso,
                                         /*@RequestParam(required = true) int pagina,
                                         @RequestParam(required = true) int qtd,
                                         @RequestParam(required = false) String ordenacao*/
                                         @PageableDefault(sort = "id", direction = Sort.Direction.DESC, page = 0, size = 10) Pageable paginacao) {

        //Pageable paginacao = PageRequest.of(pagina, qtd, Sort.Direction.DESC, ordenacao);

        //System.out.println(nomeCurso);

        if(nomeCurso == null) {
            Page<Topico> topicos = topicoRepository.findAll(paginacao);
            return TopicosDTO.converter(topicos);
        } else {
            Page<Topico> topicos = topicoRepository.findByCursoNome(nomeCurso, paginacao);
            return TopicosDTO.converter(topicos);
        }
        //Topico topico = new Topico("Spring", "Spring error", new Curso("Spring Boot", "Programação"));
        //return TopicosDTO.converter(Arrays.asList(topico, topico, topico));
    }

    @PostMapping
    @Transactional
    @CacheEvict(value = "listaDeTopicos", allEntries = true)
    //@RequestMapping(value = "/topicos", method = RequestMethod.POST)
    public ResponseEntity<TopicosDTO> cadastrar(@RequestBody @Valid TopicoForm topicoForm, UriComponentsBuilder uriComponentsBuilder) {

        Topico topico = topicoForm.converter(cursoRepository);
        topicoRepository.save(topico);

        URI uri = uriComponentsBuilder.path("topicos/(id)").buildAndExpand(topico.getId()).toUri();

        return ResponseEntity.created(uri).body(new TopicosDTO(topico));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalhesTopicoDTO> detail(@PathVariable("id") Long codigo) {
        Optional<Topico> topico = topicoRepository.findById(codigo);
        if(topico.isPresent()) {
            return ResponseEntity.ok( new DetalhesTopicoDTO(topico.get()));
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @PutMapping("/{id}")
    @Transactional
    @CacheEvict(value = "listaDeTopicos", allEntries = true)
    public ResponseEntity<TopicosDTO> atualizar(@PathVariable("id") Long codigo, @RequestBody @Valid UpdateTopicoForm topicoForm) {
        Optional<Topico> optional = topicoRepository.findById(codigo);
        if(optional.isPresent()) {
            Topico topico = topicoForm.atualizar(codigo, topicoRepository);
            return ResponseEntity.ok( new TopicosDTO(topico));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}")
    @Transactional
    @CacheEvict(value = "listaDeTopicos", allEntries = true)
    public ResponseEntity<?> remove(@PathVariable("id") Long id) {
        Optional<Topico> optional = topicoRepository.findById(id);
        if(optional.isPresent()) {
            topicoRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }

    }

}
