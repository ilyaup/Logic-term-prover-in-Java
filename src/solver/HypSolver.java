package solver;

import expression.Turnstile;

import java.util.Collections;
import java.util.Map;
import utils.Pair;

// Map.Entry = Pair
// Map.entry = new Pair<>

public class HypSolver {


    public Pair<Boolean, Integer> prove(Turnstile turnstile) {
        int id = turnstile.getLeftList().indexOf(turnstile.getRight());
        if (id < 0) {
            return new Pair<>(false, 0);
        } else {
            return new Pair<>(true, id + 1);
        }
    }
}
