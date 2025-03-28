package br.com.basic.basic.ordemDeServico;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import com.itextpdf.text.Image;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

@Service
public class PdfService {

    public ByteArrayOutputStream gerarPdf(OrdermDeServicoModel ordemDeServicoModel) throws DocumentException, IOException {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, out);

        document.open();

        // Adiciona a logo
        String logoPath = "C:\\Users\\jucao\\Desktop\\Basic_backend\\src\\main\\resources\\assets\\logoBasic.jpg"; // Substitua pelo caminho da sua logo
        Image logo = Image.getInstance(logoPath);
        logo.setAlignment(Element.ALIGN_CENTER);
        logo.scaleToFit(100, 100); // Ajuste o tamanho conforme necessário
        document.add(logo);

        // Estilização de fontes
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.BLACK);
        Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);

        // Título centralizado
        Paragraph title = new Paragraph("Ordem de Serviço", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(10);
        document.add(title);

        // Adiciona os detalhes do cliente em uma tabela
        PdfPTable table = new PdfPTable(2); // 2 colunas
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        table.addCell(new PdfPCell(new Paragraph("Nome do Cliente:", titleFont)));
        table.addCell(new PdfPCell(new Paragraph(ordemDeServicoModel.getNomeCliente(), normalFont)));
        table.addCell(new PdfPCell(new Paragraph("Endereço:", titleFont)));
        table.addCell(new PdfPCell(new Paragraph(ordemDeServicoModel.getEndereco(), normalFont)));
        table.addCell(new PdfPCell(new Paragraph("Número:", titleFont)));
        table.addCell(new PdfPCell(new Paragraph(ordemDeServicoModel.getNumero(), normalFont)));
        table.addCell(new PdfPCell(new Paragraph("Cidade:", titleFont)));
        table.addCell(new PdfPCell(new Paragraph(ordemDeServicoModel.getCidade(), normalFont)));
        table.addCell(new PdfPCell(new Paragraph("Número do Contrato:", titleFont)));
        table.addCell(new PdfPCell(new Paragraph(ordemDeServicoModel.getNumeroContrato(), normalFont)));
        table.addCell(new PdfPCell(new Paragraph("Email do Cliente:", titleFont)));
        table.addCell(new PdfPCell(new Paragraph(ordemDeServicoModel.getEmailCliente(), normalFont)));
        table.addCell(new PdfPCell(new Paragraph("Nome do Atendente:", titleFont)));
        table.addCell(new PdfPCell(new Paragraph(ordemDeServicoModel.getNomeAtendente(), normalFont)));
        table.addCell(new PdfPCell(new Paragraph("Supervisor:", titleFont)));
        table.addCell(new PdfPCell(new Paragraph(ordemDeServicoModel.getSupervisor(), normalFont)));
        table.addCell(new PdfPCell(new Paragraph("Tipo de Atendimento:", titleFont)));
        table.addCell(new PdfPCell(new Paragraph(ordemDeServicoModel.getTipoAtendimento(), normalFont)));
        table.addCell(new PdfPCell(new Paragraph("Observação:", titleFont)));
        table.addCell(new PdfPCell(new Paragraph(ordemDeServicoModel.getObservacao(), normalFont)));

        document.add(table);

        // Adiciona a imagem da assinatura
        if (ordemDeServicoModel.getAssinatura() != null && !ordemDeServicoModel.getAssinatura().isEmpty()) {
            String assinaturaBase64 = ordemDeServicoModel.getAssinatura().replaceFirst("^data:image/[^;]+;base64,", "");
            byte[] assinaturaBytes = Base64.getDecoder().decode(assinaturaBase64);
            Image assinatura = Image.getInstance(assinaturaBytes);
            assinatura.setAlignment(Element.ALIGN_CENTER);
            document.add(new Paragraph("Assinatura:", titleFont));
            document.add(assinatura);
        }

        // Adiciona as fotos do atendimento
        if (ordemDeServicoModel.getFotos() != null) {
            for (String foto : ordemDeServicoModel.getFotos()) {
                if (foto.startsWith("data:image/")) {
                    String fotoBase64 = foto.replaceFirst("^data:image/[^;]+;base64,", "");
                    byte[] fotoBytes = Base64.getDecoder().decode(fotoBase64);
                    Image image = Image.getInstance(fotoBytes);
                    image.setAlignment(Element.ALIGN_CENTER);
                    document.add(new Paragraph("Foto:", titleFont));
                    document.add(image);
                } else if (foto.startsWith("file:///")) {
                    byte[] fotoBytes = Files.readAllBytes(Paths.get(foto.replaceFirst("file:///", "")));
                    Image image = Image.getInstance(fotoBytes);
                    image.setAlignment(Element.ALIGN_CENTER);
                    document.add(new Paragraph("Foto:", titleFont));
                    document.add(image);
                }
            }
        }

        document.close();
        return out;
    }
}