package expression;

import java.util.Objects;

public class Variable implements Expression {

    final private String name;

    public Variable(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Expression getRight() {
        return null;
    } // what if null, not this

    public Expression getLeft() {
        return getRight();
    }

    @Override
    public boolean isImplication() {
        return false;
    }

    @Override
    public String getSign() {
        return "var";
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Variable variable = (Variable) o;
        return Objects.equals(name, variable.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
