package ace.diablo.two.database.entities;

import javax.persistence.*;

@Entity
public class Variable {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;
    @Column(nullable = false, length = 255)
    private String name;
    @Column(nullable = false)
    private boolean isDynamic;
    @Column(nullable = false)
    private int byteSize;

    protected Variable() {
    }

    public Variable(String name, boolean isDynamic, int byteSize) {
        this.name = name;
        this.isDynamic = isDynamic;
        this.byteSize = byteSize;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDynamic() {
        return isDynamic;
    }

    public void setDynamic(boolean dynamic) {
        isDynamic = dynamic;
    }

    public int getByteSize() {
        return byteSize;
    }

    public void setByteSize(int byteSize) {
        this.byteSize = byteSize;
    }

    @Override
    public String toString() {
        return "Variable{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isDynamic=" + isDynamic +
                ", byteSize=" + byteSize +
                '}';
    }

    @Override
    public Variable clone() {
        return new Variable(name, isDynamic, byteSize);
    }
}
