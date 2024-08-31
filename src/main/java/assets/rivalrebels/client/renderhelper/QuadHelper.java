package assets.rivalrebels.client.renderhelper;

import net.fabricmc.fabric.api.renderer.v1.mesh.QuadEmitter;
import net.minecraft.util.CommonColors;
import org.joml.Vector3f;

public class QuadHelper {
    public static void addVertice(int vertexIndex, QuadEmitter emitter, Vector3f v, TextureVertice t) {
        addVertice(vertexIndex, emitter, v, t, CommonColors.WHITE);
    }

    public static void addVertice(int vertexIndex, QuadEmitter emitter, Vector3f v, int color) {
        addVertice(vertexIndex, emitter, v, new TextureVertice(16, 16), color);
    }

    public static void addVertice(int vertexIndex, QuadEmitter emitter, Vector3f v, TextureVertice t, int color) {
        emitter.pos(vertexIndex, v)
            .color(vertexIndex, color)
            .uv(vertexIndex, t.x(), t.y())
            .normal(vertexIndex, 0F, 0F, 1F);
    }

    public static void addTri(QuadEmitter emitter, Vector3f v1, Vector3f v2, Vector3f v3, int color) {
        emitter.pos(0, v3).color(0, color);
        emitter.pos(1, v1).color(1, color);
        emitter.pos(2, v2).color(2, color);
        emitter.emit();
    }

    public static void addTri(QuadEmitter emitter, Vector3f v1, Vector3f v2, Vector3f v3, TextureVertice t1, TextureVertice t2, TextureVertice t3, int color) {
        emitter.pos(0, v3).color(0, color).uv(0, t3.x(), t3.y());
        emitter.pos(1, v1).color(1, color).uv(1, t1.x(), t1.y());
        emitter.pos(2, v2).color(2, color).uv(2, t2.x(), t2.y());
        emitter.emit();
    }

    public static void addTri(QuadEmitter emitter, Vector3f v1, Vector3f v2, Vector3f v3, TextureVertice t1, TextureVertice t2, TextureVertice t3) {
        addTri(emitter, v1, v2, v3, t1, t2, t3, CommonColors.WHITE);
    }

    public static void addFace(QuadEmitter emitter, Vector3f v1, Vector3f v2, Vector3f v3, Vector3f v4, TextureVertice t1, TextureVertice t2, TextureVertice t3, TextureVertice t4) {
        Vector3f mv = new Vector3f((v1.x + v2.x + v3.x + v4.x) / 4, (v1.y + v2.y + v3.y + v4.y) / 4, (v1.z + v2.z + v3.z + v4.z) / 4);
        TextureVertice mt = new TextureVertice((t1.x() + t2.x() + t3.x() + t4.x()) / 4, (t1.y() + t2.y() + t3.y() + t4.y()) / 4);
        addTri(emitter, v1, v2, mv, t1, t2, mt);

        addTri(emitter, v2, v3, mv, t2, t3, mt);

        addTri(emitter, v3, v4, mv, t3, t4, mt);

        addTri(emitter, v4, v1, mv, t4, t1, mt);
    }

    public static void addFace(QuadEmitter emitter, Vector3f v1, Vector3f v2, Vector3f v3, Vector3f v4, TextureFace t) {
        addVertice(0, emitter, v1, t.v1());
        addVertice(1, emitter, v2, t.v2());
        addVertice(2, emitter, v3, t.v3());
        addVertice(3, emitter, v4, t.v4());
        emitter.emit();
    }

    public static void addFace(QuadEmitter emitter, Vector3f v1, Vector3f v2, Vector3f v3, Vector3f v4, float x1, float x2, float y2, float y1) {
        addFace(emitter, v1, v2, v3, v4, new TextureFace(new TextureVertice(x1, y1), new TextureVertice(x2, y1), new TextureVertice(x2, y2), new TextureVertice(x1, y2)));
    }
}
