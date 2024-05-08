package de.buw.fm4se.smtsolving.utils;

import com.microsoft.z3.Context;
import com.microsoft.z3.Solver;
import com.microsoft.z3.Status;

public class Z3Utils {

  public static void main(String[] args) {
    String plink = "https://play.formal-methods.net/?check=SMT&p=ex-3-task-1";
    String code = FmPlay.getCodeFromPermalink(plink);
    String constraints = "(assert (not (exists ((x Person)) (killed x Agatha))))";
    System.out.println(isUnsatForConstraints(code, constraints));

  }

  public static boolean isUnsatForConstraints(String code, String constraints) {
    String formulaToCheck = code + "\n" + constraints;

    Context ctx = new Context();
    Solver solver = ctx.mkSolver();
    solver.add(ctx.parseSMTLIB2String(formulaToCheck, null, null, null, null)); 
    Boolean result = solver.check() == Status.UNSATISFIABLE;
    ctx.close();
    return result;
  }
  
}
