package me.shedaniel.reiaddons;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.GlStateManager;
import me.shedaniel.rei.api.IRecipeCategory;
import me.shedaniel.rei.gui.widget.IWidget;
import me.shedaniel.rei.gui.widget.ItemSlotWidget;
import me.shedaniel.rei.gui.widget.RecipeBaseWidget;
import me.shedaniel.rei.listeners.IMixinContainerGui;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GuiLighting;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.item.ItemProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

import java.awt.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class AddonsCompostingCategory implements IRecipeCategory<AddonsCompostingDisplay> {
    
    private static final Identifier DISPLAY_TEXTURE = new Identifier("reiaddons", "textures/gui/display.png");
    
    @Override
    public Identifier getIdentifier() {
        return AddonsPlugin.COMPOSTING;
    }
    
    @Override
    public ItemStack getCategoryIcon() {
        return new ItemStack(Blocks.COMPOSTER);
    }
    
    @Override
    public String getCategoryName() {
        return I18n.translate("category.reiaddons.composting");
    }
    
    @Override
    public boolean usesFullPage() {
        return true;
    }
    
    @Override
    public List<IWidget> setupDisplay(IMixinContainerGui containerGui, AddonsCompostingDisplay recipeDisplay, Rectangle bounds) {
        List<IWidget> widgets = Lists.newArrayList();
        Point startingPoint = new Point(bounds.x + bounds.width - 55, bounds.y + 110);
        widgets.add(new RecipeBaseWidget(bounds) {
            @Override
            public void draw(int mouseX, int mouseY, float partialTicks) {
                GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                GuiLighting.disable();
                MinecraftClient.getInstance().getTextureManager().bindTexture(DISPLAY_TEXTURE);
                this.drawTexturedRect(startingPoint.x, startingPoint.y, 0, 0, 55, 26);
            }
        });
        List<ItemProvider> stacks = new LinkedList<>(recipeDisplay.getItemsByOrder());
        int i = 0;
        for(int y = 0; y < 6; y++)
            for(int x = 0; x < 8; x++) {
                widgets.add(new ItemSlotWidget((int) bounds.getCenterX() - 72 + x * 18, bounds.y + y * 18,
                        stacks.size() > i ? Arrays.asList(stacks.get(i).getItem().getDefaultStack()) : Lists.newArrayList(),
                        true, true, containerGui, true) {
                    @Override
                    protected List<String> getExtraToolTips(ItemStack stack) {
                        final List<String>[] thing = new List[]{null};
                        recipeDisplay.getInputMap().forEach((itemProvider, aFloat) -> {
                            if (itemProvider.getItem().equals(stack.getItem()))
                                thing[0] = Arrays.asList(I18n.translate("text.reiaddons.composting.chance", MathHelper.fastFloor(aFloat * 100)));
                        });
                        if (thing[0] != null)
                            return thing[0];
                        return super.getExtraToolTips(stack);
                    }
                });
                i++;
            }
        widgets.add(new ItemSlotWidget((int) startingPoint.x + 34, startingPoint.y + 5, recipeDisplay.getOutput(), false, true, containerGui, true));
        return widgets;
    }
    
}
