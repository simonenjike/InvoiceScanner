package sim.refpro.invoicescanner.repository;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import org.springframework.stereotype.Repository;

/**
 * <h2>In Memory Blacklist Repository</h2>
 * 
 * <p>
 * In-memory implementation of {@link BlacklistRepository}. This repository stores blacklisted IBANs 
 * directly in memory. It is intended for prototypes, testing and development setups.
 *</p>
 * 
 * <p>
 * The IBANs stored in this repository MUST:<br>
 *  – be uppercase<br>
 *  – contain no whitespace
 * </p>
 * 
 * @author Simone Njike
 * @version 1.1
 * @since 02.12.2025
 */
@Repository
public class InMemoryBlacklistRepository implements BlacklistRepository {

    /**
     * Internal hardcoded list of blacklisted IBANs.
     * 
     * IMPORTANT: IBANs must be stored in normalized form: uppercase + no spaces.
     */
    private static final List<String> BLACKLIST = List.of(
            "DE44500105175407324931",           
            "AT611904300234573201",
            "FR7630006000011234567890189"
    );

    /**
     * Checks whether the given IBAN is present in the blacklist.<br>
     *
     * The IBAN should be provided in normalized form:<br>
     * – uppercase letters<br>
     * – no whitespace
     *
     * @param iban The IBAN to check; must not be null or blank.
     * @return {@code true} if the IBAN is blacklisted, otherwise {@code false}.
     */
    @Override
    public boolean isBlacklisted(String iban) {
        
        if (iban == null || iban.isBlank()) {
            return false;
        }
        
        // Normalize: uppercase + remove spaces
        String normalized = normalize(iban);
        return BLACKLIST.contains(normalized);
    }

    @Override
    public List<String> findAll() {
        
        return Collections.unmodifiableList(BLACKLIST);
    }
    
    /**
     * Normalizes an IBAN to a compare-ready format: uppercase + no whitespace.
     */
    private String normalize(String iban) {
        
        return iban.toUpperCase(Locale.ROOT).replaceAll("\\s+", "");
    }    
    
}