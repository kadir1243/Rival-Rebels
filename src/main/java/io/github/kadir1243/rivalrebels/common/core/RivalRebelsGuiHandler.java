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
package io.github.kadir1243.rivalrebels.common.core;

import io.github.kadir1243.rivalrebels.RRIdentifiers;
import io.github.kadir1243.rivalrebels.client.gui.*;
import io.github.kadir1243.rivalrebels.common.container.*;
import net.minecraft.core.registries.Registries;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class RivalRebelsGuiHandler {
    private static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, RRIdentifiers.MODID);
    public static final DeferredHolder<MenuType<?>, MenuType<ContainerTsar>> TSAR_SCREEN_HANDLER_TYPE = MENUS.register("tsar", () -> new MenuType<>(ContainerTsar::new, FeatureFlags.DEFAULT_FLAGS));
    public static final DeferredHolder<MenuType<?>, MenuType<ContainerAntimatterBomb>> ANTIMATTER_SCREEN_HANDLER_TYPE = MENUS.register("antimatter", () -> new MenuType<>(ContainerAntimatterBomb::new, FeatureFlags.DEFAULT_FLAGS));
    public static final DeferredHolder<MenuType<?>, MenuType<ContainerReactor>> REACTOR_SCREEN_HANDLER_TYPE = MENUS.register("reactor", () -> new MenuType<>(ContainerReactor::new, FeatureFlags.DEFAULT_FLAGS));
    public static final DeferredHolder<MenuType<?>, MenuType<ContainerLaptop>> LAPTOP_SCREEN_HANDLER_TYPE = MENUS.register("laptop", () -> new MenuType<>(ContainerLaptop::new, FeatureFlags.DEFAULT_FLAGS));
    public static final DeferredHolder<MenuType<?>, MenuType<ContainerLoader>> LOADER_SCREEN_HANDLER_TYPE = MENUS.register("loader", () -> new MenuType<>(ContainerLoader::new, FeatureFlags.DEFAULT_FLAGS));
    public static final DeferredHolder<MenuType<?>, MenuType<ContainerReciever>> RECIEVER_SCREEN_HANDLER_TYPE = MENUS.register("reciever", () -> new MenuType<>(ContainerReciever::new, FeatureFlags.DEFAULT_FLAGS));
    public static final DeferredHolder<MenuType<?>, MenuType<ContainerNuclearBomb>> NUCLEAR_BOMB_SCREEN_HANDLER_TYPE = MENUS.register("nuclear_bomb", () -> new MenuType<>(ContainerNuclearBomb::new, FeatureFlags.DEFAULT_FLAGS));
    public static final DeferredHolder<MenuType<?>, MenuType<ContainerTachyonBomb>> TACHYON_SCREEN_HANDLER_TYPE = MENUS.register("tachyon", () -> new MenuType<>(ContainerTachyonBomb::new, FeatureFlags.DEFAULT_FLAGS));
    public static final DeferredHolder<MenuType<?>, MenuType<ContainerTheoreticalTsar>> THEORETICAL_TSAR_SCREEN_HANDLER_TYPE = MENUS.register("theoretical_tsar", () -> new MenuType<>(ContainerTheoreticalTsar::new, FeatureFlags.DEFAULT_FLAGS));

    @OnlyIn(Dist.CLIENT)
    private static void registerClientGuiBinds(RegisterMenuScreensEvent event) {
        event.register(TSAR_SCREEN_HANDLER_TYPE.get(), GuiTsar::new);
        event.register(ANTIMATTER_SCREEN_HANDLER_TYPE.get(), GuiAntimatterBomb::new);
        event.register(REACTOR_SCREEN_HANDLER_TYPE.get(), GuiReactor::new);
        event.register(LAPTOP_SCREEN_HANDLER_TYPE.get(), GuiLaptop::new);
        event.register(LOADER_SCREEN_HANDLER_TYPE.get(), GuiLoader::new);
        event.register(RECIEVER_SCREEN_HANDLER_TYPE.get(), GuiTray::new);
        event.register(NUCLEAR_BOMB_SCREEN_HANDLER_TYPE.get(), GuiNuclearBomb::new);
        event.register(TACHYON_SCREEN_HANDLER_TYPE.get(), GuiTachyonBomb::new);
        event.register(THEORETICAL_TSAR_SCREEN_HANDLER_TYPE.get(), GuiTheoreticalTsar::new);
    }

    public static void init(IEventBus bus, Dist dist) {
        MENUS.register(bus);
        if (dist.isClient()) {
            bus.addListener(RivalRebelsGuiHandler::registerClientGuiBinds);
        }
    }
}
