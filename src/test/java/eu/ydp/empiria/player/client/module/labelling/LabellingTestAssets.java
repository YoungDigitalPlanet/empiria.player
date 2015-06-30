package eu.ydp.empiria.player.client.module.labelling;

import com.google.common.collect.Lists;
import eu.ydp.empiria.player.client.module.labelling.structure.ImgBean;

import java.util.List;

public class LabellingTestAssets {

    public static final String ID = "id";
    public static final ChildData CHILD_0 = new ChildData(0, 0, "<node/>");
    public static final ChildData CHILD_1 = new ChildData(10, 20, "<nodeWithDescendants>\n\n<innerNode attr='val'/>\t\ninnerText\n</nodeWithDescendants>");
    public static final ChildData CHILD_2 = new ChildData(-10, -20, "<siblingNode1/><siblingNode2/>");
    public static final ChildData CHILD_3 = new ChildData(10, 20, "");
    public static final List<ChildData> CHILDREN_FULL = Lists.newArrayList(CHILD_0, CHILD_1, CHILD_2, CHILD_3);
    public static final List<Integer> CHILDREN_FULL_X = Lists.newArrayList(CHILD_0.getX(), CHILD_1.getX(), CHILD_2.getX(), CHILD_3.getX());
    public static final List<Integer> CHILDREN_FULL_Y = Lists.newArrayList(CHILD_0.getY(), CHILD_1.getY(), CHILD_2.getY(), CHILD_3.getY());

    public static final String IMAGE_SRC = "image.png";
    public static final int IMAGE_WIDTH = 400;
    public static final int IMAGE_HEIGHT = 300;
    public static final ImgBean IMAGE_BEAN = createImgBean();

    public static final String XML_FULL = "<labellingInteraction id=\"" + ID + "\">" + "<img src='" + IMAGE_SRC + "' width='" + IMAGE_WIDTH + "' height='"
            + IMAGE_HEIGHT + "'/>" + "<children>" + CHILD_0.xml() + CHILD_1.xml() + CHILD_2.xml() + CHILD_3.xml() + "</children>" + "</labellingInteraction>";

    public static final String XML_WITHOUT_ID = "<labellingInteraction>" + "<img src='" + IMAGE_SRC + "' width='" + IMAGE_WIDTH + "' height='" + IMAGE_HEIGHT
            + "'/>" + "<children>" + CHILD_0.xml() + CHILD_1.xml() + CHILD_2.xml() + CHILD_3.xml() + "</children>" + "</labellingInteraction>";

    public static final String XML_NO_CHILDREN = "<labellingInteraction>" + "<img src='" + IMAGE_SRC + "' width='" + IMAGE_WIDTH + "' height='" + IMAGE_HEIGHT
            + "'/>" + "<children>" + "</children>" + "</labellingInteraction>";

    public static final String XML_NO_CHILDREN_NODE = "<labellingInteraction>" + "<img src='" + IMAGE_SRC + "' width='" + IMAGE_WIDTH + "' height='"
            + IMAGE_HEIGHT + "'/>" + "</labellingInteraction>";

    private static ImgBean createImgBean() {
        ImgBean bean = new ImgBean();
        bean.setSrc(IMAGE_SRC);
        bean.setWidth(IMAGE_WIDTH);
        bean.setHeight(IMAGE_HEIGHT);
        return bean;
    }

}
