package com.uddernetworks.lak.rest.sound;

import com.uddernetworks.lak.sounds.modulation.ModulationId;
import com.uddernetworks.lak.sounds.modulation.ModulatorData;

import java.awt.Color;
import java.util.UUID;

public class SoundEndpointBodies {
    public static class AddingSound {
        private String uri;

        public String getURI() {
            return uri;
        }

        public void setURI(String uri) {
            this.uri = uri;
        }

        @Override
        public String toString() {
            return "AddingSound{" +
                    "uri='" + uri + '\'' +
                    '}';
        }
    }

    public static class AddingVariant {
        private String name;
        private UUID soundId;

        public String getName() {
            return name;
        }

        public UUID getSoundId() {
            return soundId;
        }

        @Override
        public String toString() {
            return "AddingVariant{" +
                    "name='" + name + '\'' +
                    ", soundId=" + soundId +
                    '}';
        }
    }

    public static class UpdatingVariant {
        private final UUID id;
        private final UUID soundId;
        private final String description;
        private final Color color;

        UpdatingVariant(UUID id, UUID soundId, String description, Color color) {
            this.id = id;
            this.soundId = soundId;
            this.description = description;
            this.color = color;
        }

        public UUID getId() {
            return id;
        }

        public UUID getSoundId() {
            return soundId;
        }

        public String getDescription() {
            return description;
        }

        public Color getColor() {
            return color;
        }

        @Override
        public String toString() {
            return "UpdatingVariant{" +
                    "id=" + id +
                    ", soundId=" + soundId +
                    ", description='" + description + '\'' +
                    ", color=" + color +
                    '}';
        }
    }

    public static class UpdatingModulator {
        private final UUID variantUUID;
        private final ModulationId id;
        private final ModulatorData modulatorData;

        UpdatingModulator(UUID variantUUID, ModulationId id, ModulatorData modulatorData) {
            this.variantUUID = variantUUID;
            this.id = id;
            this.modulatorData = modulatorData;
        }

        public UUID getVariantUUID() {
            return variantUUID;
        }

        public ModulationId getId() {
            return id;
        }

        public ModulatorData getModulatorData() {
            return modulatorData;
        }

        @Override
        public String toString() {
            return "UpdatingModulator{" +
                    "variantUUID=" + variantUUID +
                    ", id=" + id +
                    ", modulatorData=" + modulatorData +
                    '}';
        }
    }

    public static class AddRemoveModulator {
        private final UUID variantUUID;
        private final ModulationId id;

        public AddRemoveModulator(UUID variantUUID, ModulationId id) {
            this.variantUUID = variantUUID;
            this.id = id;
        }

        public UUID getVariantUUID() {
            return variantUUID;
        }

        public ModulationId getId() {
            return id;
        }

        @Override
        public String toString() {
            return "AddRemoveModulator{" +
                    "variantUUID=" + variantUUID +
                    ", id=" + id +
                    '}';
        }
    }
}
