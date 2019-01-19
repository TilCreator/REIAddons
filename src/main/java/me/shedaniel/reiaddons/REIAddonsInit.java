package me.shedaniel.reiaddons;

import me.shedaniel.rei.RoughlyEnoughItemsCore;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.util.Identifier;

public class REIAddonsInit implements ClientModInitializer {
    
    public static final Identifier ADDONS_PLUGIN = new Identifier("reiaddons", "addons_plugin");
    
    @Override
    public void onInitializeClient() {
        RoughlyEnoughItemsCore.registerPlugin(ADDONS_PLUGIN, new AddonsPlugin());
    }
    
}
