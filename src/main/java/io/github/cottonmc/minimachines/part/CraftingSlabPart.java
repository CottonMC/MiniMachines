package io.github.cottonmc.minimachines.part;

import alexiil.mc.lib.multipart.api.AbstractPart;
import alexiil.mc.lib.multipart.api.MultipartHolder;
import alexiil.mc.lib.multipart.api.PartDefinition;
import alexiil.mc.lib.multipart.api.render.PartModelKey;
import net.minecraft.util.shape.VoxelShape;

import javax.annotation.Nullable;

public class CraftingSlabPart extends AbstractPart {
    public CraftingSlabPart(PartDefinition definition, MultipartHolder holder) {
        super(definition, holder);
    }

    @Override
    public VoxelShape getShape() {
        return null;
    }

    @Nullable
    @Override
    public PartModelKey getModelKey() {
        return null;
    }
}
