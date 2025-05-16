package ee.bcs.DoseSync;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Status {
    ACTIVE("A"),
    DELETED("B");
    private final String code;
}
