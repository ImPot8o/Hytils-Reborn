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

package net.wyvest.hytilities.handlers.chat.modules.events;

import net.wyvest.hytilities.Hytilities;
import net.wyvest.hytilities.config.HytilitiesConfig;
import net.wyvest.hytilities.events.HypixelLevelupEvent;
import net.wyvest.hytilities.handlers.chat.ChatReceiveModule;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;

public class LevelupEvent implements ChatReceiveModule {

    @Override
    public void onMessageReceived(@NotNull ClientChatReceivedEvent event) {
        final String unformattedText = EnumChatFormatting.getTextWithoutFormattingCodes(event.message.getUnformattedText());
        final Matcher matcher = getLanguage().hypixelLevelUpRegex.matcher(unformattedText.trim());
        if (matcher.find()) {
            final String level = matcher.group("level");
            if (StringUtils.isNumeric(level)) {
                MinecraftForge.EVENT_BUS.post(new HypixelLevelupEvent(Integer.parseInt(level)));
            }
        }
    }

    @Override
    public boolean isEnabled() {
        return HytilitiesConfig.broadcastLevelup;
    }

    @Override
    public int getPriority() {
        return -3;
    }

    @SubscribeEvent
    public void levelUpEvent(HypixelLevelupEvent event) {
        Hytilities.INSTANCE.getCommandQueue().queue("/gchat Levelup! I am now Hypixel Level: " + event.getLevel() + "!");
    }
}