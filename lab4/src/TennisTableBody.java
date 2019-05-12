import javax.media.j3d.*;
import javax.vecmath.Color3f;
import java.awt.*;

import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.image.TextureLoader;

import javax.vecmath.*;


class TennisTableBody {
  static Box getFoundation(float height, float width, float length) {
    int primflags = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;
    return new Box(width, length, height, primflags, getFoundationAppearance());
  }

  static Box getBody(float height, float width, float length) {
    int primflags = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;
    return new Box(width, length, height, primflags, getBodyAppearance());
  }

  static Box getNet(float width, float height, float length) {
    int primflags = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;
    return new Box(width, length, height, primflags, getNetAppearance());
  }

  static Box getLine(float width, float height, float length) {
    int primflags = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;
    return new Box(width, length, height, primflags, getLineAppearance());
  }

  private static Cylinder getCentralTower(float cylinderHeight) {
    int primflags = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;
    return new Cylinder(0.1f, cylinderHeight, primflags, getCylTowersAppearance());
  }

  static TransformGroup getCylinderTower(float height, float xPos, float yPos, float zPos) {
    TransformGroup tg = new TransformGroup();

    Cylinder centralTower = TennisTableBody.getCentralTower(height);
    Transform3D centralTowerT = new Transform3D();
    centralTowerT.setTranslation(new Vector3f(xPos, yPos, zPos));
    centralTowerT.setRotation(new AxisAngle4d(1, 0, 0, Math.toRadians(90)));
    TransformGroup centralTowerTG = new TransformGroup();
    centralTowerTG.setTransform(centralTowerT);
    centralTowerTG.addChild(centralTower);
    tg.addChild(centralTowerTG);
    return tg;
  }

  private static Appearance getFoundationAppearance() {
    Appearance ap = new Appearance();
    Color3f emissive = new Color3f(new Color(0, 0, 0));
    Color3f ambient = new Color3f(new Color(0, 0, 0));
    Color3f diffuse = new Color3f(new Color(55, 155, 155));
    Color3f specular = new Color3f(new Color(0, 0, 0));
    ap.setMaterial(new Material(ambient, emissive, diffuse, specular, 1.0f));
    return ap;
  }

  private static Appearance getLineAppearance() {
    Appearance ap = new Appearance();
    Color3f emissive = new Color3f(new Color(0, 0, 0));
    Color3f ambient = new Color3f(new Color(200, 200, 200));
    Color3f diffuse = new Color3f(new Color(0, 0, 0));
    Color3f specular = new Color3f(new Color(0, 0, 0));
    ap.setMaterial(new Material(ambient, emissive, diffuse, specular, 1.0f));
    return ap;
  }

  private static Appearance getNetAppearance() {
    TextureLoader loader = new TextureLoader("lab4/res/metal.jpg", "LUMINANCE", new Container());

    Texture texture = loader.getTexture();
    texture.setBoundaryModeS(Texture.WRAP);
    texture.setBoundaryModeT(Texture.WRAP);
    texture.setBoundaryColor(new Color4f(0.0f, 1.0f, 1.0f, 0.0f));

    TextureAttributes texAttr = new TextureAttributes();
    texAttr.setTextureMode(TextureAttributes.MODULATE);

    Appearance ap = new Appearance();
    ap.setTexture(texture);
    ap.setTextureAttributes(texAttr);

    Color3f emissive = new Color3f(new Color(0, 0, 0));
    Color3f ambient = new Color3f(new Color(200, 200, 200));
    Color3f diffuse = new Color3f(new Color(255, 255, 255));
    Color3f specular = new Color3f(new Color(0, 0, 0));
    ap.setMaterial(new Material(ambient, emissive, diffuse, specular, 1.0f));
    return ap;
  }

  private static Appearance getBodyAppearance() {
    Appearance ap = new Appearance();
    Color3f emissive = new Color3f(new Color(0, 0, 0));
    Color3f ambient = new Color3f(new Color(21, 85, 26));
    Color3f diffuse = new Color3f(new Color(9, 39, 15, 250));
    Color3f specular = new Color3f(new Color(0, 0, 0));
    ap.setMaterial(new Material(ambient, emissive, diffuse, specular, 1.0f));
    return ap;
  }

  private static Appearance getCylTowersAppearance() {
    Appearance ap = new Appearance();

    Color3f emissive = new Color3f(new Color(0, 0, 0));
    Color3f ambient = new Color3f(new Color(0, 0, 0));
    Color3f diffuse = new Color3f(new Color(30, 30, 30));
    Color3f specular = new Color3f(new Color(0, 0, 0));
    ap.setMaterial(new Material(ambient, emissive, diffuse, specular, 1.0f));
    return ap;
  }
}
