package br.com.basic.basic.ordemDeServico;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.mail.MessagingException;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
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
    public ResponseEntity<?> createOs(
            @RequestPart("dados") String dadosJson,
            @RequestPart(value = "fotos", required = false) List<MultipartFile> fotos
    ) throws MessagingException, DocumentException, IOException {
        // Converte o JSON recebido para o modelo esperado
        ObjectMapper objectMapper = new ObjectMapper();
        OrdermDeServicoModel ordemDeServicoModel = objectMapper.readValue(dadosJson, OrdermDeServicoModel.class);

        // Converte as fotos para Base64
        List<String> fotosBase64 = null;
        if (fotos != null) {
            fotosBase64 = fotos.stream().map(foto -> {
                try {
                    return Base64.getEncoder().encodeToString(foto.getBytes());
                } catch (IOException e) {
                    throw new RuntimeException("Erro ao converter foto para Base64", e);
                }
            }).toList();
        }

        // Gera o PDF
        ByteArrayOutputStream pdfContent = pdfService.gerarPdf(ordemDeServicoModel, fotosBase64);

        // Envia o e-mail com o PDF em anexo
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