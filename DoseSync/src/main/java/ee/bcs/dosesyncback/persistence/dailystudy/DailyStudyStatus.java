package ee.bcs.dosesyncback.persistence.dailystudy;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DailyStudyStatus {
    PENDING("A"),
    IN_PROGRESS("B"),
    COMPLETED("D");
    private final String code;
}
