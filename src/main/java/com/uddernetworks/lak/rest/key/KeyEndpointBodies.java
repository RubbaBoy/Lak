package com.uddernetworks.lak.rest.key;

import com.uddernetworks.lak.keys.KeyEnum;

import java.util.UUID;

public class KeyEndpointBodies {
    public static class UpdatingKey {
        private final KeyEnum key;
        private final UUID variantId;
        private final Boolean loop;

        public UpdatingKey(KeyEnum key, UUID variantId, Boolean loop) {
            this.key = key;
            this.variantId = variantId;
            this.loop = loop;
        }

        public KeyEnum getKey() {
            return key;
        }

        public UUID getVariantId() {
            return variantId;
        }

        public Boolean isLoop() {
            return loop;
        }

        @Override
        public String toString() {
            return "UpdatingKey{" +
                    "key=" + key +
                    ", variantId=" + variantId +
                    ", loop=" + loop +
                    '}';
        }
    }
}
