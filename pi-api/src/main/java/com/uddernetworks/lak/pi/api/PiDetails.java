package com.uddernetworks.lak.pi.api;

public interface PiDetails {

    /**
     * The model of the device.
     * Example:
     * <pre>
     *     Raspberry Pi Zero W Rev 1.1
     * </pre>
     *
     * @return The model
     */
    String getModel();

    /**
     * The hardware of the device.
     * Example:
     * <pre>
     *     BCM2835
     * </pre>
     *
     * @return The hardware
     */
    String getHardware();

    /**
     * The revision of the device.
     * Example:
     * <pre>
     *     9000c1
     * </pre>
     *
     * @return The revision
     */
    String getRevision();

}
