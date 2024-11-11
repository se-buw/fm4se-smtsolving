package de.buw.fm4se.smtsolving.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.microsoft.z3.Z3Exception;

import de.buw.fm4se.smtsolving.Tasks;
import de.buw.fm4se.smtsolving.utils.FmPlay;
import de.buw.fm4se.smtsolving.utils.PuzzleTest;
import de.buw.fm4se.smtsolving.utils.Z3Utils;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class T2MathPuzzleTest {

  String code = FmPlay.getCodeFromPermalink(Tasks.task_2);
  String puzzle = PuzzleTest.readPuzzleFromMd();
  List<String> mdPuzzle = PuzzleTest.convertEmoji(puzzle);
  List<String> smt = PuzzleTest.generateSmtAssert(puzzle);

  @Test
  @Order(1)
  void testCheckFormula1() {
    testCheckFormula(0);
  }

  @Test
  @Order(2)
  void testCheckFormula2() {
    testCheckFormula(1);
  }

  @Test
  @Order(3)
  void testCheckFormula3() {
    testCheckFormula(2);
  }

  @Test
  @Order(4)
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
  @Order(5)
  void testCheckConclusion() {
    try {
      assertFalse(Z3Utils.isUnsatForConstraints(code, ""), "The puzzle has no solution");
    } catch (Z3Exception e) {
      fail("The encoding of the puzzle likely contains a syntax error");
    }
  }

}
