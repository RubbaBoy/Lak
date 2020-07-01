package com.uddernetworks.lak.pi;

import com.uddernetworks.lak.pi.api.PiDetails;

import java.util.Arrays;

public class AutoPiDetails implements PiDetails {

    private final String hardware;
    private final String revision;
    private final String model;

    public AutoPiDetails() {
        var cpuInfo = CommandRunner.runCommand("cat", "/proc/cpuinfo").split("\n");
        var endData = Arrays.stream(Arrays.copyOfRange(cpuInfo, cpuInfo.length - 4, cpuInfo.length))
                .map(line -> line.split(":")[1].trim())
                .toArray(String[]::new);

        hardware = endData[0];
        revision = endData[1];
        model = endData[4];
    }

    @Override
    public String getModel() {
        return model;
    }

    @Override
    public String getHardware() {
        return hardware;
    }

    @Override
    public String getRevision() {
        return revision;
    }
}
