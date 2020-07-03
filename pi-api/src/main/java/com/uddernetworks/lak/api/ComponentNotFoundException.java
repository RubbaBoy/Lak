package com.uddernetworks.lak.api;

import com.uddernetworks.lak.api.button.Button;
import com.uddernetworks.lak.api.button.ButtonId;
import com.uddernetworks.lak.api.component.PiComponent;
import com.uddernetworks.lak.api.light.Light;
import com.uddernetworks.lak.api.light.LightId;

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
