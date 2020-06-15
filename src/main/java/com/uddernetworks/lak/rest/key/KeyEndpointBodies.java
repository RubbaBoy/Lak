package com.uddernetworks.lak.rest.key;

import com.uddernetworks.lak.keys.KeyEnum;

import java.util.UUID;

public class KeyEndpointBodies {
    public static class UpdatingKey {
        private final int key;
        private final UUID variantId;
        private final Boolean loop;

        public UpdatingKey(int key, UUID variantId, Boolean loop) {
            this.key = key;
            this.variantId = variantId;
            this.loop = loop;
        }

        public int getKey() {
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
