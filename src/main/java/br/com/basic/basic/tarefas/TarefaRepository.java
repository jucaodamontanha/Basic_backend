package br.com.basic.basic.tarefas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarefaRepository extends JpaRepository<TarefasModel, Long> {
        List<TarefasModel> findByTecnico(String tecnico);
        List<TarefasModel> findBySupervisor(String supervisor);
}