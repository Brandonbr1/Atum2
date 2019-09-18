package com.teammetallurgy.atum.api.recipe.spinningwheel;

import com.teammetallurgy.atum.utils.StackHelper;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.Tag;
import net.minecraft.util.NonNullList;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nonnull;

public class SpinningWheelRecipe extends ForgeRegistryEntry<ISpinningWheelRecipe> implements ISpinningWheelRecipe {
    private final NonNullList<ItemStack> inputs;
    private final ItemStack output;
    private final int rotations;

    public SpinningWheelRecipe(Block input, @Nonnull ItemStack output, int rotations) {
        this(new ItemStack(input), output, rotations);
    }

    public SpinningWheelRecipe(Item input, @Nonnull ItemStack output, int rotations) {
        this(new ItemStack(input), output, rotations);
    }

    public SpinningWheelRecipe(@Nonnull ItemStack input, @Nonnull ItemStack output, int rotations) {
        this(NonNullList.withSize(1, input), output, rotations);
    }

    public SpinningWheelRecipe(Tag<Item> input, @Nonnull ItemStack output, int rotations) {
        this(Ingredient.fromTag(input), output, rotations);
    }

    public SpinningWheelRecipe(Ingredient input, @Nonnull ItemStack output, int rotations) {
        this(NonNullList.from(ItemStack.EMPTY, input.getMatchingStacks()), output, rotations);
    }

    private SpinningWheelRecipe(NonNullList<ItemStack> input, @Nonnull ItemStack output, int rotations) {
        this.inputs = input;
        this.output = output;
        this.rotations = rotations;
    }

    @Override
    public NonNullList<ItemStack> getInput() {
        return this.inputs;
    }

    @Override
    public boolean isValidInput(@Nonnull ItemStack stack) {
        for (final ItemStack validInput : this.inputs) {
            if (StackHelper.areStacksEqualIgnoreSize(stack, validInput)) {
                return true;
            }
        }
        return false;
    }

    @Override
    @Nonnull
    public ItemStack getOutput() {
        return this.output;
    }

    @Override
    public int getRotations() {
        return this.rotations;
    }
}