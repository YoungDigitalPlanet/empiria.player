package eu.ydp.empiria.player.client.module.labelling.structure;

import com.google.common.collect.Lists;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "children")
public class ChildrenBean {

    @XmlElement(name = "child")
    private List<ChildBean> childBeanList = Lists.newArrayList();

    public List<ChildBean> getChildBeanList() {
        return childBeanList;
    }

    public void setChildBeanList(List<ChildBean> children) {
        this.childBeanList = children;
    }

}
