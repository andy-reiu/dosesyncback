package ee.bcs.dosesyncback.persistence.study;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StudyStatus {
    PENDING("P"),
    IN_PROGRESS("I"),
    COMPLETED("C");
    private final String code;
}
