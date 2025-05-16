package ee.bcs.DoseSync.infrastructure;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Error {
    INCORRECT_CREDENTIALS("Vale kasutajanimi või parool", 111),
    NO_ATM_LOCATIONS_FOUND("Ei leitud ühtegi pangaautomaati" , 222),
    LOCATION_NAME_UNAVAILABLE("Sellise pangaautomaadi asukoht juba olemas", 403);

    private final String message;
    private final int errorCode;
}