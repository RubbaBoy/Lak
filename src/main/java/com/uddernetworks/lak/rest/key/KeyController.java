package com.uddernetworks.lak.rest.key;

import com.uddernetworks.lak.keys.Key;
import com.uddernetworks.lak.keys.KeyEnum;
import com.uddernetworks.lak.keys.KeyManager;
import com.uddernetworks.lak.rest.exceptions.KeyNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;
import java.util.stream.Collectors;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping(path = "/keys")
public class KeyController {

    private static final Logger LOGGER = LoggerFactory.getLogger(KeyController.class);

    private final KeyManager keyManager;

    public KeyController(@Qualifier("defaultKeyManager") KeyManager keyManager) {
        this.keyManager = keyManager;
    }

    @GetMapping(path = "/list")
    public @ResponseBody
    Iterable<SmallKey> getAllKeys() {
        return keyManager.getAllKeys()
                .stream()
                .map(SmallKey::new)
                .collect(Collectors.toList());
    }

    @PostMapping(path = "/update", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    Map<String, Object> updateKey(@RequestBody KeyEndpointBodies.UpdatingKey updatingKey) {
        if (updatingKey.getKey() == null) {
            throw new KeyNotFoundException();
        }

        keyManager.updateKey(updatingKey);

        return Map.of("status", "ok");
    }
}
