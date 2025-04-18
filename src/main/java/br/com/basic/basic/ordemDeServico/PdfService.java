package br.com.basic.basic.ordemDeServico;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;

@Service
public class PdfService {

    public ByteArrayOutputStream gerarPdf(OrdermDeServicoModel ordem, List<String> fotosBase64) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4, 36, 36, 36, 36);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, out);
        document.open();

        // Fundo azul elegante
        Rectangle pageSize = document.getPageSize();
        PdfContentByte canvas = writer.getDirectContentUnder();
        canvas.setColorFill(new BaseColor(200, 220, 240));
        canvas.rectangle(0, 0, pageSize.getWidth(), pageSize.getHeight());
        canvas.fill();

        // Logo centralizada
        try (InputStream logoStream = getClass().getResourceAsStream("/assets/BASIC LOGO.png")) {
            if (logoStream != null) {
                Image logo = Image.getInstance(IOUtils.toByteArray(logoStream));
                logo.setAlignment(Element.ALIGN_CENTER);
                logo.scaleToFit(180, 90);
                document.add(logo);
            }
        } catch (Exception e) {
            System.err.println("Erro ao carregar logo: " + e.getMessage());
        }

        // Título
        Font titleFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD, BaseColor.BLACK);
        Paragraph title = new Paragraph("ORDEM DE SERVIÇO", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingBefore(10);
        title.setSpacingAfter(10);
        document.add(title);

        // Moldura branca com bordas arredondadas
        PdfPTable dadosBox = new PdfPTable(2);
        dadosBox.setWidthPercentage(100);
        dadosBox.setSpacingBefore(10f);
        dadosBox.setSpacingAfter(15f);
        dadosBox.getDefaultCell().setBorder(Rectangle.NO_BORDER);

        Font labelFont = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD);
        Font valueFont = new Font(Font.FontFamily.HELVETICA, 11);

        addCell("Nome do Cliente:", ordem.getNomeCliente(), dadosBox, labelFont, valueFont);
        addCell("Endereço:", ordem.getEndereco(), dadosBox, labelFont, valueFont);
        addCell("Número:", ordem.getNumero(), dadosBox, labelFont, valueFont);
        addCell("Cidade:", ordem.getCidade(), dadosBox, labelFont, valueFont);
        addCell("Número do Contrato:", ordem.getNumeroContrato(), dadosBox, labelFont, valueFont);
        addCell("Email do Cliente:", ordem.getEmailCliente(), dadosBox, labelFont, valueFont);
        addCell("Nome do Atendente:", ordem.getNomeAtendente(), dadosBox, labelFont, valueFont);
        addCell("Supervisor:", ordem.getSupervisor(), dadosBox, labelFont, valueFont);
        addCell("Tipo de Atendimento:", ordem.getTipoAtendimento(), dadosBox, labelFont, valueFont);
        addCell("Observação:", ordem.getObservacao(), dadosBox, labelFont, valueFont);

        PdfPCell boxContainer = new PdfPCell(dadosBox);
        boxContainer.setPadding(12f);
        boxContainer.setBorder(Rectangle.BOX);
        boxContainer.setBorderColor(BaseColor.LIGHT_GRAY);
        boxContainer.setBackgroundColor(BaseColor.WHITE);
        boxContainer.setColspan(2);
        document.add(boxContainer);

        // Assinatura
        if (ordem.getAssinatura() != null && !ordem.getAssinatura().isEmpty()) {
            Paragraph assinaturaTitle = new Paragraph("Assinatura:", labelFont);
            assinaturaTitle.setSpacingBefore(10f);
            document.add(assinaturaTitle);

            byte[] assinaturaBytes = Base64.getDecoder().decode(ordem.getAssinatura().replaceFirst("^data:image/[^;]+;base64,", ""));
            Image assinatura = Image.getInstance(assinaturaBytes);
            assinatura.scaleToFit(200, 100);

            PdfPCell assinaturaCell = new PdfPCell(assinatura);
            assinaturaCell.setBorder(Rectangle.BOX);
            assinaturaCell.setBorderColor(BaseColor.GRAY);
            assinaturaCell.setPadding(10f);
            assinaturaCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            PdfPTable assinaturaTable = new PdfPTable(1);
            assinaturaTable.setWidthPercentage(100);
            assinaturaTable.setSpacingBefore(10f);
            assinaturaTable.addCell(assinaturaCell);
            document.add(assinaturaTable);
        }

        // Fotos
        if (fotosBase64 != null && !fotosBase64.isEmpty()) {
            Paragraph fotosTitle = new Paragraph("Fotos do Atendimento:", labelFont);
            fotosTitle.setSpacingBefore(15f);
            document.add(fotosTitle);

            PdfPTable fotosTable = new PdfPTable(2);
            fotosTable.setWidthPercentage(100);
            fotosTable.setSpacingBefore(10f);

            int count = 0;
            for (String base64 : fotosBase64) {
                try {
                    byte[] fotoBytes = Base64.getDecoder().decode(base64.replaceFirst("^data:image/[^;]+;base64,", ""));
                    Image foto = Image.getInstance(fotoBytes);
                    foto.scaleToFit(250, 180);

                    PdfPCell fotoCell = new PdfPCell(foto);
                    fotoCell.setPadding(6f);
                    fotoCell.setBorder(Rectangle.BOX);
                    fotoCell.setBorderColor(BaseColor.GRAY);
                    fotosTable.addCell(fotoCell);

                    count++;
                    if (count % 6 == 0) {
                        document.add(fotosTable);
                        document.newPage();
                        fotosTable = new PdfPTable(2);
                        fotosTable.setWidthPercentage(100);
                        fotosTable.setSpacingBefore(10f);
                    }

                } catch (Exception e) {
                    System.err.println("Erro ao processar imagem: " + e.getMessage());
                }
            }

            if (count % 6 != 0) {
                document.add(fotosTable);
            }
        }

        document.close();
        return out;
    }

    private void addCell(String label, String value, PdfPTable table, Font labelFont, Font valueFont) {
        PdfPCell labelCell = new PdfPCell(new Phrase(label, labelFont));
        labelCell.setBorder(Rectangle.NO_BORDER);
        table.addCell(labelCell);

        PdfPCell valueCell = new PdfPCell(new Phrase(value != null ? value : "-", valueFont));
        valueCell.setBorder(Rectangle.NO_BORDER);
        table.addCell(valueCell);
    }
}
