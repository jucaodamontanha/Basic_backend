package br.com.basic.basic.tarefas;

import br.com.basic.basic.cadastro.CadastroNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TarefasController {
    @Autowired
    private  TarefasService tarefasService;

    @GetMapping("/tarefas/lista")
    public List<TarefasModel> listarTodos(){
        return tarefasService.listarTodos();
    }
    @GetMapping("/tarefas/{id}")
    public ResponseEntity<TarefasModel> buscar(@PathVariable Long id){
    return  tarefasService.buscar(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @PostMapping("/tarefa")
    public ResponseEntity<String> createTarefa (@RequestBody TarefasModel tarefasModel){
        TarefasModel saveTarefa = tarefasService.salvar(tarefasModel);
        return  ResponseEntity.status(HttpStatus.CREATED).body("Tarefa cadastrada com sucesso!");
    }

    @PutMapping("/tarefas/{id}")
    public ResponseEntity<TarefasModel> atualizar (@PathVariable Long id, @RequestBody TarefasModel tarefasModel){
        try {
            TarefasModel updateTarefa = tarefasService.atualizar(id, tarefasModel);
            return ResponseEntity.ok(updateTarefa);
        } catch (CadastroNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/tarefas/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        tarefasService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
