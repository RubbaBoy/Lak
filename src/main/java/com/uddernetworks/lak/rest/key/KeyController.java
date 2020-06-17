package com.uddernetworks.lak.rest.key;

import com.uddernetworks.lak.keys.Key;
import com.uddernetworks.lak.keys.KeyEnum;
import com.uddernetworks.lak.keys.KeyManager;
import com.uddernetworks.lak.rest.exceptions.KeyNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping(path = "/keys")
public class KeyController {

    private final KeyManager keyManager;

    public KeyController(@Qualifier("defaultKeyManager") KeyManager keyManager) {
        this.keyManager = keyManager;
    }

    @GetMapping(path = "/list")
    public @ResponseBody
    Iterable<Key> getAllKeys() {
        return keyManager.getAllKeys();
    }

    @PostMapping(path = "/update", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    Map<String, Object> updateKey(@RequestBody KeyEndpointBodies.UpdatingKey updatingKey) {
        if (KeyEnum.fromId(updatingKey.getKey()).isEmpty()) {
            throw new KeyNotFoundException(updatingKey.getKey());
        }

        keyManager.updateKey(updatingKey);

        return Map.of("status", "ok");
    }
}
