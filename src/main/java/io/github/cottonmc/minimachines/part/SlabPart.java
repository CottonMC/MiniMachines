package io.github.cottonmc.minimachines.part;

import alexiil.mc.lib.multipart.api.AbstractPart;
import alexiil.mc.lib.multipart.api.MultipartHolder;
import alexiil.mc.lib.multipart.api.PartDefinition;
import alexiil.mc.lib.multipart.api.render.PartModelKey;
import alexiil.mc.lib.net.IMsgWriteCtx;
import alexiil.mc.lib.net.NetByteBuf;
import net.minecraft.block.Block;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.shape.VoxelShape;

import javax.annotation.Nullable;

public abstract class SlabPart extends AbstractPart {
    private static final VoxelShape BOTTOM_SHAPE = Block.createCuboidShape(0, 0, 0, 16, 8, 16);
    private static final VoxelShape TOP_SHAPE = Block.createCuboidShape(0, 8, 0, 16, 16, 16);
    private final BlockHalf half;

    public SlabPart(PartDefinition definition, MultipartHolder holder, BlockHalf half) {
        super(definition, holder);
        this.half = half;
    }

    public SlabPart(PartDefinition definition, MultipartHolder holder, CompoundTag tag) {
        super(definition, holder);
        this.half = tag.getBoolean("IsTop") ? BlockHalf.TOP : BlockHalf.BOTTOM;
    }

    public SlabPart(PartDefinition definition, MultipartHolder holder, NetByteBuf buf) {
        super(definition, holder);
        this.half = buf.readBoolean() ? BlockHalf.TOP : BlockHalf.BOTTOM;
    }

    @Override
    public CompoundTag toTag() {
        CompoundTag tag = super.toTag();
        tag.putBoolean("IsTop", half == BlockHalf.TOP);
        return tag;
    }

    @Override
    public void writeCreationData(NetByteBuf buffer, IMsgWriteCtx ctx) {
        super.writeCreationData(buffer, ctx);
        buffer.writeBoolean(half == BlockHalf.TOP);
    }

    @Override
    public VoxelShape getShape() {
        return half == BlockHalf.TOP ? TOP_SHAPE : BOTTOM_SHAPE;
    }

    @Nullable
    @Override
    public PartModelKey getModelKey() {
        return null;
    }

    public BlockHalf getHalf() {
        return half;
    }
}
