package de.buw.fm4se.smtsolving.tests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import de.buw.fm4se.smtsolving.Tasks;
import de.buw.fm4se.smtsolving.utils.FmPlay;
import de.buw.fm4se.smtsolving.utils.Z3Utils;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class T3PcConfigurationTest {
  // Office: 283
  // Server: 307
  // Gaming: 1006
  // Video: 318

  String code = FmPlay.getCodeFromPermalink(Tasks.task_3);

  @Test
  @Order(1)
  void testPurposeOffice1() {
    assertFalse(Z3Utils.isUnsatForConstraints(code,
        "(assert (= purpose office))\n(assert (= budget 283))"),
        "For office purpose, €283 is enough, Check `(get-value (budget))`");
  }

  @Test
  @Order(2)
  void testPurposeOffice2() {
    assertTrue(Z3Utils.isUnsatForConstraints(code,
        "(assert (= purpose office))\n(assert (= budget 282))"),
        "For office purpose, the budget should be at least €283, Check `(get-value (budget))`");
  }

  @Test
  @Order(3)
  void testPurposeServer1() {
    assertFalse(Z3Utils.isUnsatForConstraints(code,
        "(assert (= purpose server))\n(assert (= budget 307))"),
        "For server purpose, €307 is enough, Check `(get-value (budget))`");
  }

  @Test
  @Order(4)
  void testPurposeServer2() {
    assertTrue(Z3Utils.isUnsatForConstraints(code,
        "(assert (= purpose server))\n(assert (= budget 306))"),
        "For server purpose, the budget should be at least €307, Check `(get-value (budget))`");
  }

  @Test
  @Order(5)
  void testPurposeGaming1() {
    assertFalse(Z3Utils.isUnsatForConstraints(code,
        "(assert (= purpose gaming))\n(assert (= budget 1006))"),
        "For gaming purpose, €1006 is enough, Check `(get-value (budget))`");
  }

  @Test
  @Order(6)
  void testPurposeGaming2() {
    assertTrue(Z3Utils.isUnsatForConstraints(code,
        "(assert (= purpose gaming))\n(assert (= budget 1005))"),
        "For gaming purpose, the budget should be at least €1006, Check `(get-value (budget))`");
  }

  @Test
  @Order(7)
  void testPurposeVideoEditing1() {
    assertFalse(Z3Utils.isUnsatForConstraints(code,
        "(assert (= purpose video))\n(assert (= budget 318))"),
        "For video editing purpose, €318 is enough, Check `(get-value (budget))`");
  }

  @Test
  @Order(8)
  void testPurposeVideoEditing2() {
    assertTrue(Z3Utils.isUnsatForConstraints(code,
        "(assert (= purpose video))\n(assert (= budget 317))"),
        "For video editing purpose, the budget should be at least €318, Check `(get-value (budget))`");
  }
}
