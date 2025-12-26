package sim.refpro.invoicescanner.service.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.iban4j.IbanFormatException;
import org.iban4j.IbanUtil;
import org.iban4j.InvalidCheckDigitException;
import org.iban4j.UnsupportedCountryException;
import org.springframework.stereotype.Component;

/**
 * <h2>IBAN Scanner</h2>
 * 
 * <p>
 * Component responsible for scanning text and extracting valid IBANs.
 * </p>
 * 
 * The scanner:<br>
 * – searches the given text for IBAN-like patterns<br>
 * – normalizes them by removing whitespace<br>
 * – validates candidates using iban4j<br>
 * – returns a list of unique, valid IBANs
 *
 * @author Simone Njike
 * @version 1.1
 * @since 03.12.2025
 */
@Component
public class IbanScanner {

    /**
     * Regular expression to find IBAN-like patterns. Allows spaces between groups, e.g. 
     * "DE12 3456 7890 1234 5678 90".
     */
    private static final Pattern IBAN_CANDIDATE_PATTERN = 
                                                        Pattern.compile("([A-Z]{2}\\d{2}(?:[ ]?[A-Z0-9]{1,4}){3,15})");
    
    /**
     * Scans the given text for valid IBANs.
     *
     * @param text The input text; may be null or empty.
     * @return A list of unique, normalized IBAN strings (without spaces). The list is ordered by first 
     *                      occurrence in the text and never null (may be empty).
     */
    public List<String> findIbans(String text) {
        
        // Null / Leer-Check
        if (text == null || text.isBlank()) {            
            return Collections.emptyList();
        }

        String upper = text.toUpperCase(Locale.ROOT); // All in uppercase
        Matcher matcher = IBAN_CANDIDATE_PATTERN.matcher(upper);
        Set<String> result = new LinkedHashSet<>(); // 

        while (matcher.find()) {
            String rawCandidate = matcher.group(1);

            // Remove all whitespace to get a compact IBAN form
            String normalized = rawCandidate.replaceAll("\\s+", "");

            try {
                // Validate structure, country code and check digits using iban4j
                IbanUtil.validate(normalized);
                result.add(normalized);
            } catch (IbanFormatException | InvalidCheckDigitException | UnsupportedCountryException ex) {
                // Ignore invalid or unsupported IBAN candidates
            }
        }
        return new ArrayList<>(result);
    }
    
}