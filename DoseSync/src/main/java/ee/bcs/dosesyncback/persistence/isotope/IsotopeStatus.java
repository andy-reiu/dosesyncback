package ee.bcs.dosesyncback.persistence.isotope;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum IsotopeStatus {
    ACTIVE("A"),
    DISABLED("D");
    private final String code;
}