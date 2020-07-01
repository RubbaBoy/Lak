package com.uddernetworks.lak.pi.api;

import com.uddernetworks.lak.pi.api.button.Button;
import com.uddernetworks.lak.pi.api.button.ButtonId;
import com.uddernetworks.lak.pi.api.component.PiComponent;
import com.uddernetworks.lak.pi.api.light.Light;
import com.uddernetworks.lak.pi.api.light.LightId;

public class ComponentNotFoundException extends RuntimeException {

    public ComponentNotFoundException(String message) {
        super(message);
    }

    public ComponentNotFoundException(Enum<?> id) {
        super(idMessageCreator(id) + "ID \"" + id.name() + "\" could not be paired with a component.");
    }

    private static String idMessageCreator(Enum<?> id) {
        if (id instanceof ButtonId) {
            return "Button ";
        } else if (id instanceof LightId) {
            return "Light ";
        }
        return "";
    }
}
