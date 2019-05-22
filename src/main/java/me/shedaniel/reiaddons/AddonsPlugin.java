package me.shedaniel.reiaddons;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import me.shedaniel.rei.api.REIPluginEntry;
import me.shedaniel.rei.api.RecipeHelper;
import net.minecraft.block.ComposterBlock;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AddonsPlugin implements REIPluginEntry {
    
    public static final Identifier PLUGIN = new Identifier("reiaddons", "addons_plugin");
    public static final Identifier COMPOSTING = new Identifier("reiaddons", "plugins/composting");
    
    @Override
    public void registerPluginCategories(RecipeHelper recipeHelper) {
        recipeHelper.registerCategory(new AddonsCompostingCategory());
    }
    
    @Override
    public void registerRecipeDisplays(RecipeHelper recipeHelper) {
        Map<ItemConvertible, Float> map = Maps.newLinkedHashMap();
        if (ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.isEmpty())
            ComposterBlock.registerDefaultCompostableItems();
        ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.keySet().forEach(itemConvertible -> {
            float chance = ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.get(itemConvertible);
            if (chance > 0)
                map.put(itemConvertible, chance);
        });
        List<ItemConvertible> stacks = new LinkedList<>(map.keySet());
        stacks.sort((first, second) -> {
            return (int) ((map.get(first) - map.get(second)) * 100);
        });
        for(int i = 0; i < stacks.size(); i += MathHelper.clamp(48, 1, stacks.size() - i)) {
            List<ItemConvertible> thisStacks = Lists.newArrayList();
            for(int j = i; j < i + 48; j++)
                if (j < stacks.size())
                    thisStacks.add(stacks.get(j));
            recipeHelper.registerDisplay(COMPOSTING, new AddonsCompostingDisplay(MathHelper.floor(i / 48f), thisStacks, map, Lists.newArrayList(map.keySet()), new ItemStack[]{new ItemStack(Items.BONE_MEAL)}));
        }
    }
    
    @Override
    public Identifier getPluginIdentifier() {
        return PLUGIN;
    }
    
}
