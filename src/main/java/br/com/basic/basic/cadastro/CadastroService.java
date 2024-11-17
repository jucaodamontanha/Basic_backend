package br.com.basic.basic.cadastro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CadastroService {
    @Autowired
    private CadastroRepository cadastroRepository;

    public CadastroModel salvar(CadastroModel cadastroModel){
        return  cadastroRepository.save(cadastroModel);
    }

    public List<CadastroModel> listarTodos(){
        return  cadastroRepository.findAll();
    }

    public Optional <CadastroModel> buscar (Long id){
        return cadastroRepository.findById(id);
    }

    public CadastroModel atualizar(Long id, CadastroModel cadastroModel) {
        return cadastroRepository.findById(id).map(existingCadastro -> {
            existingCadastro.setNomeClompleto(cadastroModel.getNomeClompleto());
            existingCadastro.setEmail(cadastroModel.getEmail());
            existingCadastro.setSenha(cadastroModel.getSenha());
            existingCadastro.setFuncao(cadastroModel.getFuncao());
            return cadastroRepository.save(existingCadastro);

        }).orElseThrow(() -> new CadastroNotFoundException("Cadastro não encontrado!"));
    }


    public void deletar (Long id){
        cadastroRepository.deleteById(id);
    }

}
