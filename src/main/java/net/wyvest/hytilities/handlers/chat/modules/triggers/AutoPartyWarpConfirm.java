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

package net.wyvest.hytilities.handlers.chat.modules.triggers;

import gg.essential.api.utils.Multithreading;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.wyvest.hytilities.config.HytilitiesConfig;
import net.wyvest.hytilities.handlers.chat.ChatReceiveModule;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class AutoPartyWarpConfirm implements ChatReceiveModule {

    @Override
    public void onMessageReceived(@NotNull ClientChatReceivedEvent event) {
        final String message = EnumChatFormatting.getTextWithoutFormattingCodes(event.message.getUnformattedText());
        if (message.equals("Some players are still in-game, run the command again to confirm warp!")) {
            event.setCanceled(true);
            Multithreading.schedule(() -> Minecraft.getMinecraft().thePlayer.sendChatMessage("/p warp"), 1500, TimeUnit.MILLISECONDS);
        }
    }

    @Override
    public int getPriority() {
        return 3;
    }

    @Override
    public boolean isEnabled() {
        return HytilitiesConfig.autoPartyWarpConfirm;
    }
}
