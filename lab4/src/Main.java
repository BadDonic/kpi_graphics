import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.SimpleUniverse;

import javax.media.j3d.*;
import javax.swing.Timer;
import javax.vecmath.*;

public class Main implements ActionListener {
  private float upperEyeLimit = 12.0f;
  private float lowerEyeLimit = 4.0f;
  private float farthestEyeLimit = 12.0f;
  private float nearestEyeLimit = 6.0f;
  private float tableLength = 4f;
  private float tableWidth = 2f;
  private float tableHeight = 4f;

  private TransformGroup treeTransformGroup;
  private TransformGroup viewingTransformGroup;
  private Transform3D treeTransform3D = new Transform3D();
  private Transform3D viewingTransform = new Transform3D();
  private float angle = 0;
  private float eyeHeight;
  private float eyeDistance;
  private boolean descend = true;
  private boolean approaching = true;

  public static void main(String[] args) {
    new Main();
  }

  private Main() {
    Timer timer = new Timer(50, this);
    SimpleUniverse universe = new SimpleUniverse();

    viewingTransformGroup = universe.getViewingPlatform().getViewPlatformTransform();
    universe.addBranchGraph(createSceneGraph());

    eyeHeight = upperEyeLimit;
    eyeDistance = farthestEyeLimit;
    timer.start();
  }

  private BranchGroup createSceneGraph() {
    BranchGroup objRoot = new BranchGroup();

    treeTransformGroup = new TransformGroup();
    treeTransformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
    buildCastleSkeleton();
    objRoot.addChild(treeTransformGroup);

    Background background = new Background(new Color3f(1.0f, 1.0f, 1.0f)); // white color
    BoundingSphere sphere = new BoundingSphere(new Point3d(0, 0, 0), 100000);
    background.setApplicationBounds(sphere);
    objRoot.addChild(background);

    BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
    Color3f light1Color = new Color3f(1.0f, 0.5f, 0.4f);
    Vector3f light1Direction = new Vector3f(4.0f, -7.0f, -12.0f);
    DirectionalLight light1 = new DirectionalLight(light1Color, light1Direction);
    light1.setInfluencingBounds(bounds);
    objRoot.addChild(light1);

    Color3f ambientColor = new Color3f(1.0f, 1.0f, 1.0f);
    AmbientLight ambientLightNode = new AmbientLight(ambientColor);
    ambientLightNode.setInfluencingBounds(bounds);
    objRoot.addChild(ambientLightNode);
    return objRoot;
  }

  private void buildCastleSkeleton() {
    setFoundation();
    setCylinderMainstay();
    setDesk();
    setNet();
    setLines();
  }

  private void setFoundation() {
    Box foundation = TennisTableBody.getFoundation(0.1f, 5f, 8.0f);
    Transform3D foundationT = new Transform3D();
    foundationT.setTranslation(new Vector3f());
    TransformGroup foundationTG = new TransformGroup();
    foundationTG.setTransform(foundationT);
    foundationTG.addChild(foundation);
    treeTransformGroup.addChild(foundationTG);
  }

  private void setDesk() {
    Box body = TennisTableBody.getBody(0.05f, 2.1f, 4.1f);
    Vector3f position = new Vector3f(0, 0, 2f);
    Transform3D bodyT = new Transform3D();
    bodyT.setTranslation(position);
    TransformGroup bodyTG = new TransformGroup();
    bodyTG.setTransform(bodyT);
    bodyTG.addChild(body);
    treeTransformGroup.addChild(bodyTG);
  }

  private void setNet() {
    TransformGroup cylinderTower1 = TennisTableBody.getCylinderTower(tableHeight / 4, -tableWidth, 0, 2f);
    treeTransformGroup.addChild(cylinderTower1);
    TransformGroup cylinderTower2 = TennisTableBody.getCylinderTower(tableHeight / 4, tableWidth, 0, 2f);
    treeTransformGroup.addChild(cylinderTower2);
    Box net = TennisTableBody.getNet(tableWidth - 0.1f, tableHeight / 16, 0.01f);
    Vector3f position = new Vector3f(0, 0, 2.2f);
    Transform3D netT = new Transform3D();
    netT.setTranslation(position);
    TransformGroup netTG = new TransformGroup();
    netTG.setTransform(netT);
    netTG.addChild(net);
    treeTransformGroup.addChild(netTG);
  }

  private void setLines() {
    float lineWidth = 0.2f;
    Box line1 = TennisTableBody.getLine(lineWidth / 2, 0.01f, tableLength + 0.1f);
    Vector3f position1 = new Vector3f(tableWidth, 0, 2.05f);
    Transform3D line1T = new Transform3D();
    line1T.setTranslation(position1);
    TransformGroup line1TG = new TransformGroup();
    line1TG.setTransform(line1T);
    line1TG.addChild(line1);
    treeTransformGroup.addChild(line1TG);

    Box line2 = TennisTableBody.getLine(lineWidth / 2, 0.01f, tableLength + 0.1f);
    Vector3f position2 = new Vector3f(-tableWidth, 0, 2.05f);
    Transform3D line2T = new Transform3D();
    line2T.setTranslation(position2);
    TransformGroup line2TG = new TransformGroup();
    line2TG.setTransform(line2T);
    line2TG.addChild(line2);
    treeTransformGroup.addChild(line2TG);

    Box line3 = TennisTableBody.getLine(tableWidth, 0.01f, lineWidth / 2);
    Vector3f position3 = new Vector3f(0, tableLength, 2.05f);
    Transform3D line3T = new Transform3D();
    line3T.setTranslation(position3);
    TransformGroup line3TG = new TransformGroup();
    line3TG.setTransform(line3T);
    line3TG.addChild(line3);
    treeTransformGroup.addChild(line3TG);

    Box line4 = TennisTableBody.getLine(tableWidth, 0.01f, lineWidth / 2);
    Vector3f position4 = new Vector3f(0, -tableLength, 2.05f);
    Transform3D line4T = new Transform3D();
    line4T.setTranslation(position4);
    TransformGroup line4TG = new TransformGroup();
    line4TG.setTransform(line4T);
    line4TG.addChild(line4);
    treeTransformGroup.addChild(line4TG);
  }

  private void setCylinderMainstay() {
    TransformGroup cylinderTower1 = TennisTableBody.getCylinderTower(tableHeight, -tableWidth, tableLength, 0);
    treeTransformGroup.addChild(cylinderTower1);
    TransformGroup cylinderTower2 = TennisTableBody.getCylinderTower(tableHeight, -tableWidth, -tableLength, 0);
    treeTransformGroup.addChild(cylinderTower2);
    TransformGroup cylinderTower3 = TennisTableBody.getCylinderTower(tableHeight, tableWidth, tableLength, 0);
    treeTransformGroup.addChild(cylinderTower3);
    TransformGroup cylinderTower4 = TennisTableBody.getCylinderTower(tableHeight, tableWidth, -tableLength, 0);
    treeTransformGroup.addChild(cylinderTower4);
    TransformGroup cylinderTower5 = TennisTableBody.getCylinderTower(tableHeight, tableWidth, 0, 0);
    treeTransformGroup.addChild(cylinderTower5);
    TransformGroup cylinderTower6 = TennisTableBody.getCylinderTower(tableHeight, -tableWidth, 0, 0);
    treeTransformGroup.addChild(cylinderTower6);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    float delta = 0.03f;

    treeTransform3D.rotZ(angle);
    treeTransformGroup.setTransform(treeTransform3D);
    angle += delta;

    if (eyeHeight > upperEyeLimit) {
      descend = true;
    } else if (eyeHeight < lowerEyeLimit) {
      descend = false;
    }
    if (descend) {
      eyeHeight -= delta;
    } else {
      eyeHeight += delta;
    }

    if (eyeDistance > farthestEyeLimit) {
      approaching = true;
    } else if (eyeDistance < nearestEyeLimit) {
      approaching = false;
    }
    if (approaching) {
      eyeDistance -= delta;
    } else {
      eyeDistance += delta;
    }

    Point3d eye = new Point3d(eyeDistance, eyeDistance, eyeHeight);
    Point3d center = new Point3d(.0f, .0f, 0.5f);
    Vector3d up = new Vector3d(.0f, .0f, 1.0f);
    viewingTransform.lookAt(eye, center, up);
    viewingTransform.invert();
    viewingTransformGroup.setTransform(viewingTransform);
  }
}
