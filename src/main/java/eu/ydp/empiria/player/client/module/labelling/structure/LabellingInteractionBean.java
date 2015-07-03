package eu.ydp.empiria.player.client.module.labelling.structure;

import com.google.common.base.Objects;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "labellingInteraction")
public class LabellingInteractionBean {

    @XmlElement
    private ImgBean img;
    @XmlElement
    private ChildrenBean children;
    @XmlAttribute
    private String id;

    public ImgBean getImg() {
        return img;
    }

    public void setImg(ImgBean img) {
        this.img = img;
    }

    public ChildrenBean getChildren() {
        return children;
    }

    public void setChildren(ChildrenBean children) {
        this.children = children;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return Objects.firstNonNull(id, "");
    }

}
