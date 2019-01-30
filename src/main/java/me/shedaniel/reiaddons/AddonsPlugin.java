package me.shedaniel.reiaddons;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import me.shedaniel.rei.api.IRecipePlugin;
import me.shedaniel.rei.client.RecipeHelper;
import net.minecraft.block.ComposterBlock;
import net.minecraft.item.ItemProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AddonsPlugin implements IRecipePlugin {
    
    static final Identifier COMPOSTING = new Identifier("reiaddons", "plugins/composting");
    
    @Override
    public void registerPluginCategories() {
        RecipeHelper.getInstance().registerCategory(new AddonsCompostingCategory());
    }
    
    @Override
    public void registerRecipes() {
        Map<ItemProvider, Float> map = Maps.newHashMap();
        if (ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.isEmpty())
            ComposterBlock.registerDefaultCompostableItems();
        ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.keySet().forEach(itemProvider -> {
            float chance = ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.get(itemProvider);
            if (chance > 0)
                map.put(itemProvider, chance);
        });
        List<ItemProvider> stacks = new LinkedList<>(map.keySet());
        stacks.sort((first, second) -> {
            return (int) ((map.get(first) - map.get(second)) * 100);
        });
        for(int i = 0; i < stacks.size(); i += MathHelper.clamp(48, 1, stacks.size() - i)) {
            List<ItemProvider> thisStacks = Lists.newArrayList();
            for(int j = i; j < i + 48; j++)
                if (j < stacks.size())
                    thisStacks.add(stacks.get(j));
            RecipeHelper.getInstance().registerRecipe(COMPOSTING, new AddonsCompostingDisplay(thisStacks, map, new ItemStack[]{new ItemStack(Items.BONE_MEAL)}));
        }
    }
    
    @Override
    public void registerSpeedCraft() {
        RecipeHelper.getInstance().registerSpeedCraftButtonArea(COMPOSTING, null);
    }
    
}
