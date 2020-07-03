package com.uddernetworks.lak.pi;

import com.uddernetworks.lak.api.PiManager;
import com.uddernetworks.lak.api.button.ButtonHandler;
import com.uddernetworks.lak.api.light.LightHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

public class DummyManager implements PiManager {

    @Override
    public void init() {
    }

    @Override
    public void startListening() {
    }
}
