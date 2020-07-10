package com.uddernetworks.lak.hardware;

public interface ButtonInterface {

    /**
     * Initializes the physical buttons. See implementation documentation for more details.
     */
    void init();

    /**
     * Allows the button(s) to listen for recording. <pre>start</pre> is invoked when it is finally ready to be recorded, if any
     * intermediary steps are required.
     * @param start The runnable (called once) to be invoked when recording is ready
     */
    void startRecording(Runnable start, Runnable stop);
}
