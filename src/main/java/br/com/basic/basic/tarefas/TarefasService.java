package br.com.basic.basic.tarefas;

import br.com.basic.basic.cadastro.CadastroNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TarefasService {
    @Autowired
    private TarefaRepository tarefaRepository;

    public TarefasModel salvar (TarefasModel tarefasModel){
        return  tarefaRepository.save(tarefasModel);
    }

    public List<TarefasModel> listarTodos(){
        return tarefaRepository.findAll();
    }

    public Optional <TarefasModel> buscar(Long id){
    return tarefaRepository.findById(id);
    }

    public void deletar (Long id){
         tarefaRepository.deleteById(id);
    }

    public TarefasModel atualizar(Long id, TarefasModel tarefasModel) {
        return tarefaRepository.findById(id).map(existingTarefa -> {
            existingTarefa.setCidade(tarefasModel.getCidade());
            existingTarefa.setDataFinal(tarefasModel.getDataFinal());
            existingTarefa.setObservacao(tarefasModel.getObservacao());
            existingTarefa.setStatus(tarefasModel.getStatus());
            existingTarefa.setNumeroContrato(tarefasModel.getNumeroContrato());
            existingTarefa.setSupervisor(tarefasModel.getSupervisor());
            existingTarefa.setTecnico(tarefasModel.getTecnico());

            return tarefaRepository.save(existingTarefa);

        }).orElseThrow(() -> new CadastroNotFoundException("Cadastro não encontrado!"));
    }
}
