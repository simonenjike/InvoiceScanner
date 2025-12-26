package sim.refpro.invoicescanner.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sim.refpro.invoicescanner.dto.InvoiceScanRequest;
import sim.refpro.invoicescanner.dto.InvoiceScanResponse;
import sim.refpro.invoicescanner.service.InvoiceScanService;

/**
 * <h2>Invoice Scan Controller</h2>
 * 
 * <p>
 * Provides an endpoint that downloads a PDF invoice from a given URL, extracts text, searches for 
 * IBANs, and checks them against the blacklist.
 * </p>
 * 
 *  <p>
 * HTTP behavior:<br>
 * – 200 OK --> The scan was successfully executed.<br>
 * – 400 Bad Request --> Validation error or invalid input.<br>
 * – 500 Internal Server Error --> Unexpected server-side error.<br>
 * </p>
 * 
 * @author Simone Njike
 * @version 1.1
 * @since 02.12.2025
 */
@RestController
@RequestMapping("/api/v1/invoice-scan")
public class InvoiceScanController {
    
    private final InvoiceScanService invoiceScanService;

    /**
     * Injects all components used by the controller.
     * 
     * @param invoiceScanService service for URL-based scanning.
     */
    public InvoiceScanController(InvoiceScanService invoiceScanService) {
        
        this.invoiceScanService = invoiceScanService;
    }
    
    /**
     * Scans a PDF from a public URL.
     * 
     * @param request The request containing the URL of the invoice PDF.
     * @return A JSON response containing the scan result.<br>
     * 
     * <b>Example Request (JSON):</b>
     * <pre>
     * {@code
     *   "invoiceUrl": "https://example.org/invoices/sample.pdf"
     * }
     * </pre>
     */    
   
    @PostMapping
    public ResponseEntity<InvoiceScanResponse> scanInvoice (
                                                                            @Valid @RequestBody InvoiceScanRequest request) {
        // Delegate to service layer
        InvoiceScanResponse response = invoiceScanService.scanInvoiceFromUrl(request);         
        return ResponseEntity.ok(response); // Always return 200 on success
    }
    
    @GetMapping("/ping")
    public String ping() {
        
        return "Invoice Scanner is up";
    }
    
}