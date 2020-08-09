package com.uddernetworks.lak.sounds.tts;

import java.net.InetAddress;
import java.util.concurrent.CompletableFuture;

public interface IPSpeaker {

    /**
     * Speaks the given IP address.
     *
     * @param address The IP address to speak
     * @return The {@link CompletableFuture} of the task
     */
    CompletableFuture<Void> speakIP(InetAddress address);

}
