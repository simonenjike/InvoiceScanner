package sim.refpro.invoicescanner;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import org.springframework.http.*;
import static org.junit.jupiter.api.Assertions.*;

import sim.refpro.invoicescanner.dto.InvoiceScanRequest;
import sim.refpro.invoicescanner.dto.InvoiceScanResponse;

/**
 * <h2>Invoice Scan Integration Tests</h2>
 *
 * <p>
 * This integration test class verifies the complete end-to-end workflow of the Invoice Scanner Service. 
 * The Spring Boot application is started on a random  * port and real HTTP requests are sent to the REST 
 * endpoint.
 * </p>
 *
 * <p>
 * The tests cover the following scenarios:
 * </p>
 * <ul>
 *   <li>PDF containing blacklisted IBANs</li>
 *   <li>PDF containing valid IBANs without blacklist matches</li>
 *   <li>PDF containing no IBANs</li>
 *   <li>Invalid or non-existing PDF URL</li>
 * </ul>
 *
 * <p>
 * All test PDFs are served via Spring Boot's static resource handling from
 * <code>src/main/resources/static/testdata</code>.
 * </p>
 *
 * @author Simone Njike
 * @version 1.0
 * @since 12.12.2025
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class InvoiceScanIntegrationTest {
    
    @LocalServerPort
    private int port;
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    /**
     * Builds the base URL for the locally started Spring Boot test server.
     * 
     * @return the base URL including the random port
     */
    private String baseUrl() {
        
        return "http://localhost:" + port;
    }
    
    /**
     * Sends a POST request to the invoice scan endpoint using one of the predefined test PDF files.
     * 
     * @param pdfFileName the file name of the test PDF located in /testdata
     * @return the HTTP response containing the scan result
     */
    private ResponseEntity<InvoiceScanResponse> postScan(String pdfFileName) {

        String scanUrl = baseUrl() + "/api/v1/invoice-scan";
        String invoiceUrl = baseUrl() + "/testdata/" + pdfFileName;

        InvoiceScanRequest request = new InvoiceScanRequest();
        request.setInvoiceUrl(invoiceUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<InvoiceScanRequest> entity = new HttpEntity<>(request, headers);

        return restTemplate.postForEntity(scanUrl, entity, InvoiceScanResponse.class);
    }
    
    /**
     * Tests a PDF that contains at least one blacklisted IBAN. 
     * The response is expected to contain blacklist hits.
     */    
    @Test
    void scan_blacklistedPdf_returnBlacklistedIbans() {
        
        ResponseEntity<InvoiceScanResponse> response = postScan("Testdata_Blacklisted.pdf");
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        
        InvoiceScanResponse body = response.getBody();
        
        assertTrue(body.isSuccess());
        assertFalse(body.getExtractedIbans().isEmpty());
        assertFalse(body.getBlacklistedIbans().isEmpty());
        
       assertEquals("Blacklisted IBANs were detected in the invoice.", body.getMessage());        
    }        
    
    /**
     * Tests a PDF that contains valid IBANs but none of them are part of the blacklist.
     */    
    @Test
    void scan_noneBlacklistedPdf_returnNoneBlacklistedIbans() {
        
        ResponseEntity<InvoiceScanResponse> response = postScan("Testdata_None_Blacklisted.pdf");
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        
        InvoiceScanResponse body = response.getBody();
        
        assertTrue(body.isSuccess());
        assertFalse(body.getExtractedIbans().isEmpty());
        assertTrue(body.getBlacklistedIbans().isEmpty());
        
       assertEquals("IBANs were found but none of them are blacklisted.", body.getMessage());                
    }    

    /**
     * Tests a PDF that does not contain any IBANs. Both result lists are expected to be empty.
     */    
    @Test
    void scan_noIbansPdf_returnNoIbansFound() {
        
        ResponseEntity<InvoiceScanResponse> response = postScan("Testdata_No_Ibans.pdf");
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        
        InvoiceScanResponse body = response.getBody();
        
        assertTrue(body.isSuccess());
        assertTrue(body.getExtractedIbans().isEmpty());
        assertTrue(body.getBlacklistedIbans().isEmpty());
        
       assertEquals("No IBANs were found in the invoice.", body.getMessage());           
    }    
    
    /**
     * Tests the behavior of the service when the provided invoice URL is invalid or the PDF does not exist.
     */    
    @Test
    void scan_InvalidUrl_returnInvalidUrl() {
        
        String scanUrl = baseUrl() + "/api/v1/invoice-scan";

        InvoiceScanRequest request = new InvoiceScanRequest();
        request.setInvoiceUrl(baseUrl() + "/testdata/does-not-exist.pdf");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<InvoiceScanRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(scanUrl, entity, String.class);

        assertTrue(response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError());        
    }    
    
}