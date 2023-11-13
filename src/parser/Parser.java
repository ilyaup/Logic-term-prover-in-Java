package parser;

import expression.*;

import java.util.ArrayList;
import java.util.List;

public class Parser {

    int pos;
    String line;

    public Parser(String line) {
        this.line = line + '#';
        pos = 0;
    }

    public Turnstile run() {
        return line();
    }

    public Expression e() {
        return expression();
    }

    private boolean isValidChar() {
        char ch = line.charAt(pos);
        return Character.isDigit(ch) || Character.isLetter(ch) || ch == '\'';
    }

    private boolean skip(String s) {
        if (line.startsWith(s, pos)) {
            pos += s.length();
            return true;
        }
        return false;
    }

    private Turnstile line() {
        return new Turnstile(context(), expression());
    }

    private List<Expression> context() {
        List<Expression> x = new ArrayList<>();
        Expression expression = expression();
        if (expression != null) { // kostyl if there is no hypothesis
            x.add(expression);
        }
        while (skip(",")) {
            x.add(expression());
        }
        skip("|-");
        return x;
    }

    private Expression expression() {
        Expression x = disjunction();
        if (skip("->")) {
            x = new Implication(x, expression()); //hz norm li poryadok // vrode norm
        }
        return x;
    }

    private Expression disjunction() {
        Expression x = conjunction();
        while (skip("|")) {
            if (skip("-")) { // It's not disj, go out
                return x;
            }
            x = new Disjunction(x, conjunction());
        }
        return x;
    }

    private Expression conjunction() {
        Expression x = negation();
        while (skip("&")) {
            x = new Conjunction(x, negation());
        }
        return x;
    }

    private Expression negation() {
        Expression x;
        if (skip("(")) {
            x = expression();
            skip(")");
            return x;
        }
        if (skip("!")) {
            return new Negation(negation());
        }
        StringBuilder nameSB = new StringBuilder();
        while (isValidChar()) {
            nameSB.append(line.charAt(pos));
            pos++;
        }
        String name = nameSB.toString();
        if (name.equals("")) { // kostyl if there is no hypothesis
            return null;
        }
        return new Variable(name);
    }
}
