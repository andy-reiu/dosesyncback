package ee.bcs.DoseSync;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AccountStatus {
    ACTIVE("A"),
    DELETED("B");
    private final String code;
}
