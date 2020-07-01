package com.uddernetworks.lak.pi.light;

import com.uddernetworks.lak.pi.api.light.AbstractedLight;
import com.uddernetworks.lak.pi.api.light.LightId;

import java.util.Arrays;

public enum DummyLight implements AbstractedLight {
    BUTTON_RED(LightId.RED_BUTTON),
    BUTTON_GREEN(LightId.GREEN_BUTTON),
    BUTTON_BLUE(LightId.BLUE_BUTTON),
    ;

    private final LightId lightId;

    DummyLight(LightId lightId) {
        this.lightId = lightId;
    }

    @Override
    public LightId getId() {
        return lightId;
    }

    @Override
    public String getName() {
        return "DUMMY_" + lightId.name();
    }

    /**
     * Gets a {@link DummyLight} for the given {@link LightId}.
     *
     * @param lightId The {@link LightId} to get from
     * @return The associated {@link DummyLight}
     */
    public static DummyLight lightFrom(LightId lightId) {
        return Arrays.stream(values())
                .filter(light -> light.lightId == lightId)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No DummyLight found for " + lightId.name()));
    }
}
