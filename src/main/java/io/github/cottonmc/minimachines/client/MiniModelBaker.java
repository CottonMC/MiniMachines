package io.github.cottonmc.minimachines.client;

import alexiil.mc.lib.multipart.api.render.PartModelBaker;
import alexiil.mc.lib.multipart.api.render.PartRenderContext;
import io.github.cottonmc.minimachines.part.MiniModelKey;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.ModelIdentifier;

@Environment(EnvType.CLIENT)
public enum MiniModelBaker implements PartModelBaker<MiniModelKey> {
    INSTANCE;

    @Override
    public void emitQuads(MiniModelKey key, PartRenderContext context) {
        context.fallbackConsumer().accept(
                MinecraftClient.getInstance()
                        .getBakedModelManager()
                        .getModel(new ModelIdentifier(key.getPart().definition.identifier, "half=" + key.getPart().getHalf().asString()))
        );
    }
}
