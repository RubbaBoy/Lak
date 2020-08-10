package com.uddernetworks.lak.keys;

import com.uddernetworks.lak.database.key.KeyRepository;
import com.uddernetworks.lak.rest.key.KeyEndpointBodies;
import com.uddernetworks.lak.sounds.SoundManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.uddernetworks.lak.database.DatabaseUtility.waitFuture;

@Component("defaultKeyManager")
public class DefaultKeyManager implements KeyManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultKeyManager.class);

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
    public void setKeys(List<Key> keys) {
        if (!this.keys.isEmpty()) {
            LOGGER.warn("Tried to invoke DefaultKeyManager#setKeys(List<Key>) while key list is populated");
            return;
        }

        this.keys.addAll(keys);
    }

    @Override
    public void updateKey(KeyEndpointBodies.UpdatingKey updatingKey) {
        LOGGER.debug("Updating hereeee");

        var storedKeyOptional = keys.stream().filter(key -> key.getKey() == updatingKey.getKey()).findFirst();
        if (storedKeyOptional.isPresent()) {
            var storedKey = storedKeyOptional.get();

            var variantId = updatingKey.getVariantId();
            LOGGER.debug("Variant is {}", variantId);
            if (variantId != null) {
                soundManager.getVariant(variantId)
                        .ifPresent(storedKey::setSound);
            }

            var loop = updatingKey.isLoop();
            LOGGER.debug("Looping {}", loop);
            if (loop != null) {
                storedKey.setLoop(loop);
            }

            waitFuture(synchronous, keyRepository.updateKey(storedKey));
        }
    }
}
