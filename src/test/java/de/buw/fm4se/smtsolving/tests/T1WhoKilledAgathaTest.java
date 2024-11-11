package de.buw.fm4se.smtsolving.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import de.buw.fm4se.smtsolving.Tasks;
import de.buw.fm4se.smtsolving.utils.FmPlay;
import de.buw.fm4se.smtsolving.utils.Z3Utils;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class T1WhoKilledAgathaTest {

  String code = FmPlay.getCodeFromPermalink(Tasks.task_1);

  @Test
  @Order(1)
  void testCheckFormula1() {
    String constraints = "(assert (not (exists ((x Person)) (killed x Agatha))))";
    assertTrue(Z3Utils.isUnsatForConstraints(code, constraints), "Encoding of formula 1 is wrong");
  }

  @Test
  @Order(2)
  void testCheckFormula2() {
    String c1 = "(assert (not (forall ((x Person) (y Person))(=> (killed x y) (hates x y)))))";
    String c2 = "(assert (not (forall ((x Person) (y Person))(=> (killed x y) (not (richer x y))))))";
    assertTrue(Z3Utils.isUnsatForConstraints(code, c1), "Encoding of formula 2 is wrong");
    assertTrue(Z3Utils.isUnsatForConstraints(code, c2), "Encoding of formula 2 is wrong");
  }

  @Test
  @Order(3)
  void testCheckFormula3() {
    String c1 = "(assert (not (forall ((x Person))(=> (hates Agatha x) (not (hates Charles x))))))";
    assertTrue(Z3Utils.isUnsatForConstraints(code, c1), "Encoding of formula 3 is wrong");
  }

  @Test
  @Order(4)
  void testCheckFormula4() {
    String c1 = "(assert (not (hates Agatha Agatha)))";
    String c2 = "(assert (not (hates Agatha Charles)))";
    assertTrue(Z3Utils.isUnsatForConstraints(code, c1), "Encoding of formula 4 is wrong");
    assertTrue(Z3Utils.isUnsatForConstraints(code, c2), "Encoding of formula 4 is wrong");
  }

  @Test
  @Order(5)
  void testCheckFormula5() {
    String constraints = "(assert (not (forall ((x Person))(=> (not (richer x Agatha)) (hates Butler x)))))";
    assertTrue(Z3Utils.isUnsatForConstraints(code, constraints), "Encoding of formula 5 is wrong");
  }

  @Test
  @Order(6)
  void testCheckFormula6() {
    String constraints = "(assert (not (forall ((x Person))(=> (hates Agatha x) (hates Butler x)))))";
    assertTrue(Z3Utils.isUnsatForConstraints(code, constraints), "Encoding of formula 6 is wrong");
  }

  @Test
  @Order(7)
  void testCheckFormula7() {
    String constraints = "(assert (not (forall ((x Person)) (exists ((y Person)) (not (hates x y))))))";
    assertTrue(Z3Utils.isUnsatForConstraints(code, constraints), "Encoding of formula 7 is wrong");
  }

  @Test
  @Order(8)
  void testCheckConclusion() {
    String constraints = "(assert (killed Agatha Agatha))";
    assertFalse(Z3Utils.isUnsatForConstraints(code, constraints), "Conclusion is wrong");
  }

}
