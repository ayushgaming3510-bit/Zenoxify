package com.example.zoommod;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;

public class ZoomModClient implements ClientModInitializer {
    public static final String MOD_ID = "zoommod";

    private static KeyMapping zoomKey;

    @Override
    public void onInitializeClient() {
        KeyMapping.Category category = KeyMapping.Category.register(
                Identifier.fromNamespaceAndPath(MOD_ID, "main"));

        // Default key is C. Players can change it in Options > Controls > Key Binds.
        zoomKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.zoommod.zoom",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_C,
                category));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            boolean zoom = client.player != null
                    && client.screen == null
                    && zoomKey.isDown();
            ZoomHandler.setZooming(zoom);
        });
    }
}
