package br.com.basic.basic.tarefas;

import br.com.basic.basic.cadastro.CadastroNotFoundException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import java.util.List;
import java.util.Optional;


@Service
public class TarefasService {



    private static final Logger logger = LoggerFactory.getLogger(TarefasService.class);
    private final TarefaRepository tarefaRepository;

    @Autowired
    public TarefasService(TarefaRepository tarefaRepository) {
        this.tarefaRepository = tarefaRepository;
    }

    public List<TarefasModel> listarTodos() {
        List<TarefasModel> tarefas = tarefaRepository.findAll();
        logger.info("Listando todas as tarefas: {}", tarefas);
        return tarefas;
    }

    public List<TarefasModel> listarTecnico() {
        List<TarefasModel> tarefas = tarefaRepository.findByTecnico("técnico");
        logger.info("Listando todas as tarefas com função de técnico: {}", tarefas);
        return tarefas;
    }

    public Optional<TarefasModel> buscar(Long id) {
        Optional<TarefasModel> tarefa = tarefaRepository.findById(id);
        logger.info("Buscando tarefa com ID {}: {}", id, tarefa);
        return tarefa;
    }

    public TarefasModel salvar(TarefasModel tarefasModel) {
        TarefasModel tarefaSalva = tarefaRepository.save(tarefasModel);
        logger.info("Salvando nova tarefa: {}", tarefaSalva);
        return tarefaSalva;
    }

    public TarefasModel atualizar(Long id, TarefasModel tarefasModel) throws CadastroNotFoundException {
        if (!tarefaRepository.existsById(id)) {
            throw new CadastroNotFoundException("Tarefa not found");
        }
        tarefasModel.setId(id);
        logger.info("Atualizando tarefa com ID {}: {}", id, tarefasModel);
        TarefasModel tarefaAtualizada = tarefaRepository.save(tarefasModel);
        logger.info("Tarefa atualizada com sucesso: {}", tarefaAtualizada);
        return tarefaAtualizada;
    }

    public void deletar(Long id) {
        tarefaRepository.deleteById(id);
        logger.info("Deletando tarefa com ID {}", id);
    }
    public TarefasModel atualizarStatus(Long id, Boolean novoStatus) throws CadastroNotFoundException {
        Optional<TarefasModel> tarefaOptional = buscar(id);
        if (tarefaOptional.isPresent()) {
            TarefasModel tarefa = tarefaOptional.get();
            logger.info("Status atual antes da atualização: {}", tarefa.getStatus());
            tarefa.setStatus(novoStatus); // Atualiza o status
            TarefasModel tarefaAtualizada = salvar(tarefa); // Salva a tarefa atualizada no banco de dados
            logger.info("Status atualizado: {}", tarefaAtualizada.getStatus());
            return tarefaAtualizada;
        } else {
            throw new CadastroNotFoundException("Tarefa não encontrada");
        }
    }
}