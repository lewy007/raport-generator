package pl.lewandowski.raportgenerator;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
public class ReportController {


    //do przechowywania pdfa
    private ByteArrayOutputStream byteArrayOutputStream;

    private final PdfReportService pdfReportService;

    public ReportController(PdfReportService pdfReportService) {
        this.pdfReportService = pdfReportService;
    }

    @GetMapping("/add-user")
    public void addUser() {
        User user = new User(6L, "Jan Kowalski", "5000", "Spring Cloud");
        pdfReportService.addUser(user);
    }

    @GetMapping("/generate-pdf")
    public ResponseEntity<byte[]> generateReport() throws DocumentException, IOException {

        //tworzymy strumien wyjsciowy do przechowywania danych
        byteArrayOutputStream = new ByteArrayOutputStream();
        Document document = new Document();
        //zapisujemy dokument w strumieniu wyjsciowym
        PdfWriter.getInstance(document, byteArrayOutputStream);


        document.open();
        pdfReportService.addHeader(document);
        pdfReportService.addImage(document);
        pdfReportService.createTable(document);
        pdfReportService.addFooter(document);
        document.close();

        //tworzymy naglowki dla odpowiedzi
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("filename", "Report.pdf");


        return new ResponseEntity<>(byteArrayOutputStream.toByteArray(), headers, HttpStatus.OK);
    }
}
