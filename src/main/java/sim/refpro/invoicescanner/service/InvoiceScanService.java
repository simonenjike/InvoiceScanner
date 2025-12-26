package sim.refpro.invoicescanner.service;

import sim.refpro.invoicescanner.dto.InvoiceScanRequest;
import sim.refpro.invoicescanner.dto.InvoiceScanResponse;

/**
 * <h2>Invoice Scan Service</h2>
 * 
 * <p>
 * Service interface for scanning invoice PDF documents.
 * </p>
 * 
 * Implementations of this interface are responsible for:<br>
 * – Downloading the PDF from the given URL.<br>
 * – Extracting raw text from the PDF.<br>
 * – Searching the extracted text for IBANs.<br>
 * – Checking found IBANs against a blacklist.<br>
 * – Returning a structured scan result.
 * 
 * @author Simone Njike
 * @version 1.1
 * @since 30.11.2025
 */
public interface InvoiceScanService {

    /**
     * Executes the invoice scanning workflow based on the provided request.
     * 
     * @param request The request containing the invoice PDF URL.
     * @return A response containing extracted IBANs, blacklist matches and additional scan metadata.
     */
    
    InvoiceScanResponse scanInvoiceFromUrl(InvoiceScanRequest request);
    
}