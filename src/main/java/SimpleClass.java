import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "test")
public class SimpleClass {

    @XmlAttribute
    public int id;

    private String myString;
    private int myInt;
    private boolean myBoolean;

    public SimpleClass() {
    }

    public SimpleClass(int id, String s, int a, boolean b) {
        this.id = id;
        this.myString = s;
        this.myInt = a;
        this.myBoolean = b;
    }

    public int getId() {
        return id;
    }

    @XmlElement
    public String getMyString() {
        return myString;
    }

    @XmlElement
    public int getMyInt() {
        return myInt;
    }

    @XmlElement
    public boolean isMyBoolean() {
        return myBoolean;
    }
}