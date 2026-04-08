module com.baeldung.lnj.domain {
    // transitive keyword makes java.logging available to modules requiring com.baeldung.lnj.domain
    requires transitive java.logging;

    // Below directive makes model package available to all modules requiring com.baeldung.lnj.domain
    // exports com.baeldung.lnj.domain.model;

    // A qualified exports directive restricts access to mentioned modules only
    // Provides more security
    exports com.baeldung.lnj.domain.model to com.baeldung.lnj.api;

    // opens directive works like exports, but it grants runtime-only reflective access to a package.
    // It is useful with framework relying on reflection like Spring or Jackson
    // Qualified opens directive provides more security
    // Without this you might get an InaccessibleObjectException.
    opens com.baeldung.lnj.domain.model to com.fasterxml.jackson.databind;
}