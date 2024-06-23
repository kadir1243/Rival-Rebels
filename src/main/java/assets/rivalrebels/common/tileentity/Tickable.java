package assets.rivalrebels.common.tileentity;

public interface Tickable {
    default void tick() {
    }

    default void clientTick() {
        tick();
    }

    default void serverTick() {
        tick();
    }
}
