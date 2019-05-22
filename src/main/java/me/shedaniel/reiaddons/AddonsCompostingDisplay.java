package me.shedaniel.reiaddons;

import me.shedaniel.rei.api.RecipeDisplay;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.util.Identifier;

import java.util.*;
import java.util.stream.Collectors;

public class AddonsCompostingDisplay implements RecipeDisplay {
    
    private List<ItemConvertible> order, allItems;
    private Map<ItemConvertible, Float> inputMap;
    private ItemStack[] output;
    private int page;
    
    public AddonsCompostingDisplay(int page, List<ItemConvertible> order, Map<ItemConvertible, Float> inputMap, List<ItemConvertible> allItems, ItemStack[] output) {
        this.page = page;
        this.order = order;
        this.inputMap = inputMap;
        this.output = output;
        this.allItems = allItems;
    }
    
    public int getPage() {
        return page;
    }
    
    @Override
    public Optional<Recipe> getRecipe() {
        return Optional.empty();
    }
    
    @Override
    public List<List<ItemStack>> getInput() {
        List<List<ItemStack>> lists = new ArrayList<>();
        allItems.stream().forEachOrdered(itemProvider -> {
            lists.add(Arrays.asList(itemProvider.asItem().getDefaultStack()));
        });
        return lists;
    }
    
    public Map<ItemConvertible, Float> getInputMap() {
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
        return Arrays.asList(new LinkedList<>(allItems.stream().map(ItemConvertible::asItem).map(Item::getDefaultStack).collect(Collectors.toList())));
    }
    
    public List<ItemConvertible> getItemsByOrder() {
        return order;
    }
    
}
