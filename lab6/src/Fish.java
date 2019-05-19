import javax.vecmath.*;

import com.sun.j3d.utils.universe.*;

import javax.media.j3d.*;

import com.sun.j3d.utils.behaviors.vp.*;

import javax.swing.JFrame;

import com.sun.j3d.loaders.*;
import com.sun.j3d.loaders.objectfile.*;

import java.util.Hashtable;
import java.util.Enumeration;

public class Fish extends JFrame {
  public Canvas3D myCanvas3D;

  public Fish() {
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    myCanvas3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
    SimpleUniverse simpUniv = new SimpleUniverse(myCanvas3D);

    simpUniv.getViewingPlatform().setNominalViewingTransform();

    createSceneGraph(simpUniv);
    addLight(simpUniv);

    OrbitBehavior ob = new OrbitBehavior(myCanvas3D);
    ob.setSchedulingBounds(new BoundingSphere(new Point3d(0.0, 0.0, 0.0), Double.MAX_VALUE));
    simpUniv.getViewingPlatform().setViewPlatformBehavior(ob);

    setTitle("Fish");
    setSize(700, 700);
    getContentPane().add("Center", myCanvas3D);
    setVisible(true);
  }

  public void createSceneGraph(SimpleUniverse su) {
    ObjectFile f = new ObjectFile(ObjectFile.RESIZE);
    BoundingSphere bs = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), Double.MAX_VALUE);
    String name;
    BranchGroup carBranchGroup = new BranchGroup();
    Background carBackground = new Background(new Color3f(0, 255, 255));

    Scene carScene = null;
    try {
      carScene = f.load("lab6/res/fish.obj");
    } catch (Exception e) {
      System.out.println("File loading failed:" + e);
    }
    Hashtable roachNamedObjects = carScene.getNamedObjects();
    Enumeration enumer = roachNamedObjects.keys();
    while (enumer.hasMoreElements()) {
      name = (String) enumer.nextElement();
      System.out.println("Name: " + name);
    }


    Transform3D startTransformation = new Transform3D();
    startTransformation.setScale(1.0 / 6);
    Transform3D combinedStartTransformation = new Transform3D();
    combinedStartTransformation.rotY(-3 * Math.PI / 2);
    combinedStartTransformation.mul(startTransformation);

    TransformGroup carStartTransformGroup = new TransformGroup(combinedStartTransformation);


    int movesCount = 100;
    int movesDuration = 400;
    int startTime = 0;


    Alpha tailRotAlpha = new Alpha(movesCount, Alpha.INCREASING_ENABLE | Alpha.DECREASING_ENABLE, startTime, 0, movesDuration, 0, 0, movesDuration, 0, 0);

    Shape3D tail = (Shape3D) roachNamedObjects.get("tail");
    TransformGroup tailTG = new TransformGroup();
    tailTG.addChild(tail.cloneTree());

    Transform3D tailRotAxis = new Transform3D();
    tailRotAxis.set(new Vector3d(0, -0, -0.644));
    tailRotAxis.setRotation(new AxisAngle4d(0, 0, 0, 0));

    RotationInterpolator tailRoot = new RotationInterpolator(tailRotAlpha, tailTG, tailRotAxis, -(float) Math.PI / 3, (float) Math.PI / 3);
    tailRoot.setSchedulingBounds(bs);
    tailTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
    tailTG.addChild(tailRoot);

    Alpha ventralFinQAlpha = new Alpha(movesCount, Alpha.INCREASING_ENABLE | Alpha.DECREASING_ENABLE, startTime, 0, movesDuration, 0, 0, movesDuration, 0, 0);

    Shape3D ventralFinQ = (Shape3D) roachNamedObjects.get("ventral_finq");
    TransformGroup ventralFinQTG = new TransformGroup();
    ventralFinQTG.addChild(ventralFinQ.cloneTree());

    Transform3D legRotAxis = new Transform3D();
    legRotAxis.set(new Vector3d(0, 0, 0.5));
    legRotAxis.setRotation(new AxisAngle4d(90, 90, 90, 90));

    RotationInterpolator ventralFinQRot = new RotationInterpolator(ventralFinQAlpha, ventralFinQTG, legRotAxis, 0, (float) Math.PI / 8);
    ventralFinQRot.setSchedulingBounds(bs);
    ventralFinQTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
    ventralFinQTG.addChild(ventralFinQRot);


    Alpha ventralFin2Alpha = new Alpha(movesCount, Alpha.INCREASING_ENABLE | Alpha.DECREASING_ENABLE, startTime + 500, 0, movesDuration, 0, 0, movesDuration, 0, 0);

    Shape3D ventralFin2 = (Shape3D) roachNamedObjects.get("ventral_fin2");
    TransformGroup ventralFinTG2 = new TransformGroup();
    ventralFinTG2.addChild(ventralFin2.cloneTree());

    Transform3D ventralFinAxis2 = new Transform3D();
    ventralFinAxis2.set(new Vector3d(0, 0, 0.52));
    ventralFinAxis2.setRotation(new AxisAngle4d(90, 90, 90, 90));

    RotationInterpolator ventralFin2Rot = new RotationInterpolator(ventralFin2Alpha, ventralFinTG2, ventralFinAxis2, -(float) Math.PI / 8, 0);
    ventralFin2Rot.setSchedulingBounds(bs);
    ventralFinTG2.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
    ventralFinTG2.addChild(ventralFin2Rot);

    Alpha headAplha = new Alpha(movesCount, Alpha.INCREASING_ENABLE | Alpha.DECREASING_ENABLE, startTime, 0, movesDuration, 0, 0, movesDuration, 0, 0);

    Shape3D head = (Shape3D) roachNamedObjects.get("head");
    TransformGroup headTG = new TransformGroup();
    headTG.addChild(head.cloneTree());

    Transform3D headAxis = new Transform3D();
    headAxis.set(new Vector3d(0, -0, 0.644));
    headAxis.setRotation(new AxisAngle4d(0, 0, 0, 0));

    RotationInterpolator headRot = new RotationInterpolator(headAplha, headTG, headAxis, -(float) Math.PI / 10, (float) Math.PI / 10);
    headRot.setSchedulingBounds(bs);
    headTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
    headTG.addChild(headRot);


    TransformGroup sceneGroup = new TransformGroup();
    sceneGroup.addChild(tailTG);
    sceneGroup.addChild(ventralFinQTG);
    sceneGroup.addChild(ventralFinTG2);
    sceneGroup.addChild(headTG);
    TransformGroup tgBody = new TransformGroup();
    Shape3D carBodyShape = (Shape3D) roachNamedObjects.get("rt_body");
    tgBody.addChild(carBodyShape.cloneTree());
    sceneGroup.addChild(tgBody.cloneTree());


    TransformGroup tgBodyfin2 = new TransformGroup();
    Shape3D carBodyShapefin2 = (Shape3D) roachNamedObjects.get("fin2");
    tgBodyfin2.addChild(carBodyShapefin2.cloneTree());
    sceneGroup.addChild(tgBodyfin2.cloneTree());
    TransformGroup tgBodyfin1 = new TransformGroup();
    Shape3D carBodyShapefin1 = (Shape3D) roachNamedObjects.get("fin1");
    tgBodyfin1.addChild(carBodyShapefin1.cloneTree());
    sceneGroup.addChild(tgBodyfin1.cloneTree());


    TransformGroup whiteTransXformGroup = translate(
        carStartTransformGroup,
        new Vector3f(0.0f, 0.0f, 0.5f));

    TransformGroup whiteRotXformGroup = rotate(whiteTransXformGroup, new Alpha(10, 5000));
    carBranchGroup.addChild(whiteRotXformGroup);
    carStartTransformGroup.addChild(sceneGroup);


    BoundingSphere bounds = new BoundingSphere(new Point3d(120.0, 250.0, 100.0), Double.MAX_VALUE);
    carBackground.setApplicationBounds(bounds);
    carBranchGroup.addChild(carBackground);

    carBranchGroup.compile();
    su.addBranchGraph(carBranchGroup);
  }

  public void addLight(SimpleUniverse su) {
    BranchGroup bgLight = new BranchGroup();
    BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
    Color3f lightColour1 = new Color3f(1.0f, 1.0f, 1.0f);
    Vector3f lightDir1 = new Vector3f(-1.0f, 0.0f, -0.5f);
    DirectionalLight light1 = new DirectionalLight(lightColour1, lightDir1);
    light1.setInfluencingBounds(bounds);
    bgLight.addChild(light1);
    su.addBranchGraph(bgLight);
  }

  TransformGroup translate(Node node, Vector3f vector) {

    Transform3D transform3D = new Transform3D();
    transform3D.setTranslation(vector);
    TransformGroup transformGroup =
        new TransformGroup();
    transformGroup.setTransform(transform3D);

    transformGroup.addChild(node);
    return transformGroup;
  }

  TransformGroup rotate(Node node, Alpha alpha) {

    TransformGroup xformGroup = new TransformGroup();
    xformGroup.setCapability(
        TransformGroup.ALLOW_TRANSFORM_WRITE);


    RotationInterpolator interpolator =
        new RotationInterpolator(alpha, xformGroup);


    interpolator.setSchedulingBounds(new BoundingSphere(
        new Point3d(0.0, 0.0, 0.0), 1.0));


    xformGroup.addChild(interpolator);
    xformGroup.addChild(node);

    return xformGroup;
  }

  public static void main(String[] args) {
    Fish start = new Fish();
  }

}

