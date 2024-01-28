/*******************************************************************************
 * Copyright (c) 2012, 2016 Rodol Phito.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Mozilla Public License Version 2.0
 * which accompanies this distribution, and is available at
 * https://www.mozilla.org/en-US/MPL/2.0/
 *
 * Rival Rebels Mod. All code, art, and design by Rodol Phito.
 *
 * http://RivalRebels.com/
 *******************************************************************************/
package assets.rivalrebels.common.core;

import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.client.gui.*;
import assets.rivalrebels.common.container.*;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.HashSet;
import java.util.Set;

public class RivalRebelsGuiHandler {
    public static final Set<ScreenHandlerType<?>> SCREEN_HANDLER_TYPES = new HashSet<>();
    public static final ScreenHandlerType<ContainerTsar> TSAR_SCREEN_HANDLER_TYPE = register("tsar", new ScreenHandlerType<>(ContainerTsar::new));
    public static final ScreenHandlerType<ContainerAntimatterBomb> ANTIMATTER_SCREEN_HANDLER_TYPE = register("antimatter", new ScreenHandlerType<>(ContainerAntimatterBomb::new));
    public static final ScreenHandlerType<ContainerReactor> REACTOR_SCREEN_HANDLER_TYPE = register("reactor", new ScreenHandlerType<>(ContainerReactor::new));
    public static final ScreenHandlerType<ContainerLaptop> LAPTOP_SCREEN_HANDLER_TYPE = register("laptop", new ScreenHandlerType<>(ContainerLaptop::new));
    public static final ScreenHandlerType<ContainerLoader> LOADER_SCREEN_HANDLER_TYPE = register("loader", new ScreenHandlerType<>(ContainerLoader::new));
    public static final ScreenHandlerType<ContainerReciever> RECIEVER_SCREEN_HANDLER_TYPE = register("reciever", new ScreenHandlerType<>(ContainerReciever::new));
    public static final ScreenHandlerType<ContainerNuclearBomb> NUCLEAR_BOMB_SCREEN_HANDLER_TYPE = register("nuclear_bomb", new ScreenHandlerType<>(ContainerNuclearBomb::new));
    public static final ScreenHandlerType<ContainerTachyonBomb> TACHYON_SCREEN_HANDLER_TYPE = register("tachyon", new ScreenHandlerType<>(ContainerTachyonBomb::new));
    public static final ScreenHandlerType<ContainerTheoreticalTsar> THEORETICAL_TSAR_SCREEN_HANDLER_TYPE = register("theoretical_tsar", new ScreenHandlerType<>(ContainerTheoreticalTsar::new));

    @OnlyIn(Dist.CLIENT)
    public static void registerClientGuiBinds() {
        HandledScreens.register(TSAR_SCREEN_HANDLER_TYPE, GuiTsar::new);
        HandledScreens.register(ANTIMATTER_SCREEN_HANDLER_TYPE, GuiAntimatterBomb::new);
        HandledScreens.register(REACTOR_SCREEN_HANDLER_TYPE, GuiReactor::new);
        HandledScreens.register(LAPTOP_SCREEN_HANDLER_TYPE, GuiLaptop::new);
        HandledScreens.register(LOADER_SCREEN_HANDLER_TYPE, GuiLoader::new);
        HandledScreens.register(RECIEVER_SCREEN_HANDLER_TYPE, GuiTray::new);
        HandledScreens.register(NUCLEAR_BOMB_SCREEN_HANDLER_TYPE, GuiNuclearBomb::new);
        HandledScreens.register(TACHYON_SCREEN_HANDLER_TYPE, GuiTachyonBomb::new);
        HandledScreens.register(THEORETICAL_TSAR_SCREEN_HANDLER_TYPE, GuiTheoreticalTsar::new);
    }

    public static <U extends ScreenHandler, T extends ScreenHandlerType<U>> T register(String name, T type) {
        type.setRegistryName(RivalRebels.MODID, name);
        SCREEN_HANDLER_TYPES.add(type);
        return type;
    }
}
