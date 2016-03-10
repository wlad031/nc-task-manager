package models;

import javax.xml.bind.annotation.XmlAttribute;

public abstract class Model {

    @XmlAttribute
    protected int id;

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
