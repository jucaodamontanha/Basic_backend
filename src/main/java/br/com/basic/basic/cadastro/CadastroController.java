package br.com.basic.basic.cadastro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class CadastroController {
    @Autowired
    private CadastroService cadastroService;

    @GetMapping("/todos")
    public List<CadastroModel> getAllCadastroModel(){
        return cadastroService.listarTodos();
    }
    @GetMapping("/{id}")
    public ResponseEntity<CadastroModel> buscar(@PathVariable Long id){
        return cadastroService.buscar(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @PostMapping("/cadastro")
    public ResponseEntity<String> createCadastroModel( @RequestBody CadastroModel cadastroModel){
        CadastroModel saveCadastro = cadastroService.salvar(cadastroModel);
        return ResponseEntity.status(HttpStatus.CREATED).body("Cadastro realizado com sucesso!");
    }
    @PutMapping("/{id}")
    public ResponseEntity<CadastroModel> atualizar (@PathVariable Long id,@RequestBody CadastroModel cadastroModel){
        try {
            CadastroModel updateCadastro = cadastroService.atualizar(id, cadastroModel);
            return ResponseEntity.ok(updateCadastro);
        } catch (CadastroNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCadastroModel(@PathVariable Long id){
        cadastroService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
