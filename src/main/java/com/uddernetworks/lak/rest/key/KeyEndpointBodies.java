package com.uddernetworks.lak.rest.key;

import com.uddernetworks.lak.keys.KeyEnum;

import java.util.UUID;

public class KeyEndpointBodies {
    public static class UpdatingKey {
        private KeyEnum key;
        private UUID variantId;
        private Boolean loop;

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
