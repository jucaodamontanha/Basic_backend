package br.com.basic.basic.ordemDeServico;

import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.mail.MessagingException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
public class OrdemDeServicoController {

    private final OrdemDeServicoService ordemDeServicoService;
    private final EmailService emailService;
    private final PdfService pdfService;

    @Autowired
    public OrdemDeServicoController(OrdemDeServicoService ordemDeServicoService, EmailService emailService, PdfService pdfService) {
        this.ordemDeServicoService = ordemDeServicoService;
        this.emailService = emailService;
        this.pdfService = pdfService;
    }

    @PostMapping("/os")
    public ResponseEntity<?> createOs(@RequestBody OrdermDeServicoModel ordemDeServicoModel) throws MessagingException, DocumentException, IOException {
        ordemDeServicoService.salvar(ordemDeServicoModel);

        ByteArrayOutputStream pdfContent = pdfService.gerarPdf(ordemDeServicoModel);
        String subject = "Ordem de Serviço - " + ordemDeServicoModel.getCidade() + " - " + ordemDeServicoModel.getNumeroContrato();
        String[] to = {"eng.linekerx@gmail.com", ordemDeServicoModel.getEmailCliente()};

        emailService.enviarEmailComAnexo(to, subject, "Segue em anexo a ordem de serviço.", pdfContent);

        return ResponseEntity.ok().build();
    }
    @GetMapping("/os")
    public ResponseEntity<List<OrdermDeServicoModel>> getAllOs() {
        List<OrdermDeServicoModel> ordensDeServico = ordemDeServicoService.buscarTodas();
        return ResponseEntity.ok(ordensDeServico);
    }
}