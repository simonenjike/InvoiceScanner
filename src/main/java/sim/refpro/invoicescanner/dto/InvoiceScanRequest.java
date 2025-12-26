package sim.refpro.invoicescanner.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * <h2>Invoice Scan Request</h2>
 * 
 * <p>
 * Request DTO for triggering an invoice scan. Contains the URL of a PDF invoice that should be 
 * downloaded and scanned.
 *</p>
 * 
 * @author Simone Njike
 * @version 1.1
 * @since 30.11.2025
 */
public class InvoiceScanRequest {
    
    /**
     * URL of the invoice PDF to scan. 
     */
    @NotBlank(message = "invoiceUrl must not be empty")
    private String invoiceUrl;

    public InvoiceScanRequest() { }

    public InvoiceScanRequest(String invoiceUrl) {        
        
        this.invoiceUrl = invoiceUrl;
    }

    public String getInvoiceUrl() {      
        return invoiceUrl;
    }

    public void setInvoiceUrl(String invoiceUrl) {        
        this.invoiceUrl = invoiceUrl;
    }

    @Override
    public String toString() {

        return "InvoiceScanRequest{" + "invoiceUrl=' " + invoiceUrl + '\'' + '}';
    }
 
}