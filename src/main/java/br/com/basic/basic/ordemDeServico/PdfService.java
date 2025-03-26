package br.com.basic.basic.ordemDeServico;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

@Service
public class PdfService {

    public ByteArrayOutputStream gerarPdf(OrdermDeServicoModel ordemDeServicoModel) throws DocumentException, IOException {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, out);

        document.open();
        document.add(new Paragraph("Ordem de Serviço"));
        document.add(new Paragraph("Nome do Cliente: " + ordemDeServicoModel.getNomeCliente()));
        document.add(new Paragraph("Endereço: " + ordemDeServicoModel.getEndereco()));
        document.add(new Paragraph("Número: " + ordemDeServicoModel.getNumero()));
        document.add(new Paragraph("Cidade: " + ordemDeServicoModel.getCidade()));
        document.add(new Paragraph("Número do Contrato: " + ordemDeServicoModel.getNumeroContrato()));
        document.add(new Paragraph("Email do Cliente: " + ordemDeServicoModel.getEmailCliente()));
        document.add(new Paragraph("Nome do Atendente: " + ordemDeServicoModel.getNomeAtendente()));
        document.add(new Paragraph("Supervisor: " + ordemDeServicoModel.getSupervisor()));
        document.add(new Paragraph("Tipo de Atendimento: " + ordemDeServicoModel.getTipoAtendimento()));
        document.add(new Paragraph("Observação: " + ordemDeServicoModel.getObservacao()));

        // Adiciona a imagem da assinatura
        if (ordemDeServicoModel.getAssinatura() != null && !ordemDeServicoModel.getAssinatura().isEmpty()) {
            String assinaturaBase64 = ordemDeServicoModel.getAssinatura().replaceFirst("^data:image/[^;]+;base64,", "");
            byte[] assinaturaBytes = Base64.getDecoder().decode(assinaturaBase64);
            Image assinatura = Image.getInstance(assinaturaBytes);
            document.add(new Paragraph("Assinatura:"));
            document.add(assinatura);
        }

        // Adiciona as fotos do atendimento
        if (ordemDeServicoModel.getFotos() != null) {
            for (String foto : ordemDeServicoModel.getFotos()) {
                if (foto.startsWith("data:image/")) {
                    String fotoBase64 = foto.replaceFirst("^data:image/[^;]+;base64,", "");
                    byte[] fotoBytes = Base64.getDecoder().decode(fotoBase64);
                    Image image = Image.getInstance(fotoBytes);
                    document.add(new Paragraph("Foto:"));
                    document.add(image);
                } else if (foto.startsWith("file:///")) {
                    byte[] fotoBytes = Files.readAllBytes(Paths.get(foto.replaceFirst("file:///", "")));
                    Image image = Image.getInstance(fotoBytes);
                    document.add(new Paragraph("Foto:"));
                    document.add(image);
                }
            }
        }

        document.close();
        return out;
    }
}