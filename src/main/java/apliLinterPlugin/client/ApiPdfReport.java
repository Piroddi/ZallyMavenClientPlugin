package apliLinterPlugin.client;


import apliLinterPlugin.client.Entities.Response.ApiResponseEntity;
import apliLinterPlugin.client.Entities.Response.Violation;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import com.sun.javafx.iio.ImageLoaderFactory;


import javax.inject.Named;
import javax.inject.Singleton;
import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;



import java.util.stream.Stream;

@Named
@Singleton
public class ApiPdfReport {

    public void generatePDF(ApiResponseEntity apiResponseEntity, File fileLocation, String name) throws IOException, DocumentException {

        Document document = new Document();

        File file = new File(fileLocation +"/"+name+".pdf");
        file.getParentFile().mkdirs();
        PdfWriter.getInstance(document, new FileOutputStream(file));


        //Open Document
        document.open();

        //Header
        Font font = FontFactory.getFont(FontFactory.TIMES, 18, BaseColor.BLACK);
        Paragraph p = new Paragraph(name, font);
        p.setSpacingAfter(10);
        p.setAlignment(Element.ALIGN_CENTER);
        document.add(p);

        //Summary Table
        PdfPTable summaryTable = new PdfPTable(2);

        addTableHeader(summaryTable);
        addRows(summaryTable, apiResponseEntity );
        document.add(summaryTable);

        //Details
        Font detailsFont = FontFactory.getFont(FontFactory.TIMES, 8, BaseColor.BLACK);
        Paragraph details = new Paragraph("Details:", detailsFont);
        details.setSpacingAfter(5);

        document.add(details);

        //Detailed Table
        PdfPTable detailedTable = new PdfPTable(6);
        detailedTable.setHorizontalAlignment(Element.ALIGN_LEFT);
        addDetailedTableHeader(detailedTable);
        addDetailedRows(detailedTable, apiResponseEntity);
        document.add(detailedTable);

        document.close();
    }

    private void addTableHeader(PdfPTable table) {

        Font font = FontFactory.getFont(FontFactory.TIMES, 9, BaseColor.BLACK);
        Stream.of("Summary", "No. of violation")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();

                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(1);
                    header.setPhrase(new Phrase(columnTitle , font));
                    table.setWidthPercentage(20);
                    table.addCell(header);
                });
    }

    private void addDetailedTableHeader(PdfPTable table) {

        Font font = FontFactory.getFont(FontFactory.TIMES, 6, BaseColor.BLACK);

        Stream.of("Violation #", "Violation Type", "Violation Title", "Violation Description", "Violation Rule", "Violation Path")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(1);
                    header.setPhrase(new Phrase(columnTitle,font));
                    table.addCell(header);
                });
    }

    private void addRows(PdfPTable table , ApiResponseEntity apiResponseEntity ) {

        Font fontRed = FontFactory.getFont(FontFactory.TIMES, 6, BaseColor.RED);
        Font fontOrange = FontFactory.getFont(FontFactory.TIMES, 6, BaseColor.ORANGE);
        Font fontMagenta = FontFactory.getFont(FontFactory.TIMES, 6, BaseColor.MAGENTA);
        Font fontGreen = FontFactory.getFont(FontFactory.TIMES, 6, BaseColor.GREEN);

        table.addCell(new PdfPCell(new Phrase("MUST",fontRed)));
        table.addCell(new PdfPCell(new Phrase(apiResponseEntity.getViolationsCount().getMust().toString(),fontRed)));
        table.addCell(new PdfPCell(new Phrase("SHOULD",fontMagenta)));
        table.addCell(new PdfPCell(new Phrase(apiResponseEntity.getViolationsCount().getShould().toString(),fontMagenta)));
        table.addCell(new PdfPCell(new Phrase("MAY",fontOrange)));
        table.addCell(new PdfPCell(new Phrase(apiResponseEntity.getViolationsCount().getMay().toString(),fontOrange)));
        table.addCell(new PdfPCell(new Phrase("HINT",fontGreen)));
        table.addCell(new PdfPCell(new Phrase(apiResponseEntity.getViolationsCount().getHint().toString(),fontGreen)));

    }

    private void addDetailedRows(PdfPTable table, ApiResponseEntity apiResponseEntity){


        Font font = FontFactory.getFont(FontFactory.TIMES, 6, BaseColor.BLACK);
        Font fontLink = FontFactory.getFont(FontFactory.TIMES_ITALIC, 6, BaseColor.BLUE);


        int i = 1;
        for (Violation violation: apiResponseEntity.getViolations()){
            table.addCell(new PdfPCell(new Phrase(String.valueOf(i++),font)));
            table.addCell(new PdfPCell(new Phrase(violation.getViolationType(),font)));
            table.addCell(new PdfPCell(new Phrase(violation.getTitle(),font)));
            table.addCell(new PdfPCell(new Phrase(violation.getDescription(),font)));
            Anchor anchor = new Anchor("Rule",fontLink);

            anchor.setReference(violation.getRuleLink());
            table.addCell(anchor);
            table.addCell(new PdfPCell(new Phrase(violation.getPaths(),font)));
        }

    }
}
