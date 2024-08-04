package assets.rivalrebels.common.util;

import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

/**
 * Wrapper for Loader Specific Block Tags
 */
public class ModBlockTags {
    public static final TagKey<Block> GLASS_BLOCKS = ConventionalBlockTags.GLASS_BLOCKS;
    public static final TagKey<Block> GLASS_PANES = ConventionalBlockTags.GLASS_PANES;
    public static final TagKey<Block> SANDSTONE_BLOCKS = ConventionalBlockTags.SANDSTONE_BLOCKS;

}
