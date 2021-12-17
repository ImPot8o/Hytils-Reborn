/*
 * Hytilities Reborn - Hypixel focused Quality of Life mod.
 * Copyright (C) 2021  W-OVERFLOW
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package net.wyvest.hytilities.hooks;

import net.wyvest.hytilities.config.HytilitiesConfig;
import net.wyvest.hytilities.handlers.cache.HeightHandler;
import net.wyvest.hytilities.util.ColorUtils;
import net.minecraft.block.BlockColored;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

public class BlockModelRendererHook {

    public static void handleHeightOverlay(Args args, IBlockState stateIn, BlockPos blockPosIn) {
        if (HytilitiesConfig.heightOverlay && stateIn.getBlock() instanceof BlockColored) {
            int height = HeightHandler.INSTANCE.getHeight();
            if (height == -1) {
                return;
            }
            MapColor mapColor = stateIn.getBlock().getMapColor(stateIn);
            if (blockPosIn.getY() == (height - 1) && mapColor != null && (!(stateIn.getBlock().getMaterial() == Material.rock) || check(mapColor.colorIndex))) {
                int color = ColorUtils.getCachedDarkColor(mapColor);
                args.set(0, (float) ColorUtils.getRed(color) / 255);
                args.set(1, (float) ColorUtils.getGreen(color) / 255);
                args.set(2, (float) ColorUtils.getBlue(color) / 255);
            }
        }
    }

    private static boolean check(int color) {
        switch (color) {
            case 18:
            case 25:
            case 27:
            case 28:
                return true;
            default:
                return false;
        }
    }
}