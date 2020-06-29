package com.uddernetworks.lak.pi.component;

/**
 * An interface of components such as lights, buttons, etc.
 *
 * @param <T> The identifier type, preferably an enum.
 */
public interface PiComponent<T> {

    /**
     * Gets the unique identifier of type T.
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
