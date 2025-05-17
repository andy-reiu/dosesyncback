package ee.bcs.DoseSync;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StudyStatus {
    PENDING("A"),
    IN_PROGRESS("B"),
    COMPLETED("D");
    private final String code;
}
