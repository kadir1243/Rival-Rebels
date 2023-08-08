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
package assets.rivalrebels.client.renderhelper;

public class TextureFace {
	private final TextureVertice v1;
	private final TextureVertice v2;
	private final TextureVertice v3;
	private final TextureVertice v4;

	public TextureFace(TextureVertice v1, TextureVertice v2, TextureVertice v3, TextureVertice v4) {
		this.v1 = v1;
		this.v2 = v2;
		this.v3 = v3;
		this.v4 = v4;
	}

    public TextureVertice getV1() {
        return v1;
    }

    public TextureVertice getV2() {
        return v2;
    }

    public TextureVertice getV3() {
        return v3;
    }

    public TextureVertice getV4() {
        return v4;
    }
}
