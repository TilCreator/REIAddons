package me.shedaniel.reiaddons;

import me.shedaniel.rei.api.RecipeDisplay;
import net.minecraft.item.Item;
import net.minecraft.item.ItemProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.util.Identifier;

import java.util.*;
import java.util.stream.Collectors;

public class AddonsCompostingDisplay implements RecipeDisplay {
    
    private List<ItemProvider> order, allItems;
    private Map<ItemProvider, Float> inputMap;
    private ItemStack[] output;
    
    public AddonsCompostingDisplay(List<ItemProvider> order, Map<ItemProvider, Float> inputMap, List<ItemProvider> allItems, ItemStack[] output) {
        this.order = order;
        this.inputMap = inputMap;
        this.output = output;
        this.allItems = allItems;
    }
    
    @Override
    public Optional<Recipe> getRecipe() {
        return Optional.empty();
    }
    
    @Override
    public List<List<ItemStack>> getInput() {
        List<List<ItemStack>> lists = new ArrayList<>();
        allItems.stream().forEachOrdered(itemProvider -> {
            lists.add(Arrays.asList(itemProvider.getItem().getDefaultStack()));
        });
        return lists;
    }
    
    public Map<ItemProvider, Float> getInputMap() {
        return inputMap;
    }
    
    @Override
    public List<ItemStack> getOutput() {
        return Arrays.asList(output);
    }
    
    @Override
    public Identifier getRecipeCategory() {
        return AddonsPlugin.COMPOSTING;
    }
    
    @Override
    public List<List<ItemStack>> getRequiredItems() {
        return Arrays.asList(new LinkedList<>(allItems.stream().map(ItemProvider::getItem).map(Item::getDefaultStack).collect(Collectors.toList())));
    }
    
    public List<ItemProvider> getItemsByOrder() {
        return order;
    }
    
}
