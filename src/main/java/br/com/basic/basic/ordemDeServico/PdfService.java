package br.com.basic.basic.ordemDeServico;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
public class PdfService {

    public ByteArrayOutputStream gerarPdf(OrdermDeServicoModel ordemDeServicoModel, List<String> fotosBase64) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4, 36, 36, 36, 36);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, out);
        document.open();

        // Fundo da página
        Rectangle pageSize = document.getPageSize();
        PdfContentByte canvas = writer.getDirectContentUnder();
        canvas.setColorFill(new BaseColor(220, 235, 250)); // azul suave
        canvas.rectangle(0, 0, pageSize.getWidth(), pageSize.getHeight());
        canvas.fill();

        // Data da OS
        Font dateFont = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.DARK_GRAY);
        Paragraph dataParagraph = new Paragraph("Data: " + new SimpleDateFormat("dd/MM/yyyy").format(new Date()), dateFont);
        dataParagraph.setAlignment(Element.ALIGN_RIGHT);
        document.add(dataParagraph);

        // Logo centralizado
        try {
            InputStream logoStream = getClass().getResourceAsStream("/assets/logoBasic.png");
            if (logoStream != null) {
                byte[] logoBytes = IOUtils.toByteArray(logoStream);
                Image logo = Image.getInstance(logoBytes);
                logo.setAlignment(Element.ALIGN_CENTER);
                logo.scaleToFit(180, 90);
                document.add(logo);
            }
        } catch (Exception e) {
            System.err.println("Erro ao carregar a logo: " + e.getMessage());
        }

        // Título
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.BLACK);
        Paragraph title = new Paragraph("ORDEM DE SERVIÇO", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(15);
        document.add(title);

        // Moldura com dados
        PdfPTable borderTable = new PdfPTable(1);
        borderTable.setWidthPercentage(100);
        PdfPCell borderedCell = new PdfPCell();
        borderedCell.setPadding(10);
        borderedCell.setBorder(Rectangle.BOX);
        borderedCell.setBorderColor(BaseColor.LIGHT_GRAY);
        borderedCell.setBackgroundColor(BaseColor.WHITE);

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        Font labelFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11, BaseColor.BLACK);
        Font valueFont = FontFactory.getFont(FontFactory.HELVETICA, 11, BaseColor.BLACK);

        addCell("Nome do Cliente:", ordemDeServicoModel.getNomeCliente(), table, labelFont, valueFont);
        addCell("Endereço:", ordemDeServicoModel.getEndereco(), table, labelFont, valueFont);
        addCell("Número:", ordemDeServicoModel.getNumero(), table, labelFont, valueFont);
        addCell("Cidade:", ordemDeServicoModel.getCidade(), table, labelFont, valueFont);
        addCell("Número do Contrato:", ordemDeServicoModel.getNumeroContrato(), table, labelFont, valueFont);
        addCell("Email do Cliente:", ordemDeServicoModel.getEmailCliente(), table, labelFont, valueFont);
        addCell("Nome do Atendente:", ordemDeServicoModel.getNomeAtendente(), table, labelFont, valueFont);
        addCell("Supervisor:", ordemDeServicoModel.getSupervisor(), table, labelFont, valueFont);
        addCell("Tipo de Atendimento:", ordemDeServicoModel.getTipoAtendimento(), table, labelFont, valueFont);
        addCell("Observação:", ordemDeServicoModel.getObservacao(), table, labelFont, valueFont);

        borderedCell.addElement(table);
        borderTable.addCell(borderedCell);
        document.add(borderTable);

        // Assinatura
        if (ordemDeServicoModel.getAssinatura() != null && !ordemDeServicoModel.getAssinatura().isEmpty()) {
            Paragraph assinaturaTitle = new Paragraph("Assinatura:", labelFont);
            assinaturaTitle.setSpacingBefore(15f);
            document.add(assinaturaTitle);

            String assinaturaBase64 = ordemDeServicoModel.getAssinatura().replaceFirst("^data:image/[^;]+;base64,", "");
            byte[] assinaturaBytes = Base64.getDecoder().decode(assinaturaBase64);
            Image assinatura = Image.getInstance(assinaturaBytes);
            assinatura.scaleToFit(180, 90);

            PdfPTable assinaturaTable = new PdfPTable(1);
            assinaturaTable.setWidthPercentage(50);
            PdfPCell assinaturaCell = new PdfPCell(assinatura);
            assinaturaCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            assinaturaCell.setPadding(10);
            assinaturaCell.setBorder(Rectangle.BOX);
            assinaturaCell.setBorderColor(BaseColor.LIGHT_GRAY);
            assinaturaCell.setBackgroundColor(BaseColor.WHITE);
            assinaturaTable.addCell(assinaturaCell);
            assinaturaTable.setHorizontalAlignment(Element.ALIGN_CENTER);
            document.add(assinaturaTable);
        }

        // Fotos
        if (fotosBase64 != null && !fotosBase64.isEmpty()) {
            Paragraph fotosTitle = new Paragraph("Fotos do Atendimento:", labelFont);
            fotosTitle.setSpacingBefore(20f);
            document.add(fotosTitle);

            int fotosPorLinha = 2;
            int count = 0;
            PdfPTable fotosTable = new PdfPTable(fotosPorLinha);
            fotosTable.setWidthPercentage(100);
            fotosTable.setSpacingBefore(10f);

            for (String fotoBase64 : fotosBase64) {
                try {
                    String fotoData = fotoBase64.replaceFirst("^data:image/[^;]+;base64,", "");
                    byte[] fotoBytes = Base64.getDecoder().decode(fotoData);
                    Image foto = Image.getInstance(fotoBytes);
                    foto.scaleToFit(250, 180);
                    PdfPCell cell = new PdfPCell(foto);
                    cell.setBorder(Rectangle.BOX);
                    cell.setBorderColor(BaseColor.LIGHT_GRAY);
                    cell.setPadding(5f);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    fotosTable.addCell(cell);
                    count++;

                    if (count % 4 == 0) {
                        document.add(fotosTable);
                        document.newPage();
                        fotosTable = new PdfPTable(fotosPorLinha);
                        fotosTable.setWidthPercentage(100);
                        fotosTable.setSpacingBefore(10f);
                    }
                } catch (Exception e) {
                    System.err.println("Erro ao processar foto: " + e.getMessage());
                }
            }

            if (count % 4 != 0) {
                document.add(fotosTable);
            }
        }

        // Rodapé
        Paragraph rodape = new Paragraph("Lineker Tecnologia - eng.linekerx@gmail.com", dateFont);
        rodape.setAlignment(Element.ALIGN_CENTER);
        rodape.setSpacingBefore(30f);
        document.add(rodape);

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
