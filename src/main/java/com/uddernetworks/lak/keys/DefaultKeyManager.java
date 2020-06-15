package com.uddernetworks.lak.keys;

import com.uddernetworks.lak.database.key.KeyRepository;
import com.uddernetworks.lak.rest.key.KeyEndpointBodies;
import com.uddernetworks.lak.sounds.SoundManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.uddernetworks.lak.database.DatabaseUtility.waitFuture;

@Component("defaultKeyManager")
public class DefaultKeyManager implements KeyManager {

    @Value("${lak.database.synchronous}")
    private boolean synchronous;

    private final KeyRepository keyRepository;
    private final SoundManager soundManager;
    private final List<Key> keys = new ArrayList<>();

    public DefaultKeyManager(@Qualifier("sqlKeyRepository") KeyRepository keyRepository,
                             @Qualifier("variableSoundManager") SoundManager soundManager) {
        this.keyRepository = keyRepository;
        this.soundManager = soundManager;
    }

    @Override
    public List<Key> getAllKeys() {
        return keys;
    }

    @Override
    public Key getKeyFrom(KeyEnum keyEnum) {
        return keys.stream().filter(key -> key.getKey() == keyEnum).findFirst().orElseGet(() -> {
            throw new IllegalStateException("No key is defined for enum '" + keyEnum.name() + "' ID #" + keyEnum.getId());
        });
    }

    @Override
    public void updateKey(KeyEndpointBodies.UpdatingKey updatingKey) {
        var storedKeyOptional = keys.stream().filter(key -> key.getKey().getId() == updatingKey.getKey()).findFirst();
        if (storedKeyOptional.isPresent()) {
            var storedKey = storedKeyOptional.get();

            var variantId = updatingKey.getVariantId();
            if (variantId != null) {
                soundManager.getVariant(variantId)
                        .ifPresent(storedKey::setSound);
            }

            var loop = updatingKey.isLoop();
            if (loop != null) {
                storedKey.setLoop(loop);
            }

            waitFuture(synchronous, keyRepository.updateKey(storedKey));
        }
    }
}
