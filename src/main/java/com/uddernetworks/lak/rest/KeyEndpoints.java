package com.uddernetworks.lak.rest;

import com.uddernetworks.lak.keys.Key;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping(path = "/keys")
public class KeyEndpoints {

    @GetMapping(path = "/list")
    public @ResponseBody
    Iterable<Key> getAllKeys() {
        return null;
    }

    @PostMapping(path = "/update", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    Map<String, Object> updateKey(Key key) {
        // TODO: Update key
        return Map.of("status", "ok");
    }
}
