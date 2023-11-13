import java.util.Scanner;

import expression.Expression;
import expression.Turnstile;
import parser.*;
import solver.Solver;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Solver solver = new Solver();
        int id = 1;
        while (scanner.hasNext()) {
            String str = scanner.nextLine();
            System.out.println(solver.solve(str, id));
            id++;
        }
    }
//|-!!(1|2)->(1|2)
}
