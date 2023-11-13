package expression;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Turnstile implements Expression {
    final private List<Expression> left;
    final private Set<Expression> leftSet;
    final private Expression right;

    public Turnstile(List<Expression> left, Expression right) {
        this.left = left;
        this.leftSet = new HashSet<>(left);
        this.right = right;
    }

    public List<Expression> getLeftList() {
        return left;
    }

    public Expression getLeft() {
        return getRight();
    }

    public Set<Expression> getLeftSet() {
        return leftSet;
    }

    public Expression getRight() {
        return right;
    }

    @Override
    public boolean isImplication() {
        return false;
    }

    @Override
    public String getSign() {
        return "|-";
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Expression u : left) {
            sb.append(u);
            sb.append(",");
        }
        return "(|-" + sb.toString() + right.toString() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Turnstile that = (Turnstile) o;
        return Objects.equals(left, that.left) &&
                Objects.equals(right, that.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }
}
