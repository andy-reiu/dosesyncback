package ee.bcs.dosesyncback.persistence.machine;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MachineStatus {
    ACTIVE("A"),
    DISABLED("D");
    private final String code;
}
