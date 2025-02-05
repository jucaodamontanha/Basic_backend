package br.com.basic.basic.cadastro;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CadastroRepository extends JpaRepository<CadastroModel, Long> {

    CadastroModel findByEmail(String email);

    List<CadastroModel> findByFuncaoIn(List<String> funcao);

    // Novo método para buscar por função
    List<CadastroModel> findByFuncao(String funcao);
}