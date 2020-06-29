package com.uddernetworks.lak.pi.light;

import com.uddernetworks.lak.pi.button.Button;
import com.uddernetworks.lak.pi.button.ButtonHandler;
import com.uddernetworks.lak.pi.button.ButtonId;

import java.util.List;
import java.util.Optional;

public interface LightHandler {

    /**
     * Registers a light to be used in the future.
     *
     * @param light The light to register
     * @return The current {@link LightHandler} for chaining
     */
    LightHandler registerLight(Light<LightId> light);

    /**
     * Gets an unmodifiable list of registered lights.
     *
     * @return An unmodifiable list of registered lights
     */
    List<Light<LightId>> getLights();

    /**
     * Gets a {@link Light} by its {@link LightId}, if registered via {@link #registerLight(Light)}.
     *
     * @param id The {@link LightId}
     * @return The light retrieved
     */
    Optional<Light<LightId>> getLight(LightId id);
}
