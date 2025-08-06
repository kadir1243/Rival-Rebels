package io.github.kadir1243.rivalrebels.client.renderhelper;

import io.github.kadir1243.rivalrebels.RRIdentifiers;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RRTextures {
    public static final Texture guitrivalrebels = new Texture("textures/gui/h.png", 931, 120);
    public static final Texture guitbutton = new Texture("textures/gui/a.png", 512, 512);
    public static final ResourceLocation button_disabled = RRIdentifiers.create("textures/gui/button_disabled.png");
    public static final ResourceLocation button_enabled = RRIdentifiers.create("textures/gui/button_enabled.png");
    public static final ResourceLocation button_hovered = RRIdentifiers.create("textures/gui/button_hovered.png");
    public static final ResourceLocation guitspawn = RRIdentifiers.create("textures/gui/b.png");
    public static final ResourceLocation guitclass = RRIdentifiers.create("textures/gui/c.png");
    public static final Texture guitrebel = new Texture("textures/gui/d.png", 512, 512);
    public static final Texture guitnuker = new Texture("textures/gui/e.png", 512, 512);
    public static final Texture guitintel = new Texture("textures/gui/f.png", 512, 512);
    public static final Texture guithacker = new Texture("textures/gui/g.png", 512, 512);
    public static final Texture guitnuke = new Texture("textures/gui/j.png", 256, 256);
    public static final Texture guittsar = new Texture("textures/gui/k.png", 512, 512);
    public static final ResourceLocation guitwarning0 = RRIdentifiers.create("textures/gui/l.png");
    public static final ResourceLocation guitwarning1 = RRIdentifiers.create("textures/gui/m.png");
    public static final Texture guitloader = new Texture("textures/gui/i.png", 512, 512);
    public static final Texture guittokamak = new Texture("textures/gui/n.png", 512, 512);
    public static final ResourceLocation guibinoculars = RRIdentifiers.create("textures/gui/o.png");
    public static final ResourceLocation guibinocularsoverlay = RRIdentifiers.create("textures/gui/p.png");
    public static final Texture guilaptopnuke = new Texture("textures/gui/q.png", 512, 512);
    public static final Texture guitesla = new Texture("textures/gui/r.png", 512, 512);
    public static final Texture guitray = new Texture("textures/gui/s.png", 512, 512);
    public static final ResourceLocation guiflamethrower = RRIdentifiers.create("textures/gui/u.png");
    public static final Texture guirhodesline = new Texture("textures/gui/rhodes-gui_line.png", 1600, 900);
    public static final ResourceLocation guirhodesout = RRIdentifiers.create("textures/gui/rhodes-gui_out.png");
    public static final ResourceLocation guirhodeshelp = RRIdentifiers.create("textures/gui/rhodes-gui_help.png");
    public static final ResourceLocation guicarpet = RRIdentifiers.create("textures/gui/v.png");
    public static final Texture guitheoreticaltsar = new Texture("textures/gui/w.png", 512, 512);
    public static final Texture guitantimatterbomb = new Texture("textures/gui/x.png", 512, 512);
    public static final Texture guitachyonbomb = new Texture("textures/gui/y.png", 512, 512);

    public record Texture(ResourceLocation location, int width, int height) {
        public void blit(GuiGraphics graphics, int x, int y, float uOffset, float vOffset, int uWidth, int vHeight, int color) {
            graphics.blit(RenderPipelines.GUI_TEXTURED, location, x, y, uOffset, vOffset, uWidth, vHeight, width, height, color);
        }

        private Texture(String location, int width, int height) {
            this(RRIdentifiers.create(location), width, height);
        }
    }
}
