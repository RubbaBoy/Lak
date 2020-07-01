package com.uddernetworks.lak.pi;

import com.uddernetworks.lak.pi.api.PiManager;
import com.uddernetworks.lak.pi.api.button.ButtonHandler;
import com.uddernetworks.lak.pi.api.light.LightHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

public class DummyManager implements PiManager {

    @Override
    public void registerComponents() {
    }

    @Override
    public void startListening() {
    }
}
