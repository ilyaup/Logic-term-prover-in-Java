package expression;

import java.util.Objects;

public class Conjunction implements Expression {
    private final Expression left;
    private final Expression right;

    public Conjunction(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    public Expression getLeft() {
        return left;
    }

    public Expression getRight() {
        return right;
    }

    public String toString() {
        return "(&," + left.toString() + "," + right.toString() + ")";
    }

    @Override
    public boolean isImplication() {
        return false;
    }

    @Override
    public String getSign() {
        return "&";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Conjunction that = (Conjunction) o;
        return Objects.equals(left, that.left) &&
                Objects.equals(right, that.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }
}
