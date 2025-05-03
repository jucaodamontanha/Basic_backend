package br.com.basic.basic.ordemDeServico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarEmailComAnexo(String[] to, String subject, ByteArrayOutputStream pdfContent) throws MessagingException, IOException {
        // Lê o HTML do template
        String html = lerHtmlTemplate("templates/email-template.html");

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(html, true); // true para HTML

        // Logo incorporada via Content-ID (cid)
        ClassPathResource logo = new ClassPathResource("static/images/logo.png"); // ou onde você salvar
        helper.addInline("logoBasicTelecom", logo);

        // Anexo do PDF
        helper.addAttachment("ordem_de_servico.pdf", new ByteArrayResource(pdfContent.toByteArray()));

        mailSender.send(message);
    }

    private String lerHtmlTemplate(String path) throws IOException {
        ClassPathResource resource = new ClassPathResource(path);
        byte[] bytes = Files.readAllBytes(resource.getFile().toPath());
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
