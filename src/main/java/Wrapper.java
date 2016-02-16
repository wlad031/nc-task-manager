import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "item-list")
@XmlAccessorType(XmlAccessType.FIELD)
public class Wrapper<T> {

    @XmlElement(name = "amount")
    private int amount;

    @XmlElement(name = "item")
    private List<T> list;

    public Wrapper() {
        list = new ArrayList<T>();
        amount = 0;
    }

    public Wrapper(List<T> list) {
        this.list = list;
        this.amount = list.size();
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
        this.amount = list.size();
    }
}