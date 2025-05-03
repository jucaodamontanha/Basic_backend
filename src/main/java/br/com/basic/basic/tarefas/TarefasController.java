package br.com.basic.basic.tarefas;
import br.com.basic.basic.cadastro.CadastroModel;
import br.com.basic.basic.cadastro.CadastroNotFoundException;
import br.com.basic.basic.cadastro.CadastroService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class TarefasController {

    @Autowired
    private static final Logger logger = LoggerFactory.getLogger(TarefasController.class);
    @Autowired
    private final TarefasService tarefasService;
    @Autowired
    private NotificacaoService notificacaoService;
    @Autowired
    private CadastroService cadastroService;

    @Autowired
    public TarefasController(TarefasService tarefasService) {
        this.tarefasService = tarefasService;
    }

    @GetMapping("/tarefas")
    public List<TarefasModel> listarTodos() {
        List<TarefasModel> tarefas = tarefasService.listarTodos();
        logger.info("Listando todas as tarefas: {}", tarefas);
        return tarefas;
    }

    @GetMapping("/tarefas/{id}")
    public ResponseEntity<TarefasModel> buscar(@PathVariable Long id) {
        Optional<TarefasModel> tarefa = tarefasService.buscar(id);
        logger.info("Buscando tarefa com ID {}: {}", id, tarefa);
        return tarefa.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/tarefa")
    public ResponseEntity<String> createTarefa(@RequestBody TarefasModel tarefasModel) {
        // Salvar a tarefa no banco de dados
        TarefasModel saveTarefa = tarefasService.salvar(tarefasModel);
        logger.info("Criando nova tarefa: {}", saveTarefa);

        // Buscar supervisores cadastrados
        List<CadastroModel> supervisores = cadastroService.buscarPorFuncao("supervisor");

        // Enviar notificações para os supervisores
        for (CadastroModel supervisor : supervisores) {
            if (supervisor.getTokenNotificacao() != null) {
                notificacaoService.enviarNotificacao(
                        supervisor.getTokenNotificacao(),
                        "Nova Tarefa Criada",
                        "Uma nova tarefa foi atribuída. Verifique os detalhes."
                );
            }
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("Tarefa cadastrada com sucesso!");
    }

    @PutMapping("/tarefas/{id}")
    public ResponseEntity<TarefasModel> atualizar(@PathVariable Long id, @RequestBody TarefasModel tarefasModel) {
        try {
            logger.info("Recebendo solicitação para atualizar tarefa com ID {}: {}", id, tarefasModel);
            TarefasModel updateTarefa = tarefasService.atualizar(id, tarefasModel);
            logger.info("Tarefa atualizada com sucesso: {}", updateTarefa);
            return ResponseEntity.ok(updateTarefa);
        } catch (CadastroNotFoundException e) {
            logger.error("Erro ao atualizar tarefa com ID {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/tarefas/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tarefasService.deletar(id);
        logger.info("Deletando tarefa com ID {}", id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/tarefas/tecnico")
    public List<TarefasModel> listarTecnico() {
        List<TarefasModel> tarefas = tarefasService.listarTecnico();
        logger.info("Listando todas as tarefas com função de técnico: {}", tarefas);
        return tarefas;
    }
    @PutMapping("/tarefas/{id}/status")
    public ResponseEntity<TarefasModel> atualizarStatus(@PathVariable Long id, @RequestBody Boolean novoStatus) {
        try {
            logger.info("Recebendo solicitação para atualizar status da tarefa com ID {}: {}", id, novoStatus);
            TarefasModel tarefaAtualizada = tarefasService.atualizarStatus(id, novoStatus);
            logger.info("Status da tarefa atualizado com sucesso: {}", tarefaAtualizada);
            return ResponseEntity.ok(tarefaAtualizada);
        } catch (CadastroNotFoundException e) {
            logger.error("Erro ao atualizar status da tarefa com ID {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}