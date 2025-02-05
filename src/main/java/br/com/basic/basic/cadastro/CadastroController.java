package br.com.basic.basic.cadastro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
public class CadastroController {

    private final CadastroService cadastroService;

    @Autowired
    public CadastroController(CadastroService cadastroService) {
        this.cadastroService = cadastroService;
    }

    /**
     * Lista todos os cadastros.
     * @return Lista de CadastroModel.
     */
    @GetMapping("/todos")
    public List<CadastroModel> getAllCadastroModel() {
        return cadastroService.listarTodos();
    }

    /**
     * Busca um cadastro pelo ID.
     * @param id ID do cadastro.
     * @return ResponseEntity contendo o cadastro, se encontrado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CadastroModel> buscar(@PathVariable Long id) {
        return cadastroService.buscar(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cria um novo cadastro.
     * @param cadastroModel Cadastro a ser criado.
     * @return ResponseEntity com mensagem de sucesso.
     */
    @CrossOrigin(origins = "http://localhost:8081")
    @PostMapping("/cadastro")
    public ResponseEntity<String> createCadastroModel(@RequestBody CadastroModel cadastroModel) {
        System.out.println("Requisição recebida: " + cadastroModel);
        CadastroModel saveCadastro = cadastroService.salvar(cadastroModel);
        System.out.println("Cadastro salvo: " + saveCadastro);
        return ResponseEntity.status(HttpStatus.CREATED).body("Cadastro realizado com sucesso!");
    }

    /**
     * Atualiza um cadastro existente.
     * @param id ID do cadastro a ser atualizado.
     * @param cadastroModel Dados atualizados do cadastro.
     * @return ResponseEntity com mensagem de sucesso.
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> atualizar(@PathVariable Long id, @RequestBody CadastroModel cadastroModel) {
        try {
            CadastroModel updateCadastro = cadastroService.atualizar(id, cadastroModel);
            return ResponseEntity.ok("Cadastro atualizado com sucesso!");
        } catch (CadastroNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deleta um cadastro pelo ID.
     * @param id ID do cadastro a ser deletado.
     * @return ResponseEntity sem conteúdo.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        cadastroService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Autentica um usuário pelo email e senha.
     * @param cadastroModel Dados de login do usuário.
     * @return ResponseEntity com resultado da autenticação.
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody CadastroModel cadastroModel) {
        CadastroModel usuarioAutenticado = cadastroService.autenticar(cadastroModel.getEmail(), cadastroModel.getSenha());
        Map<String, Object> response = new HashMap<>();
        if (usuarioAutenticado != null) {
            response.put("success", true);
            response.put("message", "Login bem-sucedido!");
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "Falha no login. Verifique suas credenciais.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
    /**
     * Lista todos os supervisores.
     * @return Lista de NomeCompletoDTO com a função de supervisor.
     */
    @GetMapping("/cadastros/supervisores")
    public ResponseEntity<List<NomeCompletoDTO>> listarSupervisores() {
        List<NomeCompletoDTO> supervisores = cadastroService.listarSupervisores();
        if (supervisores.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(supervisores);
    }

    /**
     * Lista todos os técnicos.
     * @return Lista de NomeCompletoDTO com a função de técnico.
     */
    @GetMapping("/cadastros/tecnicos")
    public ResponseEntity<List<NomeCompletoDTO>> listarTecnicos() {
        List<NomeCompletoDTO> tecnicos = cadastroService.listarTecnicos();
        if (tecnicos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tecnicos);
    }
}