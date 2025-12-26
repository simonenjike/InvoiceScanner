package sim.refpro.invoicescanner.repository;

import java.util.List;

/**
 * <h2>Blacklist Repository</h2>
 * 
 * <p>
 * Repository abstraction for accessing blacklisted IBANs. Implementations of this interface may load data 
 * from configuration files, databases, or any other persistent storage.
 * </p>
 * 
 * @author Simone Njike
 * @version 1.1
 * @since 01.12.2025
 */
public interface BlacklistRepository {

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
    
    boolean isBlacklisted(String iban);

    /**
     * Returns all IBANs that are currently blacklisted.  The returned list contains normalized IBANs 
     * (uppercase, no whitespace).
     *
     * @return A list of all blacklisted IBANs. Never {@code null}, but may be empty.
     */
    
    List<String> findAll();    
    
}