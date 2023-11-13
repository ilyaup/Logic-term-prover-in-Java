package expression;

public interface Expression {

    boolean isImplication();

    String getSign();

    Expression getRight();

    Expression getLeft();

    @Override
    String toString();
}
