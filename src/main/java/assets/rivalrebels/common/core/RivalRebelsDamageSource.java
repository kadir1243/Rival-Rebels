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

import net.minecraft.entity.damage.DamageSource;

public class RivalRebelsDamageSource {
    public static final DamageSource electricity = new DamageSource("electricity").setBypassesArmor().setUnblockable();
    public static final DamageSource radioactivepoisoning = new DamageSource("radioactivepoisoning").setBypassesArmor().setUnblockable();
    public static final DamageSource nuclearblast = new DamageSource("nuclearblast").setUnblockable();
    public static final DamageSource cooked = new DamageSource("cooked").setUnblockable();
    public static final DamageSource gasgrenade = new DamageSource("gasgrenade").setUnblockable();
    public static final DamageSource cuchillo = new DamageSource("cuchillo");
    public static final DamageSource tron = new DamageSource("tron").setUnblockable();
    public static final DamageSource cyanide = new DamageSource("cyanide").setUnblockable();
    public static final DamageSource landmine = new DamageSource("landmine").setUnblockable();
    public static final DamageSource timebomb = new DamageSource("timebomb").setUnblockable();
    public static final DamageSource flare = new DamageSource("flare").setUnblockable();
    public static final DamageSource charge = new DamageSource("charge").setUnblockable();
    public static final DamageSource plasmaexplosion = new DamageSource("plasmaexplosion").setUnblockable();
    public static final DamageSource rocket = new DamageSource("rocket").setUnblockable();
    public static final DamageSource laserburst = new DamageSource("laserburst").setUnblockable();
}
