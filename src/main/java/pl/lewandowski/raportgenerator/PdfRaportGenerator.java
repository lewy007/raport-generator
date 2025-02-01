package pl.lewandowski.raportgenerator;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class PdfRaportGenerator {

    @Value("${pdf.cell.headers}")
    private List<String> tableHeaders;

    private final UserRepository userRepository;

    public PdfRaportGenerator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void generatePdf() throws IOException {
        Document document = new Document();
        try {

            PdfWriter.getInstance(document, new FileOutputStream("Raport.pdf"));

            document.open();

            //HEADER
            Paragraph header = new Paragraph();
            Font font = new Font(Font.FontFamily.HELVETICA, 20, Font.BOLDITALIC);
            header.add(new Paragraph("List of chat users", font));
            header.setAlignment(Element.ALIGN_TOP);
            document.add(header);

            //IMAGE
            Image logo = Image.getInstance("src/main/resources/static/images/javaicon.png");
            logo.scalePercent(20, 20);
            logo.setAlignment(Element.ALIGN_RIGHT);
            document.add(logo);

            //TABLE
            Paragraph table = new Paragraph();

            PdfPTable pdfPTable = new PdfPTable(4);

            //wyciagamy nazwy kolumn z listy
            for (int i = 0; i < 4; i++) {
                PdfPCell pdfPCell = new PdfPCell(new Phrase(tableHeaders.get(i)));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setBackgroundColor(BaseColor.RED);
                table.add(pdfPTable);
            }

            //chcemy jeden wiersz elementow
            pdfPTable.setHeaderRows(1);

            //wyciagamy wszytskich usrów z bazy danych i dodajemy do tabeli
            userRepository.findAll().forEach(user -> {
                pdfPTable.addCell(user.getId().toString());
                pdfPTable.addCell(user.getUsername());
                pdfPTable.addCell(user.getUserSalary());
                pdfPTable.addCell(user.getUserDepartment());
            });

            document.add(table);


            //przed wygenerowaniem raportu trzeba zamknac dokument
            document.close();


        } catch (DocumentException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @EventListener(ApplicationReadyEvent.class)
    void starter() throws IOException {
        generatePdf();

    }
}
