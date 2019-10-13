package io.github.cottonmc.minimachines.part;

import alexiil.mc.lib.multipart.api.AbstractPart;
import com.google.common.collect.ImmutableMap;
import net.minecraft.state.AbstractPropertyContainer;
import net.minecraft.state.property.Property;

public class MultipartState extends AbstractPropertyContainer<AbstractPart, MultipartState> {
    public MultipartState(AbstractPart part, ImmutableMap<Property<?>, Comparable<?>> properties) {
        super(part, properties);
    }
}
