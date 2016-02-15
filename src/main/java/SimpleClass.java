import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "test")
public class SimpleClass {

    @XmlAttribute
    public int id;

    private String s;
    private int a;
    private boolean b;

    public SimpleClass() {
    }

    public SimpleClass(int id, String s, int a, boolean b) {
        this.id = id;
        this.s = s;
        this.a = a;
        this.b = b;
    }

    @Getter(fieldName = "id")
    public int getId() {
        return id;
    }

    @XmlElement
    @Getter(fieldName = "s")
    public String getS() {
        return s;
    }

    @XmlElement
    @Getter(fieldName = "a")
    public int getA() {
        return a;
    }

    @XmlElement
    @Getter(fieldName = "b")
    public boolean isB() {
        return b;
    }
}