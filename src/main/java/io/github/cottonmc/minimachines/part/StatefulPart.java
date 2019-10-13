package io.github.cottonmc.minimachines.part;

import alexiil.mc.lib.multipart.api.AbstractPart;
import alexiil.mc.lib.multipart.api.MultipartHolder;
import alexiil.mc.lib.multipart.api.PartDefinition;
import alexiil.mc.lib.net.IMsgWriteCtx;
import alexiil.mc.lib.net.NetByteBuf;
import com.mojang.datafixers.DataFixUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.state.StateFactory;
import net.minecraft.state.property.Property;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;
import java.util.Optional;

public abstract class StatefulPart extends AbstractPart {
    private static final Logger LOGGER = LogManager.getLogger();
    protected final StateFactory<AbstractPart, MultipartState> stateFactory;
    protected MultipartState state;

    public StatefulPart(PartDefinition definition, MultipartHolder holder) {
        super(definition, holder);
        StateFactory.Builder<AbstractPart, MultipartState> stateBuilder = new StateFactory.Builder<>(this);
        appendProperties(stateBuilder);
        stateFactory = stateBuilder.build(MultipartState::new);
        state = stateFactory.getDefaultState();
    }

    public StatefulPart(PartDefinition definition, MultipartHolder holder, CompoundTag tag) {
        this(definition, holder);
        state = readState(stateFactory, definition, Objects.requireNonNull(tag));
    }

    @SuppressWarnings("unchecked")
    private CompoundTag statesToTag() {
        return DataFixUtils.make(new CompoundTag(), tag -> {
            for (Property<?> property : state.getProperties()) {
                Property<Comparable> unsafe = (Property<Comparable>) property;
                tag.putString(property.getName(), unsafe.getName(state.get(property)));
            }
        });
    }

    @Override
    public CompoundTag toTag() {
        CompoundTag tag = super.toTag();
        tag.put("State", statesToTag());
        return tag;
    }

    @Override
    public void writeCreationData(NetByteBuf buffer, IMsgWriteCtx ctx) {
        super.writeCreationData(buffer, ctx);
        buffer.writeCompoundTag(statesToTag());
    }

    protected abstract void appendProperties(StateFactory.Builder<AbstractPart, MultipartState> builder);

    @SuppressWarnings("unchecked")
    private static MultipartState readState(StateFactory<AbstractPart, MultipartState> stateFactory, PartDefinition definition, CompoundTag tag) {
        MultipartState state = stateFactory.getDefaultState();
        CompoundTag stateTag = tag.getCompound("State");
        for (String key : stateTag.getKeys()) {
            Property<? extends Comparable> property = stateFactory.getProperty(key);
            if (property == null) {
                LOGGER.warn("Found unknown property '{}' in serialized form of multipart {}", key, definition.identifier);
                continue;
            }
            Optional<? extends Comparable> value = property.getValue(stateTag.getString(key));
            if (!value.isPresent()) {
                LOGGER.warn("Found invalid value '{}' for property '{}' in serialized form of multipart {}", stateTag.getString(key), key, definition.identifier);
                continue;
            }

            state = state.with((Property<Comparable>) property, value.get());
        }

        return state;
    }
}
