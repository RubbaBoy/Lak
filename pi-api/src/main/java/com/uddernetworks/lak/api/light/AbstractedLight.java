package com.uddernetworks.lak.api.light;

/**
 * Additional information bound to each {@link LightId} for implementation-specific information regarding it.
 */
public interface AbstractedLight {

    /**
     * Gets the {@link LightId}.
     *
     * @return The {@link LightId}
     */
    LightId getId();

    /**
     * Gets the display name for the light.
     *
     * @return The light's name
     */
    String getName();
}
