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
package assets.rivalrebels.common.round;

public enum RivalRebelsTeam
{
	NONE(0),
	OMEGA(1),
	SIGMA(2);

	public final int id;

	RivalRebelsTeam(int i)
	{
		id = i;
	}

	public static RivalRebelsTeam getForID(int i)
	{
        return switch (i) {
            case 1 -> OMEGA;
            case 2 -> SIGMA;
            default -> NONE;
        };
    }
}
