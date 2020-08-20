package br.com.alura.forum.repository;

import br.com.alura.forum.modelos.Topico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

}
