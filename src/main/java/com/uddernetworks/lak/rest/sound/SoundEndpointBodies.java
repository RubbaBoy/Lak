package com.uddernetworks.lak.rest.sound;

import com.uddernetworks.lak.sounds.modulation.ModulationId;
import com.uddernetworks.lak.sounds.modulation.ModulatorData;

import java.awt.Color;
import java.util.UUID;

public class SoundEndpointBodies {
    public static class AddingSound {
        // The sub-path within the base path. e.g. if base was C:/foo and this was bar, the absolute path would be
        // C:/foo/bar
        private String relPath;

        public String getRelPath() {
            return relPath;
        }

        public void setRelPath(String relPath) {
            this.relPath = relPath;
        }

        @Override
        public String toString() {
            return "AddingSound{" +
                    "uri='" + relPath + '\'' +
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
        private final UUID variantId;
        private final ModulationId id;
        private final ModulatorData modulatorData;

        UpdatingModulator(UUID variantId, ModulationId id, ModulatorData modulatorData) {
            this.variantId = variantId;
            this.id = id;
            this.modulatorData = modulatorData;
        }

        public UUID getVariantId() {
            return variantId;
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
                    "variantUUID=" + variantId +
                    ", id=" + id +
                    ", modulatorData=" + modulatorData +
                    '}';
        }
    }

    public static class AddRemoveModulator {
        private final UUID variantId;
        private final ModulationId id;

        public AddRemoveModulator(UUID variantId, ModulationId id) {
            this.variantId = variantId;
            this.id = id;
        }

        public UUID getVariantId() {
            return variantId;
        }

        public ModulationId getId() {
            return id;
        }

        @Override
        public String toString() {
            return "AddRemoveModulator{" +
                    "variantUUID=" + variantId +
                    ", id=" + id +
                    '}';
        }
    }
}
