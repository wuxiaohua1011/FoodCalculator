package example.com.foodcalculator;

/**
 * Created by micha on 3/15/2017.
 */

public class Food {
    private String group, name, ndbno;
    private int offset;

    public Food(String group, String name, String ndbno, int offset) {
        this.group = group;
        this.name = name;
        this.ndbno = ndbno;
        this.offset = offset;
    }

    public Food() {
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNdbno() {
        return ndbno;
    }

    public void setNdbno(String ndbno) {
        this.ndbno = ndbno;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
