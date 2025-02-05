package br.com.basic.basic.cadastro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CadastroService {

    private final CadastroRepository cadastroRepository;

    @Autowired
    public CadastroService(CadastroRepository cadastroRepository) {
        this.cadastroRepository = cadastroRepository;
    }
    public List<CadastroModel> listarTodos(){
        return  cadastroRepository.findAll();
    }
    /**
     * Salva um novo cadastro.
     * @param cadastroModel Cadastro a ser salvo.
     * @return Cadastro salvo.
     */
    public CadastroModel salvar(CadastroModel cadastroModel) {
        return cadastroRepository.save(cadastroModel);
    }

    /**
     * Busca um cadastro pelo ID.
     * @param id ID do cadastro.
     * @return Optional contendo o cadastro, se encontrado.
     */
    public Optional<CadastroModel> buscar(Long id) {
        return cadastroRepository.findById(id);
    }

    /**
     * Atualiza um cadastro existente.
     * @param id ID do cadastro a ser atualizado.
     * @param cadastroModel Dados atualizados do cadastro.
     * @return Cadastro atualizado.
     * @throws CadastroNotFoundException Se o cadastro não for encontrado.
     */
    public CadastroModel atualizar(Long id, CadastroModel cadastroModel) throws CadastroNotFoundException {
        if (!cadastroRepository.existsById(id)) {
            throw new CadastroNotFoundException("Cadastro não encontrado");
        }
        cadastroModel.setId(id);
        return cadastroRepository.save(cadastroModel);
    }

    /**
     * Deleta um cadastro pelo ID.
     * @param id ID do cadastro a ser deletado.
     */
    public void deletar(Long id) {
        cadastroRepository.deleteById(id);
    }

    /**
     * Autentica um usuário pelo email e senha.
     * @param email Email do usuário.
     * @param senha Senha do usuário.
     * @return CadastroModel do usuário autenticado, ou null se não autenticado.
     */
    public CadastroModel autenticar(String email, String senha) {
        CadastroModel usuario = cadastroRepository.findByEmail(email);
        if (usuario != null && usuario.getSenha().equals(senha)) {
            return usuario;
        }
        return null;
    }

    /**
     * Lista todos os supervisores.
     * @return Lista de NomeCompletoDTO com a função de supervisor.
     */
    public List<NomeCompletoDTO> listarSupervisores() {
        return cadastroRepository.findByFuncao("supervisor")
                .stream()
                .map(cadastro -> new NomeCompletoDTO(cadastro.getNomeCompleto()))
                .collect(Collectors.toList());
    }

    /**
     * Lista todos os técnicos.
     * @return Lista de NomeCompletoDTO com a função de técnico.
     */
    public List<NomeCompletoDTO> listarTecnicos() {
        return cadastroRepository.findByFuncao("tecnico")
                .stream()
                .map(cadastro -> new NomeCompletoDTO(cadastro.getNomeCompleto()))
                .collect(Collectors.toList());
    }
}