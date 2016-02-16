import javax.xml.bind.annotation.*;

@XmlRootElement(name = "test")
@XmlAccessorType(XmlAccessType.FIELD)
public class SimpleClass {

    @XmlAttribute
    public int id;

    @XmlElement
    private String myString;

    @XmlElement
    private int myInt;

    @XmlElement
    private boolean myBoolean;

    public SimpleClass() {
        this(0, "a", 10, false);
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

    public String getMyString() {
        return myString;
    }

    public int getMyInt() {
        return myInt;
    }

    public boolean isMyBoolean() {
        return myBoolean;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SimpleClass that = (SimpleClass) o;

        if (myInt != that.myInt) return false;
        if (myBoolean != that.myBoolean) return false;
        return !(myString != null ? !myString.equals(that.myString) : that.myString != null);

    }

    @Override
    public int hashCode() {
        int result = myString != null ? myString.hashCode() : 0;
        result = 31 * result + myInt;
        result = 31 * result + (myBoolean ? 1 : 0);
        return result;
    }
}