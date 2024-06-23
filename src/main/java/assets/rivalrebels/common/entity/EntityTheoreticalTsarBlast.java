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
package assets.rivalrebels.common.entity;

import assets.rivalrebels.RivalRebels;
import assets.rivalrebels.common.explosion.TsarBomba;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class EntityTheoreticalTsarBlast extends EntityTsarBlast {
    public EntityTheoreticalTsarBlast(EntityType<? extends EntityTheoreticalTsarBlast> type, Level world) {
        super(type, world);
    }

    public EntityTheoreticalTsarBlast(Level par1World) {
        this(RREntities.THEORETICAL_TSAR_BLAST, par1World);
        noCulling = true;
    }

    public EntityTheoreticalTsarBlast(Level par1World, float x, float y, float z, TsarBomba tsarBomba, int rad) {
        this(par1World);
        tsar = tsarBomba;
        radius = rad;
        setDeltaMovement(Math.sqrt(radius - RivalRebels.tsarBombaStrength) / 10, getDeltaMovement().y(), getDeltaMovement().z());
        setPos(x, y, z);
    }
}
