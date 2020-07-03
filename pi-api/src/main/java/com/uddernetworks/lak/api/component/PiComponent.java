package com.uddernetworks.lak.api.component;

/**
 * An interface of components such as lights, buttons, etc.
 *
 * @param <T> The identifier type, preferably an enum.
 */
public interface PiComponent<T> {

    /**
     * Gets the unique identifier of the enum identifier.
     *
     * @return The unique identifier
     */
    T getId();

    /**
     * Gets the (possibly non-unique) name used for display purposes.
     *
     * @return The display name of the component
     */
    String getName();

}
