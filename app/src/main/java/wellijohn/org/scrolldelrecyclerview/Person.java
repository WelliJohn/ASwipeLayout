package wellijohn.org.scrolldelrecyclerview;

/**
 * @author: JiangWeiwei
 * @time: 2018/4/19-16:59
 * @email: wellijohn1991@gmail.com
 * @desc:
 */
public class Person {
    private String name;

    private boolean isOpen;

    public Person() {
    }

    public Person(String name, boolean isOpen) {
        this.name = name;
        this.isOpen = isOpen;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }
}
