package com.uddernetworks.lak.pi.api.light;

import java.util.List;
import java.util.Optional;

public interface LightHandler<T extends AbstractedLight> {

    /**
     * Gets the associated {@link AbstractedLight} with the given {@link LightId}.
     *
     * @param lightId The {@link LightId} to get the light of
     * @return The associated {@link AbstractedLight}
     */
    T lightFromId(LightId lightId);

    /**
     * Registers a light to be used in the future.
     *
     * @param light The light to register
     * @return The current {@link LightHandler} for chaining
     */
    LightHandler<T> registerLight(Light<T> light);

    /**
     * Gets an unmodifiable list of registered lights.
     *
     * @return An unmodifiable list of registered lights
     */
    List<Light<T>> getLights();

    /**
     * Gets a {@link Light} by its {@link AbstractedLight}, if registered via {@link #registerLight(Light)}.
     *
     * @param id The {@link AbstractedLight}
     * @return The light retrieved
     */
    Optional<Light<T>> getLight(T id);
}
