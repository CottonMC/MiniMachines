package io.github.cottonmc.minimachines.part;

import alexiil.mc.lib.multipart.api.render.PartModelKey;

public class MiniModelKey extends PartModelKey {
    private final SlabPart part;

    public MiniModelKey(SlabPart part) {
        this.part = part;
    }

    public SlabPart getPart() {
        return part;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof MiniModelKey && part.equals(((MiniModelKey) o).part);
    }

    @Override
    public int hashCode() {
        return part.hashCode();
    }
}
