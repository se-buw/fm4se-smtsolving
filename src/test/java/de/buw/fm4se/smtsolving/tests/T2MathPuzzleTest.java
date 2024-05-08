package de.buw.fm4se.smtsolving.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.microsoft.z3.Z3Exception;

import de.buw.fm4se.smtsolving.Tasks;
import de.buw.fm4se.smtsolving.utils.FmPlay;
import de.buw.fm4se.smtsolving.utils.PuzzleTest;
import de.buw.fm4se.smtsolving.utils.Z3Utils;

public class T2MathPuzzleTest {

  String code = FmPlay.getCodeFromPermalink(Tasks.task_2);
  String puzzle = PuzzleTest.readPuzzleFromMd();
  List<String> mdPuzzle = PuzzleTest.convertEmoji(puzzle);
  List<String> smt = PuzzleTest.generateSmtAssert(puzzle);

  @Test
  void testCheckFormula1() {
    testCheckFormula(0);
  }

  @Test
  void testCheckFormula2() {
    testCheckFormula(1);
  }

  @Test
  void testCheckFormula3() {
    testCheckFormula(2);
  }

  @Test
  void testCheckFormula4() {
    testCheckFormula(3);
  }

  private void testCheckFormula(int i) {
    String message = String.format("Encoding of formula %s is wrong", mdPuzzle.get(i));
    try {
      assertTrue(Z3Utils.isUnsatForConstraints(code, smt.get(i)), message);
    } catch (Z3Exception e) {
      fail(message);
    }
  }

  @Test
  void testCheckConclusion() {
    try {
      assertFalse(Z3Utils.isUnsatForConstraints(code, ""), "The puzzle has no solution");      
    } catch (Z3Exception e) {
      fail("The encoding of the puzzle likely contains a syntax error");
    }
  }

}
