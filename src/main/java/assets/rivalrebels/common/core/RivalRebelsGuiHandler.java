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

import assets.rivalrebels.RRIdentifiers;
import assets.rivalrebels.client.gui.*;
import assets.rivalrebels.common.container.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

public class RivalRebelsGuiHandler {
    public static final MenuType<ContainerTsar> TSAR_SCREEN_HANDLER_TYPE = register("tsar", new MenuType<>(ContainerTsar::new, FeatureFlags.DEFAULT_FLAGS));
    public static final MenuType<ContainerAntimatterBomb> ANTIMATTER_SCREEN_HANDLER_TYPE = register("antimatter", new MenuType<>(ContainerAntimatterBomb::new, FeatureFlags.DEFAULT_FLAGS));
    public static final MenuType<ContainerReactor> REACTOR_SCREEN_HANDLER_TYPE = register("reactor", new MenuType<>(ContainerReactor::new, FeatureFlags.DEFAULT_FLAGS));
    public static final MenuType<ContainerLaptop> LAPTOP_SCREEN_HANDLER_TYPE = register("laptop", new MenuType<>(ContainerLaptop::new, FeatureFlags.DEFAULT_FLAGS));
    public static final MenuType<ContainerLoader> LOADER_SCREEN_HANDLER_TYPE = register("loader", new MenuType<>(ContainerLoader::new, FeatureFlags.DEFAULT_FLAGS));
    public static final MenuType<ContainerReciever> RECIEVER_SCREEN_HANDLER_TYPE = register("reciever", new MenuType<>(ContainerReciever::new, FeatureFlags.DEFAULT_FLAGS));
    public static final MenuType<ContainerNuclearBomb> NUCLEAR_BOMB_SCREEN_HANDLER_TYPE = register("nuclear_bomb", new MenuType<>(ContainerNuclearBomb::new, FeatureFlags.DEFAULT_FLAGS));
    public static final MenuType<ContainerTachyonBomb> TACHYON_SCREEN_HANDLER_TYPE = register("tachyon", new MenuType<>(ContainerTachyonBomb::new, FeatureFlags.DEFAULT_FLAGS));
    public static final MenuType<ContainerTheoreticalTsar> THEORETICAL_TSAR_SCREEN_HANDLER_TYPE = register("theoretical_tsar", new MenuType<>(ContainerTheoreticalTsar::new, FeatureFlags.DEFAULT_FLAGS));

    @Environment(EnvType.CLIENT)
    public static void registerClientGuiBinds() {
        MenuScreens.register(TSAR_SCREEN_HANDLER_TYPE, GuiTsar::new);
        MenuScreens.register(ANTIMATTER_SCREEN_HANDLER_TYPE, GuiAntimatterBomb::new);
        MenuScreens.register(REACTOR_SCREEN_HANDLER_TYPE, GuiReactor::new);
        MenuScreens.register(LAPTOP_SCREEN_HANDLER_TYPE, GuiLaptop::new);
        MenuScreens.register(LOADER_SCREEN_HANDLER_TYPE, GuiLoader::new);
        MenuScreens.register(RECIEVER_SCREEN_HANDLER_TYPE, GuiTray::new);
        MenuScreens.register(NUCLEAR_BOMB_SCREEN_HANDLER_TYPE, GuiNuclearBomb::new);
        MenuScreens.register(TACHYON_SCREEN_HANDLER_TYPE, GuiTachyonBomb::new);
        MenuScreens.register(THEORETICAL_TSAR_SCREEN_HANDLER_TYPE, GuiTheoreticalTsar::new);
    }

    public static <U extends AbstractContainerMenu, T extends MenuType<U>> T register(String name, T type) {
        Registry.register(BuiltInRegistries.MENU, RRIdentifiers.create(name), type);
        return type;
    }

    public static void init() {
    }
}
