package solver;

import expression.Expression;
import expression.Turnstile;
import utils.Pair;

import java.util.*;


// Map.Entry = Pair
// Map.entry = new Pair<>

public class MPSolver {

    private final Map<Map<Expression, Integer>, List<Pair<Integer, Expression>>> map = new HashMap<>(); //Map<context, List<expression>>

    public void addToMap(Turnstile turnstile, int id) {
        Pair<Map<Expression, Integer>, Pair<Integer, Expression>> entry = convert(turnstile, id);
        map.putIfAbsent(entry.getKey(), new ArrayList<>());
        map.get(entry.getKey()).add(entry.getValue());
    }

    public Pair<Boolean, Pair<Integer, Integer>> prove(Turnstile turnstile) {
        Pair<Map<Expression, Integer>, Pair<Integer, Expression>> entry = convert(turnstile, -1);
        if (map.containsKey(entry.getKey())) {
            for (Pair<Integer, Expression> u : map.get(entry.getKey())) {
                for (Pair<Integer, Expression> v : map.get(entry.getKey())) {
                    if (!u.getKey().equals(v.getKey())) {
                        if (u.getValue().isImplication()
                                && u.getValue().getLeft().equals(v.getValue())
                                && u.getValue().getRight().equals(entry.getValue().getValue())) {
                            return new Pair<>(true, new Pair<>(v.getKey(), u.getKey()));
                        }
                    }
                }
            }
        }
        return new Pair<>(false, new Pair<>(0, 0));
    }

    public Pair<Map<Expression, Integer>, Pair<Integer, Expression>> convert(Turnstile turnstile, int id) {
        Map<Expression, Integer> mapHyp = new HashMap<>();
        for (Expression u : turnstile.getLeftList()) {
            mapHyp.putIfAbsent(u, 0);
            mapHyp.put(u, mapHyp.get(u) + 1);
        }
        return new Pair<>(mapHyp, new Pair<>(id, turnstile.getRight()));
    }
}
