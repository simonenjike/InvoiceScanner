package sim.refpro.invoicescanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <h2>Invoice Scanner Application</h2>
 *
 * <p>
 * Main entry point for the <b>Invoice Scanner Service</b>. This Spring Boot application scans PDF 
 * invoices for blacklisted IBANs to help detect potential money-laundering activities.
 * </p>
 *
 * <p>
 * Build &amp; Documentation:<br>
 * – Sources and Javadoc JARs are generated automatically via Maven.<br>
 * – UTF-8 encoding enforced.<br>
 * – Enforcer plugin ensures Java 21 compatibility.
 * </p>
 *
 * <h3>Usage</h3>
 * <pre>
 * {@code
 * mvn clean verify
 * java -jar target/invoicescanner-0.0.1-SNAPSHOT.jar
 * }
 * </pre>
 *
 * @author Simone Njike
 * @version 1.0
 * @since 03.12.2025
 */
@SpringBootApplication
public class InvoiceScanApplication {

    /**
     * Starts the Spring Boot application.
     * 
     * @param args  command-line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(InvoiceScanApplication.class, args);
    }

}