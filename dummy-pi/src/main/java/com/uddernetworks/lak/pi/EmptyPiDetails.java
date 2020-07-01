package com.uddernetworks.lak.pi;

import com.uddernetworks.lak.pi.api.PiDetails;
import org.springframework.stereotype.Component;

import java.util.Arrays;

public class EmptyPiDetails implements PiDetails {

    @Override
    public String getModel() {
        return "-";
    }

    @Override
    public String getHardware() {
        return "-";
    }

    @Override
    public String getRevision() {
        return "-";
    }
}
