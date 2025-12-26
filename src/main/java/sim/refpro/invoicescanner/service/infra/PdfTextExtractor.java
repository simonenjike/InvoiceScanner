package sim.refpro.invoicescanner.service.infra;

import java.io.IOException;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Component;

/**
 * <h2>PDF Text Extractor</h2>
 * 
 * <p>
 * Component responsible for extracting plain text from PDF documents. Uses <b>Apache PDFBox</b> 
 * to parse the PDF structure and strip text content. This component assumes that the PDF contains a text 
 * layer (no OCR).
 * </p>
 *
 * @author Simone Njike
 * @version 1.1
 * @since 01.12.2025
 */
@Component
public class PdfTextExtractor {

    /**
     * Extracts all text from the given PDF bytes.
     * 
     * @param pdfBytes The raw bytes of the PDF document. Must not be null or empty.
     * @return The extracted text as a single String (may be empty, but never null).
     * @throws IllegalArgumentException if the input is null or empty.
     * @throws IllegalStateException if the PDF cannot be parsed or text extraction fails.
     */
    public String extractText(byte[ ] pdfBytes) {

        if (pdfBytes == null || pdfBytes.length == 0) {
            throw new IllegalArgumentException("PDF bytes must not be null or empty");
        }

        try (PDDocument document = Loader.loadPDF(pdfBytes)) {

            if (document.isEncrypted()) {
                // In this simple implementation we do not support encrypted PDFs
                throw new IllegalStateException("Cannot extract text from encrypted PDF document");
            }            
            
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);
            
            return text != null ? text : "";
        } 
        catch (IOException ex) {
            throw new RuntimeException("Failed to extract text from PDF document", ex);
        }
    }    
    
}