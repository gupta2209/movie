// ***** This file is automatically generated by SplitterFactoryTestUpdater.java

package daikon.test.split;

import daikon.*;
import daikon.split.*;
import gnu.getopt.*;
import java.io.*;
import java.util.*;
import junit.framework.*;
import org.junit.Test;
import org.plumelib.util.UtilPlume;
import static org.junit.Assert.fail;

import org.checkerframework.checker.nullness.qual.*;

/**
 * THIS CLASS WAS GENERATED BY SplitterFactoryTestUpdater. Therefore, it is a bad idea to directly
 * edit this class's code for all but temporary reasons. Any permanent changes should be made
 * through SplitterFactoryUpdater.
 *
 * <p>This class contains regression tests for the SplitterFactory class. The tests directly test
 * the Java files produced by the load_splitters method by comparing them against goal files. Note
 * that it is normal for some classes not to compile during this test.
 *
 * <p>These tests assume that the goal files are contained in the directory:
 * "daikon/test/split/targets/"
 *
 * <p>These tests ignore extra white spaces.
 */
public class SplitterFactoryTest {
  // Because the SplitterFactory sequentially numbers the
  // java files it produces, changing the order that the setUpTests
  // commands are run will cause the tests to fail.

  private static String targetDir = "daikon/test/split/targets/";

  private static @Nullable String tempDir = null;

  private static boolean saveFiles = false;

  private static String usage =
    UtilPlume.joinLines(
      "Usage:  java daikon.tools.CreateSpinfo FILE.java ...",
      "  -s       Save (do not delete) the splitter java files in the temp directory",
      "  -h       Display this usage message");

  public static void main(String[] args) {
    Getopt g = new Getopt("daikon.test.split.SplitterFactoryTest", args, "hs");
    int c;
    while ((c = g.getopt()) != -1) {
      switch (c) {
        case 's':
          saveFiles = true;
          break;
        case 'h':
          System.out.println(usage);
          System.exit(1);
          break;
        case '?':
          break;
        default:
          System.out.println("getopt() returned " + c);
          break;
      }
    }
  }

  private static void setUpTests() {
    List<String> spinfoFiles;
    List<String> declsFiles;
    createSplitterFiles(
        "daikon/test/split/targets/StreetNumberSet.spinfo", "daikon/test/split/targets/StreetNumberSet.decls");
    createSplitterFiles(
        "daikon/test/split/targets/Fib.spinfo", "daikon/test/split/targets/Fib.decls");
    createSplitterFiles(
        "daikon/test/split/targets/QueueAr.spinfo", "daikon/test/split/targets/QueueAr.decls");
    createSplitterFiles(
        "daikon/test/split/targets/BigFloat.spinfo", "daikon/test/split/targets/BigFloat.decls");
  }

  /** Sets up the test by generating the needed splitter java files. */
  private static void createSplitterFiles(String spinfo, String decl) {
    List<String> spinfoFiles = new ArrayList<>();
    spinfoFiles.add(spinfo);
    List<String> declsFiles = Collections.singletonList(decl);
    createSplitterFiles(spinfoFiles, declsFiles);
  }

  /** Sets up the test by generating the needed splitter java files. */
  private static void createSplitterFiles(List<String> spinfos, List<String> decls) {
    Set<File> spFiles = new HashSet<>();
    PptMap allPpts = new PptMap();
    for (String spinfo : spinfos) {
      spFiles.add(new File(spinfo));
    }
    try {
      if (saveFiles) {
        SplitterFactory.dkconfig_delete_splitters_on_exit = false;
      }
      PptSplitter.dkconfig_suppressSplitterErrors = true;
      Daikon.create_splitters(spFiles);
      for (String declsFile : decls) {
        FileIO.resetNewDeclFormat();
        FileIO.read_data_trace_file(declsFile, allPpts);
      }
      tempDir = SplitterFactory.getTempDir();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /** Returns true iff files are the same (ignoring extra white space). */
  public static void assertEqualFiles(String f1, String f2) {
    if (!UtilPlume.equalFiles(f1, f2)) {
      fail("Files " + f1 + " and " + f2 + " differ.");
    }
  }

  public static void assertEqualFiles(String f1) {
    assertEqualFiles(tempDir + f1, targetDir + f1 + ".goal");
  }

  @Test
  public static void testMapQuick1_StreetNumberSet_StreetNumberSet_0() {
    assertEqualFiles("MapQuick1_StreetNumberSet_StreetNumberSet_0.java");
  }

  @Test
  public static void testMapQuick1_StreetNumberSet_StreetNumberSet_1() {
    assertEqualFiles("MapQuick1_StreetNumberSet_StreetNumberSet_1.java");
  }

  @Test
  public static void testMapQuick1_StreetNumberSet_StreetNumberSet_2() {
    assertEqualFiles("MapQuick1_StreetNumberSet_StreetNumberSet_2.java");
  }

  @Test
  public static void testMapQuick1_StreetNumberSet_StreetNumberSet_3() {
    assertEqualFiles("MapQuick1_StreetNumberSet_StreetNumberSet_3.java");
  }

  @Test
  public static void testMapQuick1_StreetNumberSet_checkRep_4() {
    assertEqualFiles("MapQuick1_StreetNumberSet_checkRep_4.java");
  }

  @Test
  public static void testMapQuick1_StreetNumberSet_checkRep_5() {
    assertEqualFiles("MapQuick1_StreetNumberSet_checkRep_5.java");
  }

  @Test
  public static void testMapQuick1_StreetNumberSet_contains_6() {
    assertEqualFiles("MapQuick1_StreetNumberSet_contains_6.java");
  }

  @Test
  public static void testMapQuick1_StreetNumberSet_contains_7() {
    assertEqualFiles("MapQuick1_StreetNumberSet_contains_7.java");
  }

  @Test
  public static void testMapQuick1_StreetNumberSet_contains_8() {
    assertEqualFiles("MapQuick1_StreetNumberSet_contains_8.java");
  }

  @Test
  public static void testMapQuick1_StreetNumberSet_contains_9() {
    assertEqualFiles("MapQuick1_StreetNumberSet_contains_9.java");
  }

  @Test
  public static void testMapQuick1_StreetNumberSet_contains_10() {
    assertEqualFiles("MapQuick1_StreetNumberSet_contains_10.java");
  }

  @Test
  public static void testMapQuick1_StreetNumberSet_contains_11() {
    assertEqualFiles("MapQuick1_StreetNumberSet_contains_11.java");
  }

  @Test
  public static void testMapQuick1_StreetNumberSet_contains_12() {
    assertEqualFiles("MapQuick1_StreetNumberSet_contains_12.java");
  }

  @Test
  public static void testMapQuick1_StreetNumberSet_contains_13() {
    assertEqualFiles("MapQuick1_StreetNumberSet_contains_13.java");
  }

  @Test
  public static void testMapQuick1_StreetNumberSet_orderStatistic_14() {
    assertEqualFiles("MapQuick1_StreetNumberSet_orderStatistic_14.java");
  }

  @Test
  public static void testMapQuick1_StreetNumberSet_orderStatistic_15() {
    assertEqualFiles("MapQuick1_StreetNumberSet_orderStatistic_15.java");
  }

  @Test
  public static void testMapQuick1_StreetNumberSet_size_16() {
    assertEqualFiles("MapQuick1_StreetNumberSet_size_16.java");
  }

  @Test
  public static void testMapQuick1_StreetNumberSet_size_17() {
    assertEqualFiles("MapQuick1_StreetNumberSet_size_17.java");
  }

  @Test
  public static void testmisc_Fib_main_18() {
    assertEqualFiles("misc_Fib_main_18.java");
  }

  @Test
  public static void testDataStructures_QueueAr_isEmpty_19() {
    assertEqualFiles("DataStructures_QueueAr_isEmpty_19.java");
  }

  @Test
  public static void testDataStructures_QueueAr_isEmpty_20() {
    assertEqualFiles("DataStructures_QueueAr_isEmpty_20.java");
  }

  @Test
  public static void testDataStructures_QueueAr_isFull_21() {
    assertEqualFiles("DataStructures_QueueAr_isFull_21.java");
  }

  @Test
  public static void testDataStructures_QueueAr_isFull_22() {
    assertEqualFiles("DataStructures_QueueAr_isFull_22.java");
  }

  @Test
  public static void testDataStructures_QueueAr_getFront_23() {
    assertEqualFiles("DataStructures_QueueAr_getFront_23.java");
  }

  @Test
  public static void testDataStructures_QueueAr_getFront_24() {
    assertEqualFiles("DataStructures_QueueAr_getFront_24.java");
  }

  @Test
  public static void testDataStructures_QueueAr_dequeue_25() {
    assertEqualFiles("DataStructures_QueueAr_dequeue_25.java");
  }

  @Test
  public static void testDataStructures_QueueAr_dequeue_26() {
    assertEqualFiles("DataStructures_QueueAr_dequeue_26.java");
  }

  @Test
  public static void testDataStructures_QueueAr_dequeue_27() {
    assertEqualFiles("DataStructures_QueueAr_dequeue_27.java");
  }

  @Test
  public static void testDataStructures_QueueAr_dequeue_28() {
    assertEqualFiles("DataStructures_QueueAr_dequeue_28.java");
  }

  @Test
  public static void testDataStructures_QueueAr_enqueue_29() {
    assertEqualFiles("DataStructures_QueueAr_enqueue_29.java");
  }

  @Test
  public static void testDataStructures_QueueAr_enqueue_30() {
    assertEqualFiles("DataStructures_QueueAr_enqueue_30.java");
  }

  @Test
  public static void testDataStructures_QueueAr_enqueue_31() {
    assertEqualFiles("DataStructures_QueueAr_enqueue_31.java");
  }

  @Test
  public static void testDataStructures_QueueAr_enqueue_32() {
    assertEqualFiles("DataStructures_QueueAr_enqueue_32.java");
  }

  @Test
  public static void testMath__BigFloat_bdiv__33() {
    assertEqualFiles("Math__BigFloat_bdiv__33.java");
  }

  @Test
  public static void testMath__BigFloat_bdiv__34() {
    assertEqualFiles("Math__BigFloat_bdiv__34.java");
  }

  @Test
  public static void testMath__BigFloat_bdiv__35() {
    assertEqualFiles("Math__BigFloat_bdiv__35.java");
  }

  @Test
  public static void testMath__BigFloat_bdiv__36() {
    assertEqualFiles("Math__BigFloat_bdiv__36.java");
  }

  @Test
  public static void testMath__BigFloat_bdiv__37() {
    assertEqualFiles("Math__BigFloat_bdiv__37.java");
  }

  @Test
  public static void testMath__BigFloat_bdiv__38() {
    assertEqualFiles("Math__BigFloat_bdiv__38.java");
  }

  @Test
  public static void testMath__BigFloat_bdiv__39() {
    assertEqualFiles("Math__BigFloat_bdiv__39.java");
  }

  @Test
  public static void testMath__BigFloat_bmul__40() {
    assertEqualFiles("Math__BigFloat_bmul__40.java");
  }

  @Test
  public static void testMath__BigFloat_bmul__41() {
    assertEqualFiles("Math__BigFloat_bmul__41.java");
  }

  @Test
  public static void testMath__BigFloat_bmul__42() {
    assertEqualFiles("Math__BigFloat_bmul__42.java");
  }

  @Test
  public static void testMath__BigFloat_bmul__43() {
    assertEqualFiles("Math__BigFloat_bmul__43.java");
  }

}
