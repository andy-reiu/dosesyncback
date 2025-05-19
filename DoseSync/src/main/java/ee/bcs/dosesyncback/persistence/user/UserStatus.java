package ee.bcs.dosesyncback.persistence.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserStatus {
    ACTIVE("A"),
    DELETED("B");
    private final String code;
}
