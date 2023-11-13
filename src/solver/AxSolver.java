package solver;

import expression.Expression;
import expression.Turnstile;
import parser.Parser;

import java.util.*;

import utils.Pair;

// Map.Entry = Pair
// Map.entry = new Pair<>

public class AxSolver { //a = * b = $ c = #
    private Set<Pair<Expression, Integer>> axis1;
    private Set<Pair<Expression, Integer>> axis2;
    private Set<Pair<Expression, Integer>> axis3;
    private Set<Pair<Expression, Integer>> axis;

    public AxSolver() {
        axis1 = new HashSet<>();
        axis1.add(new Pair<>(new Parser("!!a->a").e(), 10)); // !!a -> a
        axis2 = new HashSet<>();
        axis2.add(new Pair<>(new Parser("a->b->a").e(), 1)); // a -> b -> a
        axis2.add(new Pair<>(new Parser("a->b->a&b").e(), 3)); // a -> b -> a & b
        axis2.add(new Pair<>(new Parser("a&b->a").e(), 4)); // a & b -> a
        axis2.add(new Pair<>(new Parser("a&b->b").e(), 5)); // a & b -> b
        axis2.add(new Pair<>(new Parser("a->a|b").e(), 6)); // a -> a | b
        axis2.add(new Pair<>(new Parser("b->a|b").e(), 7)); // b -> a | b
        axis2.add(new Pair<>(new Parser("(a->b)->(a->!b)->!a").e(), 9)); //(a -> b) -> (a -> !b) -> !a
        axis3 = new HashSet<>();
        axis3.add(new Pair<>(new Parser("(a->b)->(a->b->c)->(a->c)").e(), 2)); // (a -> b) -> (a -> b -> c) -> (a -> c)
        axis3.add(new Pair<>(new Parser("(a->c)->(b->c)->(a|b->c)").e(), 8)); // (a -> c) -> (b -> c) -> (a | b -> c)
        axis = new HashSet<>();
        axis.addAll(axis1);
        axis.addAll(axis2);
        axis.addAll(axis3);
    }

    public Pair<Boolean, Integer> prove(Turnstile turnstile) {
        Expression expression = turnstile.getRight();
        for (Pair<Expression, Integer> ax : axis) {
            if (new Resolver().run(ax.getKey(), expression)) {
                return new Pair<>(true, ax.getValue());
            }
        }
        return new Pair<>(false, 0);
    }

    private static class Resolver {
        Expression a;
        Expression b;
        Expression c;

        public Resolver() {
            a = null;
            b = null;
            c = null;
        }

        public Boolean run(Expression ax, Expression expression) {
            if (ax.getSign().equals("var")) {
                switch (ax.toString()) {
                    case ("a"):
                        if (a == null) {
                            a = expression;
                        } else if (!a.equals(expression)) {
                            return false;
                        }
                        break;
                    case ("b"):
                        if (b == null) {
                            b = expression;
                        } else if (!b.equals(expression)) {
                            return false;
                        }
                        break;
                    case ("c"):
                        if (c == null) {
                            c = expression;
                        } else if (!c.equals(expression)) {
                            return false;
                        }
                        break;
                    default:
                        throw new RuntimeException();
                }
                return true;
            } else if (ax.getSign().equals(expression.getSign())) {
                return run(ax.getLeft(), expression.getLeft()) && run(ax.getRight(), expression.getRight());
            } else {
                return false;
            }
        }
    }



}
