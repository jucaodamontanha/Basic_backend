package br.com.basic.basic.ordemDeServico;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import com.itextpdf.text.Image;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List; // Certifique-se de usar esta importação para listas genéricas

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

@Service
public class PdfService {

    public ByteArrayOutputStream gerarPdf(OrdermDeServicoModel ordemDeServicoModel, List<String> fotosBase64) throws DocumentException, IOException {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, out);

        document.open();

        // Adiciona a logo
        try {
            String logoPath = "https://github.com/jucaodamontanha/Basic_backend/blob/28032d12b58378c70613703cbee7bbcbd818c841/src/main/resources/assets/logoBasic.jpg"; // Substitua pelo caminho da sua logo
            Image logo = Image.getInstance(logoPath);
            logo.setAlignment(Element.ALIGN_CENTER);
            logo.scaleToFit(100, 100); // Ajusta o tamanho conforme necessário
            document.add(logo);
        } catch (Exception e) {
            System.err.println("Erro ao carregar a logo: " + e.getMessage());
        }

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

        // Adiciona a assinatura
        if (ordemDeServicoModel.getAssinatura() != null && !ordemDeServicoModel.getAssinatura().isEmpty()) {
            try {
                String assinaturaBase64 = ordemDeServicoModel.getAssinatura().replaceFirst("^data:image/[^;]+;base64,", "");
                byte[] assinaturaBytes = Base64.getDecoder().decode(assinaturaBase64);
                Image assinatura = Image.getInstance(assinaturaBytes);
                assinatura.setAlignment(Element.ALIGN_CENTER);
                assinatura.scaleToFit(200, 100); // Ajusta o tamanho da assinatura
                document.add(new Paragraph("Assinatura:", titleFont));
                document.add(assinatura);
            } catch (Exception e) {
                System.err.println("Erro ao processar a assinatura: " + e.getMessage());
            }
        }

        // Adiciona as fotos do atendimento
        if (fotosBase64 != null) {
            for (String fotoBase64 : fotosBase64) {
                try {
                    String fotoData = fotoBase64.replaceFirst("^data:image/[^;]+;base64,", "");
                    byte[] fotoBytes = Base64.getDecoder().decode(fotoData);
                    Image foto = Image.getInstance(fotoBytes);
                    foto.setAlignment(Element.ALIGN_CENTER);
                    foto.scaleToFit(300, 200); // Ajusta o tamanho da foto
                    document.add(new Paragraph("Foto:", titleFont));
                    document.add(foto);
                } catch (Exception e) {
                    System.err.println("Erro ao processar a foto: " + e.getMessage());
                }
            }
        }

        document.close();
        return out;
    }

}