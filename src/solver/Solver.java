package solver;

import expression.Expression;
import expression.Turnstile;
import parser.Parser;

import utils.Pair;

// Map.Entry = Pair
// Map.entry = new Pair<>

public class Solver {

    DedSolver dedSolver = new DedSolver();
    HypSolver hypSolver = new HypSolver();
    MPSolver mpSolver = new MPSolver();
    AxSolver axSolver = new AxSolver();

    public String solve(String str, Integer id) {
        Turnstile turnstile = new Parser(str).run();
        str = "[" + id + "] " + str;
        String ans = str + " [Incorrect]";
        Pair<Boolean, Integer> solved = dedSolver.prove(turnstile);
        Boolean ready = solved.getKey();
        if (solved.getKey()) {
            ans = str + " [Ded. " + solved.getValue() + "]";
        }
        if (!ready) {
            solved = hypSolver.prove(turnstile);
            ready = solved.getKey();
            if (solved.getKey()) {
                ans = str + " [Hyp. " + solved.getValue() + "]";
            }
        }
        if (!ready) {
            solved = axSolver.prove(turnstile);
            ready = solved.getKey();
            if (solved.getKey()) {
                ans = str + " [Ax. sch. " + solved.getValue() + "]";
            }
        }
        if (!ready) {
            Pair<Boolean, Pair<Integer, Integer>> solvedMP = mpSolver.prove(turnstile);
            ready = solvedMP.getKey();
            if (solvedMP.getKey()) {
                ans = str + " [M.P. " + solvedMP.getValue().getKey() + ", " + solvedMP.getValue().getValue() + "]";
            }
        }
        dedSolver.addToMap(turnstile, id);
        mpSolver.addToMap(turnstile, id);
        return ans;
    }
}
