package assets.rivalrebels.client.guihelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSelectionList;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import org.lwjgl.glfw.GLFW;

import java.util.Objects;

public class ReactorDockScroll extends AbstractSelectionList<ReactorDockScroll.WidgetEntry> {
    public ReactorDockScroll(Minecraft minecraft, int width, int height, int x, int y, int itemHeight) {
        super(minecraft, width, height, y, itemHeight);
        this.setX(x);
        this.setRenderHeader(false, 0);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narration) {
    }

    @Override
    protected int getDefaultScrollbarPosition() {
        return this.getX() + getWidth();
    }

    @Override
    public int addEntry(WidgetEntry entry) {
        return super.addEntry(entry);
    }

    public static class WidgetEntry extends AbstractSelectionList.Entry<WidgetEntry> {
        private final BlockPos machinePos;
        private final Block machine;
        private boolean onMachine;

        public WidgetEntry(BlockPos machinePos, Block machine, boolean onMachine) {
            this.machinePos = machinePos;
            this.machine = machine;
            this.onMachine = onMachine;
        }

        public BlockPos getMachinePos() {
            return machinePos;
        }

        public Block getMachine() {
            return machine;
        }

        public void switchEnabledState() {
            this.onMachine = !onMachine;
        }

        public boolean isEnabledMachine() {
            return onMachine;
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            if (isMouseOver(mouseX, mouseY) && button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
                switchEnabledState();
                return true;
            }

            return super.mouseClicked(mouseX, mouseY, button);
        }

        @Override
        public void render(GuiGraphics graphics, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean hovering, float partialTick) {
            float alpha = 0.5f;
            if (onMachine) alpha = 1;
            graphics.setColor(1, 1, 1, alpha);
            graphics.renderItem(machine.asItem().getDefaultInstance(), left + width / 2 - 13, top, 0, 1);
            graphics.setColor(1, 1, 1, 1);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof WidgetEntry that)) return false;
            return Objects.equals(machine, that.machine);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(machine);
        }
    }
}
