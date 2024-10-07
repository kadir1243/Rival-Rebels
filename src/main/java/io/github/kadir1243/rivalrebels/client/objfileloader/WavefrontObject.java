package io.github.kadir1243.rivalrebels.client.objfileloader;

import io.github.kadir1243.rivalrebels.RivalRebels;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import org.joml.Vector3f;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@OnlyIn(Dist.CLIENT)
public class WavefrontObject {
    public static final WavefrontObject EMPTY = new WavefrontObject();
    private static final Pattern vertexPattern = Pattern.compile("(v( (-)?\\d+(\\.\\d+)?){3,4} *\\n)|(v( (-)?\\d+(\\.\\d+)?){3,4} *$)");
    private static final Pattern vertexNormalPattern = Pattern.compile("(vn( (-)?\\d+(\\.\\d+)?){3,4} *\\n)|(vn( (-)?\\d+(\\.\\d+)?){3,4} *$)");
    private static final Pattern textureCoordinatePattern = Pattern.compile("(vt( (-)?\\d+\\.\\d+){2,3} *\\n)|(vt( (-)?\\d+(\\.\\d+)?){2,3} *$)");
    private static final Pattern face_V_VT_VN_Pattern = Pattern.compile("(f( \\d+/\\d+/\\d+){3,4} *\\n)|(f( \\d+/\\d+/\\d+){3,4} *$)");
    private static final Pattern face_V_VT_Pattern = Pattern.compile("(f( \\d+/\\d+){3,4} *\\n)|(f( \\d+/\\d+){3,4} *$)");
    private static final Pattern face_V_VN_Pattern = Pattern.compile("(f( \\d+//\\d+){3,4} *\\n)|(f( \\d+//\\d+){3,4} *$)");
    private static final Pattern face_V_Pattern = Pattern.compile("(f( \\d+){3,4} *\\n)|(f( \\d+){3,4} *$)");
    private static final Pattern groupObjectPattern = Pattern.compile("([go]( [\\w.]+) *\\n)|([go]( [\\w.]+) *$)");

    private static Matcher vertexMatcher, vertexNormalMatcher, textureCoordinateMatcher;
    private static Matcher face_V_VT_VN_Matcher, face_V_VT_Matcher, face_V_VN_Matcher, face_V_Matcher;
    private static Matcher groupObjectMatcher;
    private final String fileName;
    private final boolean isEmpty;
    public List<Vector3f> vertices = new ArrayList<>();
    public List<Vector3f> vertexNormals = new ArrayList<>();
    public List<Vector3f> textureCoordinates = new ArrayList<>();
    public List<GroupObject> groupObjects = new ArrayList<>();
    private GroupObject currentGroupObject;

    public WavefrontObject(ResourceLocation resource) throws ModelFormatException {
        this.fileName = resource.toString();

        isEmpty = false;
        try {
            Resource res = Minecraft.getInstance().getResourceManager().getResource(resource).orElseThrow(IOException::new);
            loadObjModel(res.openAsReader());
        } catch (IOException e) {
            throw new UncheckedIOException("IO Exception reading model format", e);
        }
    }

    protected WavefrontObject() {
        isEmpty = true;
        fileName = "";
    }

    public static WavefrontObject loadModel(ResourceLocation resource) {
        try {
            return new WavefrontObject(resource);
        } catch (ModelFormatException e) {
            RivalRebels.LOGGER.error("IO Exception reading model format", e);
        }
        return EMPTY;
    }

    /***
     * Verifies that the given line from the model file is a valid vertex
     * @param line the line being validated
     * @return true if the line is a valid vertex, false otherwise
     */
    private static boolean isValidVertexLine(String line) {
        if (vertexMatcher != null) {
            vertexMatcher.reset();
        }

        vertexMatcher = vertexPattern.matcher(line);
        return vertexMatcher.matches();
    }

    /***
     * Verifies that the given line from the model file is a valid vertex normal
     * @param line the line being validated
     * @return true if the line is a valid vertex normal, false otherwise
     */
    private static boolean isValidVertexNormalLine(String line) {
        if (vertexNormalMatcher != null) {
            vertexNormalMatcher.reset();
        }

        vertexNormalMatcher = vertexNormalPattern.matcher(line);
        return vertexNormalMatcher.matches();
    }

    /***
     * Verifies that the given line from the model file is a valid texture coordinate
     * @param line the line being validated
     * @return true if the line is a valid texture coordinate, false otherwise
     */
    private static boolean isValidTextureCoordinateLine(String line) {
        if (textureCoordinateMatcher != null) {
            textureCoordinateMatcher.reset();
        }

        textureCoordinateMatcher = textureCoordinatePattern.matcher(line);
        return textureCoordinateMatcher.matches();
    }

    /***
     * Verifies that the given line from the model file is a valid face that is described by vertices, texture coordinates, and vertex normals
     * @param line the line being validated
     * @return true if the line is a valid face that matches the format "f v1/vt1/vn1 ..." (with a minimum of 3 points in the face, and a maximum of 4), false otherwise
     */
    private static boolean isValidFace_V_VT_VN_Line(String line) {
        if (face_V_VT_VN_Matcher != null) {
            face_V_VT_VN_Matcher.reset();
        }

        face_V_VT_VN_Matcher = face_V_VT_VN_Pattern.matcher(line);
        return face_V_VT_VN_Matcher.matches();
    }

    /***
     * Verifies that the given line from the model file is a valid face that is described by vertices and texture coordinates
     * @param line the line being validated
     * @return true if the line is a valid face that matches the format "f v1/vt1 ..." (with a minimum of 3 points in the face, and a maximum of 4), false otherwise
     */
    private static boolean isValidFace_V_VT_Line(String line) {
        if (face_V_VT_Matcher != null) {
            face_V_VT_Matcher.reset();
        }

        face_V_VT_Matcher = face_V_VT_Pattern.matcher(line);
        return face_V_VT_Matcher.matches();
    }

    /***
     * Verifies that the given line from the model file is a valid face that is described by vertices and vertex normals
     * @param line the line being validated
     * @return true if the line is a valid face that matches the format "f v1//vn1 ..." (with a minimum of 3 points in the face, and a maximum of 4), false otherwise
     */
    private static boolean isValidFace_V_VN_Line(String line) {
        if (face_V_VN_Matcher != null) {
            face_V_VN_Matcher.reset();
        }

        face_V_VN_Matcher = face_V_VN_Pattern.matcher(line);
        return face_V_VN_Matcher.matches();
    }

    /***
     * Verifies that the given line from the model file is a valid face that is described by only vertices
     * @param line the line being validated
     * @return true if the line is a valid face that matches the format "f v1 ..." (with a minimum of 3 points in the face, and a maximum of 4), false otherwise
     */
    private static boolean isValidFace_V_Line(String line) {
        if (face_V_Matcher != null) {
            face_V_Matcher.reset();
        }

        face_V_Matcher = face_V_Pattern.matcher(line);
        return face_V_Matcher.matches();
    }

    /***
     * Verifies that the given line from the model file is a valid face of any of the possible face formats
     * @param line the line being validated
     * @return true if the line is a valid face that matches any of the valid face formats, false otherwise
     */
    private static boolean isValidFaceLine(String line) {
        return isValidFace_V_VT_VN_Line(line) || isValidFace_V_VT_Line(line) || isValidFace_V_VN_Line(line) || isValidFace_V_Line(line);
    }

    /***
     * Verifies that the given line from the model file is a valid group (or object)
     * @param line the line being validated
     * @return true if the line is a valid group (or object), false otherwise
     */
    private static boolean isValidGroupObjectLine(String line) {
        if (groupObjectMatcher != null) {
            groupObjectMatcher.reset();
        }

        groupObjectMatcher = groupObjectPattern.matcher(line);
        return groupObjectMatcher.matches();
    }

    private void loadObjModel(BufferedReader reader) throws ModelFormatException {
        String currentLine;
        int lineCount = 0;

        try (reader) {
            while ((currentLine = reader.readLine()) != null) {
                lineCount++;
                currentLine = currentLine.replaceAll("\\s+", " ").trim();

                if (currentLine.startsWith("#") || currentLine.isEmpty()) {
                    continue;
                } else if (currentLine.startsWith("v ")) {
                    Vector3f vertex = parseVertex(currentLine, lineCount);
                    if (vertex != null) {
                        vertices.add(vertex);
                    }
                } else if (currentLine.startsWith("vn ")) {
                    Vector3f vertex = parseVertexNormal(currentLine, lineCount);
                    if (vertex != null) {
                        vertexNormals.add(vertex);
                    }
                } else if (currentLine.startsWith("vt ")) {
                    Vector3f textureCoordinate = parseTextureCoordinate(currentLine, lineCount);
                    if (textureCoordinate != null) {
                        textureCoordinates.add(textureCoordinate);
                    }
                } else if (currentLine.startsWith("f ")) {

                    if (currentGroupObject == null) {
                        currentGroupObject = new GroupObject("Default");
                    }

                    Face face = parseFace(currentLine, lineCount);

                    if (face != null) {
                        currentGroupObject.faces.add(face);
                    }
                } else if (currentLine.startsWith("g ") | currentLine.startsWith("o ")) {
                    GroupObject group = parseGroupObject(currentLine, lineCount);

                    if (group != null) {
                        if (currentGroupObject != null) {
                            groupObjects.add(currentGroupObject);
                        }
                    }

                    currentGroupObject = group;
                }
            }

            groupObjects.add(currentGroupObject);
        } catch (IOException e) {
            throw new ModelFormatException("IO Exception reading model format", e);
        }
    }

    public void render(PoseStack pose, VertexConsumer buffer, int color, int light, int overlay) {
        if (isEmpty) return;
        for (GroupObject groupObject : groupObjects) {
            groupObject.render(pose, buffer, color, light, overlay);
        }
    }

    private Vector3f parseVertex(String line, int lineCount) throws ModelFormatException {
        if (isValidVertexLine(line)) {
            line = line.substring(line.indexOf(" ") + 1);
            String[] tokens = line.split(" ");

            try {
                if (tokens.length == 2) {
                    return new Vector3f(Float.parseFloat(tokens[0]), Float.parseFloat(tokens[1]), 0);
                } else if (tokens.length == 3) {
                    return new Vector3f(Float.parseFloat(tokens[0]), Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2]));
                }
            } catch (NumberFormatException e) {
                throw new ModelFormatException(String.format("Number formatting error at line %d", lineCount), e);
            }
        } else {
            throw new ModelFormatException("Error parsing entry ('" + line + "'" + ", line " + lineCount + ") in file '" + fileName + "' - Incorrect format");
        }

        return null;
    }

    private Vector3f parseVertexNormal(String line, int lineCount) throws ModelFormatException {
        if (isValidVertexNormalLine(line)) {
            line = line.substring(line.indexOf(" ") + 1);
            String[] tokens = line.split(" ");

            try {
                if (tokens.length == 3)
                    return new Vector3f(Float.parseFloat(tokens[0]), Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2]));
            } catch (NumberFormatException e) {
                throw new ModelFormatException(String.format("Number formatting error at line %d", lineCount), e);
            }
        } else {
            throw new ModelFormatException("Error parsing entry ('" + line + "'" + ", line " + lineCount + ") in file '" + fileName + "' - Incorrect format");
        }

        return null;
    }

    private Vector3f parseTextureCoordinate(String line, int lineCount) throws ModelFormatException {
        if (isValidTextureCoordinateLine(line)) {
            line = line.substring(line.indexOf(" ") + 1);
            String[] tokens = line.split(" ");

            try {
                if (tokens.length == 2)
                    return new Vector3f(Float.parseFloat(tokens[0]), 1 - Float.parseFloat(tokens[1]), 0);
                else if (tokens.length == 3)
                    return new Vector3f(Float.parseFloat(tokens[0]), 1 - Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2]));
            } catch (NumberFormatException e) {
                throw new ModelFormatException(String.format("Number formatting error at line %d", lineCount), e);
            }
        } else {
            throw new ModelFormatException("Error parsing entry ('" + line + "'" + ", line " + lineCount + ") in file '" + fileName + "' - Incorrect format");
        }

        return null;
    }

    private Face parseFace(String line, int lineCount) throws ModelFormatException {
        Face face;

        if (isValidFaceLine(line)) {
            face = new Face();

            String trimmedLine = line.substring(line.indexOf(" ") + 1);
            String[] tokens = trimmedLine.split(" ");
            String[] subTokens;

            // f v1/vt1/vn1 v2/vt2/vn2 v3/vt3/vn3 ...
            if (isValidFace_V_VT_VN_Line(line)) {
                face.vertices = new Vector3f[tokens.length];
                face.textureCoordinates = new Vector3f[tokens.length];
                face.vertexNormals = new Vector3f[tokens.length];

                for (int i = 0; i < tokens.length; ++i) {
                    subTokens = tokens[i].split("/");

                    face.vertices[i] = vertices.get(Integer.parseInt(subTokens[0]) - 1);
                    face.textureCoordinates[i] = textureCoordinates.get(Integer.parseInt(subTokens[1]) - 1);
                    face.vertexNormals[i] = vertexNormals.get(Integer.parseInt(subTokens[2]) - 1);
                }

                face.faceNormal = face.calculateFaceNormal();
            }
            // f v1/vt1 v2/vt2 v3/vt3 ...
            else if (isValidFace_V_VT_Line(line)) {
                face.vertices = new Vector3f[tokens.length];
                face.textureCoordinates = new Vector3f[tokens.length];

                for (int i = 0; i < tokens.length; ++i) {
                    subTokens = tokens[i].split("/");

                    face.vertices[i] = vertices.get(Integer.parseInt(subTokens[0]) - 1);
                    face.textureCoordinates[i] = textureCoordinates.get(Integer.parseInt(subTokens[1]) - 1);
                }

                face.faceNormal = face.calculateFaceNormal();
            }
            // f v1//vn1 v2//vn2 v3//vn3 ...
            else if (isValidFace_V_VN_Line(line)) {
                face.vertices = new Vector3f[tokens.length];
                face.vertexNormals = new Vector3f[tokens.length];

                for (int i = 0; i < tokens.length; ++i) {
                    subTokens = tokens[i].split("//");

                    face.vertices[i] = vertices.get(Integer.parseInt(subTokens[0]) - 1);
                    face.vertexNormals[i] = vertexNormals.get(Integer.parseInt(subTokens[1]) - 1);
                }

                face.faceNormal = face.calculateFaceNormal();
            }
            // f v1 v2 v3 ...
            else if (isValidFace_V_Line(line)) {
                face.vertices = new Vector3f[tokens.length];

                for (int i = 0; i < tokens.length; ++i) {
                    face.vertices[i] = vertices.get(Integer.parseInt(tokens[i]) - 1);
                }

                face.faceNormal = face.calculateFaceNormal();
            } else {
                throw new ModelFormatException("Error parsing entry ('" + line + "'" + ", line " + lineCount + ") in file '" + fileName + "' - Incorrect format");
            }
        } else {
            throw new ModelFormatException("Error parsing entry ('" + line + "'" + ", line " + lineCount + ") in file '" + fileName + "' - Incorrect format");
        }

        return face;
    }

    private GroupObject parseGroupObject(String line, int lineCount) throws ModelFormatException {
        GroupObject group = null;

        if (isValidGroupObjectLine(line)) {
            String trimmedLine = line.substring(line.indexOf(" ") + 1);

            if (!trimmedLine.isEmpty()) {
                group = new GroupObject(trimmedLine);
            }
        } else {
            throw new ModelFormatException("Error parsing entry ('" + line + "'" + ", line " + lineCount + ") in file '" + fileName + "' - Incorrect format");
        }

        return group;
    }

    @OnlyIn(Dist.CLIENT)
    public static class GroupObject {
        public String name;
        public List<Face> faces = new ArrayList<>();

        public GroupObject(String name) {
            this.name = name;
        }

        public void render(PoseStack pose, VertexConsumer buffer, int color, int light, int overlay) {
            if (!faces.isEmpty()) {
                for (Face face : faces) {
                    face.addFaceForRender(pose, buffer, color, light, overlay);
                }
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class Face {
        public Vector3f[] vertices;
        public Vector3f[] vertexNormals;
        public Vector3f faceNormal;
        public Vector3f[] textureCoordinates;

        public void addFaceForRender(PoseStack pose, VertexConsumer buffer, int color, int light, int overlay) {
            addFaceForRender(pose, buffer, color, light, overlay, 0.0005F);
        }

        public void addFaceForRender(PoseStack pose, VertexConsumer buffer, int colorRGBA, int light, int overlay, float textureOffset) {
            if (faceNormal == null) {
                faceNormal = this.calculateFaceNormal();
            }

            float averageU = 0F;
            float averageV = 0F;

            if (textureCoordinates != null && textureCoordinates.length > 0) {
                for (Vector3f textureCoordinate : textureCoordinates) {
                    averageU += textureCoordinate.x;
                    averageV += textureCoordinate.y;
                }

                averageU = averageU / textureCoordinates.length;
                averageV = averageV / textureCoordinates.length;
            }

            float offsetU, offsetV;

            for (int i = 0; i < vertices.length; ++i) {
                if ((textureCoordinates != null) && (textureCoordinates.length > 0)) {
                    offsetU = textureOffset;
                    offsetV = textureOffset;

                    if (textureCoordinates[i].x > averageU) {
                        offsetU = -offsetU;
                    }
                    if (textureCoordinates[i].y > averageV) {
                        offsetV = -offsetV;
                    }

                    buffer.addVertex(pose.last(), vertices[i]).setColor(colorRGBA).setUv(textureCoordinates[i].x + offsetU, textureCoordinates[i].y + offsetV).setOverlay(overlay).setLight(light).setNormal(pose.last(), faceNormal.x, faceNormal.y, faceNormal.z);
                } else {
                    buffer.addVertex(pose.last(), vertices[i]).setColor(colorRGBA).setOverlay(overlay).setLight(light).setNormal(pose.last(), faceNormal.x, faceNormal.y, faceNormal.z);
                }
            }
        }

        public Vector3f calculateFaceNormal() {
            Vector3f v1 = new Vector3f(vertices[1].x - vertices[0].x, vertices[1].y - vertices[0].y, vertices[1].z - vertices[0].z);
            Vector3f v2 = new Vector3f(vertices[2].x - vertices[0].x, vertices[2].y - vertices[0].y, vertices[2].z - vertices[0].z);

            Vector3f normalVector = v1.cross(v2).normalize();

            return new Vector3f(normalVector);
        }
    }

    public static class ModelFormatException extends Exception {
        public ModelFormatException(String message) {
            super(message);
        }

        public ModelFormatException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
