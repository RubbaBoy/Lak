package com.uddernetworks.lak.rest.key;

import java.util.UUID;

public class KeyEndpointBodies {
    public static class UpdatingKey {
        private final int key;
        private final Boolean shift;
        private final UUID variantId;
        private final Boolean loop;

        public UpdatingKey(int key, Boolean shift, UUID variantId, Boolean loop) {
            this.key = key;
            this.shift = shift;
            this.variantId = variantId;
            this.loop = loop;
        }

        public int getKey() {
            return key;
        }

        public Boolean isShift() {
            return shift;
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
