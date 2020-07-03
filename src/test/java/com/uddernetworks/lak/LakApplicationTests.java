package com.uddernetworks.lak;

import com.uddernetworks.lak.rest.sound.SoundController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest()
class LakApplicationTests {

    @Autowired
    private SoundController controller;

    @Test
    void contextLoads() {
        assertNotNull(controller);
    }

}
