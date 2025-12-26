package sim.refpro.invoicescanner.dto;

import java.util.List;

/**
 * <h2>Invoice Scan Response</h2>
 * 
 * <p>
 * Represents the result of the invoice scan. Contains extracted IBANs from the PDF, detected blacklist 
 * matches, and additional metadata about the scanning process.
 * </p>
 * 
 * <b>Example:</b>
 * <pre>
 * {@code
 *   {
 *     "containsBlacklistedIban": true,
 *     "detectedIbans": ["DE89370400440532013000"]
 *   }
 * }
 * </pre>
 *
 * @author Simone Njike
 * @version 1.1
 * @since 30.11.2025
 */
public class InvoiceScanResponse {
    
    /**
     * All IBANs found in the PDF.
     */
    private List<String> extractedIbans;

    /**
     * IBANs that match entries from the blacklist.
     */
    private List<String> blacklistedIbans;

    /**
     * Indicates whether the scan was executed successfully.
     */
    private boolean success;

    /**
     * Optional message with details (e.g., warnings, errors, status).
     */
    private String message;

    public InvoiceScanResponse() { }

    public InvoiceScanResponse(List<String> extractedIbans, List<String> blacklistedIbans, 
                                                boolean success, String message) {
        
        this.extractedIbans = extractedIbans;
        this.blacklistedIbans = blacklistedIbans;
        this.success = success;
        this.message = message;
    }

    public List<String> getExtractedIbans() {
        return extractedIbans;
    }

    public void setExtractedIbans(List<String> extractedIbans) {
        this.extractedIbans = extractedIbans;
    }

    public List<String> getBlacklistedIbans() {
        return blacklistedIbans;
    }

    public void setBlacklistedIbans(List<String> blacklistedIbans) {
        this.blacklistedIbans = blacklistedIbans;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        
        return "InvoiceScanResponse{" + 
                "extractedIbans=" + extractedIbans + 
                ", blacklistedIbans=" + blacklistedIbans + 
                ", success=" + success + 
                ", message=' " + message + '\'' +
                '}';
    }

}