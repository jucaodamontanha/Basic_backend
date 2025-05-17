package br.com.basic.basic.ordemDeServico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarEmailComAnexo(String[] to, String subject, ByteArrayOutputStream pdfContent) throws MessagingException, IOException {
        // Lê o HTML do template corretamente, compatível com JAR
        String html = lerHtmlTemplate("templates/email-template.html");

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(html, true); // true = envia como HTML

        // Adiciona imagem inline (logo)
        ClassPathResource logo = new ClassPathResource("imagens/logo.png");
        helper.addInline("logoBasicTelecom", logo);

        // Adiciona PDF como anexo
        helper.addAttachment("ordem_de_servico.pdf", new ByteArrayResource(pdfContent.toByteArray()));

        mailSender.send(message);
    }

    private String lerHtmlTemplate(String path) throws IOException {
        ClassPathResource resource = new ClassPathResource(path);
        try (var inputStream = resource.getInputStream()) {
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        }
    }
}
