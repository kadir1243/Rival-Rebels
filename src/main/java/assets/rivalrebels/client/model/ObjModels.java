package assets.rivalrebels.client.model;

import assets.rivalrebels.client.objfileloader.ModelFromObj;
import assets.rivalrebels.client.objfileloader.WavefrontObject;
import assets.rivalrebels.client.renderhelper.RenderHelper;

public class ObjModels {
    public static final ModelFromObj b2ForRhodes = ModelFromObj.readObjFile("d.obj");
    public static final ModelFromObj b2jetForRhodes = ModelFromObj.readObjFile("s.obj");
    public static final WavefrontObject head = RenderHelper.getModel("rhodes/head");
    public static final WavefrontObject torso = RenderHelper.getModel("rhodes/torso");
    public static final WavefrontObject flag = RenderHelper.getModel("rhodes/flag");
    public static final WavefrontObject upperarm = RenderHelper.getModel("rhodes/upperarm");
    public static final WavefrontObject lowerarm = RenderHelper.getModel("rhodes/lowerarm");
    public static final WavefrontObject rhodes_flamethrower = RenderHelper.getModel(("rhodes/flamethrower"));
    public static final WavefrontObject rhodes_rocketlauncher = RenderHelper.getModel(("rhodes/rocketlauncher"));
    public static final WavefrontObject thigh = RenderHelper.getModel(("rhodes/thigh"));
    public static final WavefrontObject shin = RenderHelper.getModel(("rhodes/shin"));
    public static final WavefrontObject booster = RenderHelper.getModel(("booster"));
    public static final WavefrontObject rhodes_flame = RenderHelper.getModel(("rhodes/flame"));
    public static final WavefrontObject rhodes_laser = RenderHelper.getModel(("rhodes/laser"));
    public static final WavefrontObject ffhead = RenderHelper.getModel(("rhodes/ffhead"));
    public static final WavefrontObject fftorso = RenderHelper.getModel(("rhodes/fftorso"));
    public static final WavefrontObject ffupperarm = RenderHelper.getModel("rhodes/ffupperarm");
    public static final WavefrontObject fflowerarm = RenderHelper.getModel("rhodes/fflowerarm");
    public static final WavefrontObject ffthigh = RenderHelper.getModel("rhodes/ffthigh");
    public static final WavefrontObject ffshin = RenderHelper.getModel("rhodes/ffshin");
    public static final WavefrontObject bomb = RenderHelper.getModel("t");
    public static final WavefrontObject nuke = RenderHelper.getModel("wacknuke");
    public static final ModelFromObj b2ForSpirit = ModelFromObj.readObjFile("d.obj");
    public static final ModelFromObj shuttle = ModelFromObj.readObjFile("shuttle.obj");
    public static final ModelFromObj tupolev = ModelFromObj.readObjFile("tupolev.obj");
    public static final ModelFromObj battery = ModelFromObj.readObjFile("k.obj");
    public static final ModelFromObj binoculars = ModelFromObj.readObjFile("b.obj");
    public static final ModelFromObj flamethrower = ModelFromObj.readObjFile("n.obj");
    public static final ModelFromObj gas = ModelFromObj.readObjFile("o.obj");
    public static final ModelFromObj plasma_cannon = ModelFromObj.readObjFile("m.obj");
    public static final ModelFromObj roda = ModelFromObj.readObjFile("e.obj");
    public static final ModelFromObj tesla = ModelFromObj.readObjFile("i.obj");
    public static final ModelFromObj dynamo = ModelFromObj.readObjFile("j.obj");
    public static final ModelFromObj b83 = ModelFromObj.readObjFile("c.obj");
    public static final ModelFromObj b2FragSide1 = ModelFromObj.readObjFile("f.obj");
    public static final ModelFromObj b2FragSide2 = ModelFromObj.readObjFile("g.obj");
    public static final ModelFromObj electrode = ModelFromObj.readObjFile("a.obj");
    public static final ModelFromObj tube = ModelFromObj.readObjFile("l.obj");
    public static final ModelFromObj tray = ModelFromObj.readObjFile("p.obj");
    public static final ModelFromObj arm = ModelFromObj.readObjFile("q.obj");
    public static final ModelFromObj adsdragon = ModelFromObj.readObjFile("r.obj");

    static {
        b2ForRhodes.scale(2.5f, 2.5f, 2.5f);
        b2jetForRhodes.scale(2.5f, 2.5f, 2.5f);
        b2ForSpirit.scale(3, 3, 3);
        b2FragSide1.scale(3, 3, 3);
        b2FragSide2.scale(3, 3, 3);
    }
}
