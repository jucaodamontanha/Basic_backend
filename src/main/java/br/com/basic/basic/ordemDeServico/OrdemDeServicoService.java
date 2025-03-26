package br.com.basic.basic.ordemDeServico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdemDeServicoService {

    @Autowired
    private OrdemDeServicoRepository ordemDeServicoRepository;

    public OrdermDeServicoModel salvar(OrdermDeServicoModel ordemDeServicoModel) {
        return ordemDeServicoRepository.save(ordemDeServicoModel);
    }
    public List<OrdermDeServicoModel> buscarTodas() {
        return ordemDeServicoRepository.findAll();
    }
}
