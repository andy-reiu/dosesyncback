package ee.bcs.dosesyncback.util;

import ee.bcs.dosesyncback.persistence.machinefill.MachineFill;
import lombok.Getter;

import java.util.List;

@Getter
public class SimulationResult {
    private final double firstInjectedVolume;
    private final double lastRemainingVolume;
    private final List<MachineFill> simulatedFills;

    public SimulationResult(double firstInjectedVolume, double lastRemainingVolume, List<MachineFill> simulatedFills) {
        this.firstInjectedVolume = firstInjectedVolume;
        this.lastRemainingVolume = lastRemainingVolume;
        this.simulatedFills = simulatedFills;
    }

}
