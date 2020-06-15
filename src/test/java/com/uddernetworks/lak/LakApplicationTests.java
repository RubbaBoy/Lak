package com.uddernetworks.lak;

import com.uddernetworks.lak.rest.sound.SoundController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LakApplicationTests {

    @Autowired
    private SoundController controller;

    @Test
    void contextLoads() {
        assertNotNull(controller);
    }

}
