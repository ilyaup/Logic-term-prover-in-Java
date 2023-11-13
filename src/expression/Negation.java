package expression;

import java.util.Objects;

public class Negation implements Expression {

    final private Expression negated;

    public Negation(Expression negated) {
        this.negated = negated;
    }

    public Expression getNegated() {
        return negated;
    }

    public Expression getRight() {
        return getNegated();
    }

    public Expression getLeft() {
        return getRight();
    }

    @Override
    public boolean isImplication() {
        return false;
    }

    @Override
    public String getSign() {
        return "!";
    }

    public String toString() {
        return "(!" + negated.toString() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Negation negation = (Negation) o;
        return Objects.equals(negated, negation.negated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(negated);
    }
}
