package solver;

import expression.*;
import utils.Pair;

import java.util.*;

// Map.Entry = Pair
// AbstractMap.SimpleEntry = Pair
// Map.entry = new Pair<>

public class DedSolver {

    private final Map<Expression, Set<Pair<Integer, Map<Expression, Integer>>>> map = new HashMap<>();

    public void addToMap(Turnstile turnstile, Integer id) {
        Pair<Expression,
                Map<Expression, Integer>> entry = convert(turnstile);
        map.putIfAbsent(entry.getKey(), new HashSet<>());
        //map.get(entry.getKey()).add(entry.getValue());
        map.get(entry.getKey()).add(new Pair<>(id, entry.getValue()));
    }

    public Pair<Boolean, Integer> prove(Turnstile turnstile) {
        Pair<Expression, Map<Expression, Integer>> entry = convert(turnstile);
        if (map.containsKey(entry.getKey())) {
            for (Pair<Integer, Map<Expression, Integer>> u : map.get(entry.getKey())) {
                if (u.getValue().equals(entry.getValue())) {
                    return new Pair<>(true, u.getKey());
                }
            }
        }
        return new Pair<>(false, 0);
    }

    public Pair<Expression, Map<Expression, Integer>> convert (Turnstile turnstile) {
        Map<Expression, Integer> mapHyp = new HashMap<>();
        for (Expression u : turnstile.getLeftList()) {
            mapHyp.putIfAbsent(u, 0);
            mapHyp.put(u, mapHyp.get(u) + 1);
        }
        Expression right = turnstile.getRight();
        while (right.isImplication()) {
            mapHyp.putIfAbsent(right.getLeft(), 0);
            mapHyp.put(right.getLeft(), mapHyp.get(right.getLeft()) + 1);
            right = right.getRight();
        }
        return new Pair<>(right, mapHyp);
    }
}
