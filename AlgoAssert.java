import org.junit.Assert;

import java.util.*;
import java.util.function.Supplier;

public class AlgoAssert {

  private AlgoAssert() {}

  public static void assertEquals(ListNode expected, ListNode actual) {
    ListNode e = expected, a = actual;
    while (e != null && a != null) {
      if (e.val != a.val)
        throw new AssertionError("expected: " + Helper.linkedList2String(expected) + ", actual: " + Helper.linkedList2String(actual));
      e = e.next;
      a = a.next;
    }
    if (e != null || a != null)
      throw new AssertionError("expected: " + Helper.linkedList2String(expected) + ", actual: " + Helper.linkedList2String(actual));
  }

  public static void assertEquals(TreeNode expected, TreeNode actual) {
    if (!Helper.isSameTree(expected, actual)) {
      throw new AssertionError("expected: " + Helper.tree2String(expected) + ", actual: " + Helper.tree2String(actual));
    }
  }

  public static <T> void assertEquals(List<T> expecteds, List<T> actuals) {
    if (expecteds != null && actuals != null) {
      if (expecteds.size() != actuals.size())
        throw new AssertionError("expected: " + Helper.list2String(expecteds) + ", actual: " + Helper.list2String(actuals));
      for (int i = 0; i < expecteds.size(); ++i) {
        if (!expecteds.get(i).equals(actuals.get(i)))
          throw new AssertionError("expected: " + Helper.list2String(expecteds) + ", actual: " + Helper.list2String(actuals));
      }
    } else if (expecteds != null || actuals != null)
      throw new AssertionError("expected: " + Helper.list2String(expecteds) + ", actual: " + Helper.list2String(actuals));
  }

  public static <T> void assertUnorderEquals(List<T> expecteds, List<T> actuals) {
    assertEquals(sort(expecteds), sort(actuals));
  }

  public static <T> void assertEquals(Iterator<T> expecteds, Iterator<T> actuals) {
    assertEquals(toList(expecteds), toList(actuals));
  }

  public static <T> void assertUnorderEquals(Iterator<T> expecteds, Iterator<T> actuals) {
    assertUnorderEquals(toList(expecteds), toList(actuals));
  }

  private static <T> List<T> toList(Iterator<T> iter) {
    List<T> list = new ArrayList<>();
    iter.forEachRemaining(list::add);
    return list;
  }

  public static <T> List<T> toList(T[] arr) {
    if (arr == null) return null;
    return new ArrayList<>(Arrays.asList(arr));
  }

  public static List<Integer> toList(int[] arr) {
    if (arr == null) return null;
    List<Integer> list = new ArrayList<>();
    for (int e: arr) list.add(e);
    return list;
  }

  public static <T> void assertListListEquals(List<List<T>> expecteds, List<List<T>> actuals) {
    if (expecteds != null && actuals != null) {
      Assert.assertEquals(expecteds.size(), actuals.size());
      for (int i = 0; i < expecteds.size(); ++i)
        assertEquals(expecteds.get(i), actuals.get(i));
    } else if (expecteds != null || actuals != null)
      throw new AssertionError("For expecteds and actuals, one is null but another is not null.");
  }

  public static void assert2DArrayEquals(int[][] expecteds, int[][] actuals) {
    if (expecteds != null && actuals != null) {
      Assert.assertEquals(expecteds.length, actuals.length);
      for (int i = 0; i < expecteds.length; ++i)
        Assert.assertArrayEquals(expecteds[i], actuals[i]);
    } else if (expecteds != null || actuals != null) {
      throw new AssertionError("For expecteds and actuals, one is null but another is not null.");
    }
  }

  // 常见类型排序
  @SuppressWarnings("unchecked")
  private static <T> List<T> sort(List<T> list) {
    if (list == null || list.isEmpty()) return list;
    T e = list.get(0);
    if (e instanceof Integer) {
      Collections.sort((List<Integer>)list);
    } else if (e instanceof String) {
      Collections.sort((List<String>)list);
    }
    return list;
  }

  /**
   * 对于 List<List<T>>，外层 List 的元素不关心顺序，内层 List 的元素需要关心顺序。
   * 例如，以下两个 List **相同**，因为外层 List 的元素是什么顺序无所谓。
   * - [[2, 1], [4, 5]] 和 [[4, 5], [2, 1]]
   *
   * 而以下两个 List **不相同**，因为内层 List 的元素顺序是要考虑的。
   * - [[2, 1], [4, 5]] 和 [[1, 2], [4, 5]]
   */
  public static <T> void assertListListUnorderEquals(List<List<T>> expecteds, List<List<T>> actuals) {
    if (expecteds != null && actuals != null) {
      Assert.assertEquals(expecteds.size(), actuals.size());
      Set<List<T>> set = new HashSet<>(actuals);
      for (List<T> e: expecteds) {
        String expectedStr = Helper.list2String(e);
        if (!set.contains(e))
          throw new AssertionError("The following expected list is not in actual lists: " + expectedStr);
      }
    } else if (expecteds != null || actuals != null)
      throw new AssertionError("In expecteds and actuals, one if null but another is not null.");
  }

  /**
   * 对于 List<List<T>>，外层 List 的元素不关心顺序，内层 List 的元素也不关心顺序。
   * 例如，以下两个 List **相同**，因为外层和内层 List 的元素是什么顺序都无所谓。
   * - [[2, 1], [4, 5]] 和 [[4, 5], [1, 2]]
   */
  public static <T> void assertListListUnorderUnorderEquals(List<List<T>> expecteds, List<List<T>> actuals) {
    if (expecteds != null && actuals != null) {
      Assert.assertEquals(expecteds.size(), actuals.size());
      Set<List<T>> set = new HashSet<>();
      for (List<T> e: actuals)
        set.add(sort(e));
      for (List<T> e: expecteds) {
        String expectedStr = Helper.list2String(e);
        if (!set.contains(sort(e)))
          throw new AssertionError("The following expected list is not in actual lists: " + expectedStr);
      }
    } else if (expecteds != null || actuals != null)
      throw new AssertionError("In expecteds and actuals, one if null but another is not null.");
  }

  public static void assertArrayUnorderEquals(int[] expecteds, int[] actuals) {
    if (expecteds != null && actuals != null) {
      Arrays.sort(expecteds);
      Arrays.sort(actuals);
      Assert.assertArrayEquals(expecteds, actuals);
    } else Assert.assertArrayEquals(expecteds, actuals);
  }

  // assert that the elem is in the array.
  public static <T> void assertIn(T[] array, T elem) {
    boolean found = false;
    for (T e: array) {
      if (e.equals(elem)) {
        found = true;
        break;
      }
    }
    if (!found)
      throw new AssertionError(elem + " is not in " + Helper.array2String(array));
  }

  // TODO: Don't use the following method. It is experimental.
  public static <T> void assertRandomness(T[] resultSet, Supplier<T> rand) {
    int One_Million = 1000000;
    Map<T, Integer> counter = new HashMap<>();
    for (T e: resultSet) counter.put(e, 0);
    for (int i = 0; i < One_Million; ++i) {
      T elem = rand.get();
      Integer cnt = counter.get(elem);
      if (cnt == null) throw new AssertionError(elem + " is generated, but it is not in " + Helper.array2String(resultSet));
      counter.put(elem, cnt+1);
    }
    double expectedProbability = 1.0 / resultSet.length;
    for (Map.Entry<T, Integer> e: counter.entrySet()) {
      double prob = e.getValue() * 1.0 / One_Million;
      if (Math.abs(prob - expectedProbability) / expectedProbability > 0.1) // this is buggy
        throw new AssertionError("Probability of the following element is not proper: " + e.getKey() + ", probability: " + prob + ", expected probability: " + expectedProbability);
    }
  }

}
