package sim.refpro.invoicescanner.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import sim.refpro.invoicescanner.dto.InvoiceScanRequest;
import sim.refpro.invoicescanner.dto.InvoiceScanResponse;
import sim.refpro.invoicescanner.repository.BlacklistRepository;
import sim.refpro.invoicescanner.service.domain.IbanScanner;
import sim.refpro.invoicescanner.service.infra.HttpDownloader;
import sim.refpro.invoicescanner.service.infra.PdfTextExtractor;

/**
 * <h2>Invoice Scan Service Implementation</h2>
 * 
 * Default implementation of {@link InvoiceScanService}.
 * This service orchestrates the invoice scanning workflow:
 * <ol>
 *     <li>Download the invoice PDF from the provided URL.</li>
 *     <li>Extract text content from the PDF.</li>
 *     <li>Scan the text for IBANs.</li>
 *     <li>Check found IBANs against the blacklist.</li>
 *     <li>Return a structured scan result.</li>
 * </ol>
 *
 * @author Simone Njike
 * @version 1.1
 * @since 30.11.2025
 */
@Service
public class InvoiceScanServiceImpl implements InvoiceScanService {
    
    private final HttpDownloader httpDownloader;
    private final PdfTextExtractor pdfTextExtractor;
    private final IbanScanner ibanScanner;
    private final BlacklistRepository blacklistRepository;

    /**
     * Creates a new instance of the invoice scan service with all required collaborators.
     *
     * @param httpDownloader      Component used to download the invoice PDF.
     * @param pdfTextExtractor     Component used to extract text from the downloaded PDF.
     * @param ibanScanner           Component used to detect and validate IBANs inside the text.
     * @param blacklistRepository  Repository used to check IBANs against a blacklist.
     */
    public InvoiceScanServiceImpl(HttpDownloader httpDownloader, PdfTextExtractor pdfTextExtractor, 
                                                        IbanScanner ibanScanner, BlacklistRepository blacklistRepository) {
        this.httpDownloader = httpDownloader;
        this.pdfTextExtractor = pdfTextExtractor;
        this.ibanScanner = ibanScanner;
        this.blacklistRepository = blacklistRepository;
    }

    /**
     * Executes the full invoice scanning workflow for the given request.
     *
     * @param request The request containing the URL of the invoice PDF.
     * @return A response containing all extracted IBANs, blacklist matches and
     *         additional status information.
     *
     * @throws IllegalArgumentException if the request is {@code null}.
     * @throws IllegalStateException    if any technical step fails (download, PDF parsing, etc.).
     */
    @Override
    public InvoiceScanResponse scanInvoiceFromUrl(InvoiceScanRequest request) {
        
        if (request == null) {
            throw new IllegalArgumentException("InvoiceScanRequest must not be null");
        }

        // 1) Download PDF
        byte[ ] pdfBytes = httpDownloader.download(request.getInvoiceUrl());

        // 2) Extract text from PDF
        String extractedText = pdfTextExtractor.extractText(pdfBytes);

        // 3) Scan text for IBANs
        List<String> extractedIbans = ibanScanner.findIbans(extractedText);

        // 4) Check IBANs against blacklist
        List<String> blacklistedIbans = extractedIbans.stream().filter(blacklistRepository::isBlacklisted)
                                                            .collect(Collectors.toList());
        // 5) Build response
        InvoiceScanResponse response = new InvoiceScanResponse();
        response.setExtractedIbans(extractedIbans);
        response.setBlacklistedIbans(blacklistedIbans);
        response.setSuccess(true);
        response.setMessage(buildMessage(extractedIbans, blacklistedIbans));

        return response;
    }

    /**
     * Builds a human-readable message based on the scan result.
     * 
     * @param extractedIbans Alle IBANs, die im Text der PDF gefunden wurden.
     * @param blacklistedIbans Alle IBANs, die in der Blacklist-Tabelle stehen.
     * @return 
     */
    private String buildMessage(List<String> extractedIbans, List<String> blacklistedIbans) {
        
        if (extractedIbans.isEmpty()) {
            return "No IBANs were found in the invoice.";
        }
        
        if (blacklistedIbans.isEmpty()) {
            return "IBANs were found but none of them are blacklisted.";
        }
        
        return "Blacklisted IBANs were detected in the invoice.";
    }
  
}