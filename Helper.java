package io.algocasts.problem;

import java.util.*;

public class Helper {

  private Helper() {}

  /////////////////// Array ///////////////////////
  public static <T> String array2String(T[] array) {
    return list2String(Arrays.asList(array));
  }

  /////////////////// List ////////////////////////
  public static <T> String list2String(List<T> list) {
    if (list == null) return "null";
    StringBuilder sb = new StringBuilder("[");
    for (T e: list) {
      if (e == null) sb.append("null, ");
      else sb.append(e).append(", ");
    }
    if (sb.length() >= 2) sb.delete(sb.length()-2, sb.length());
    sb.append("]");
    return sb.toString();
  }

  @SafeVarargs
  @SuppressWarnings("varargs")
  public static <T> List<T> buildList(T... a) {
    return Arrays.asList(a);
  }

  public static <T> List<List<T>> buildListList(T[][] a) {
    List<List<T>> result = new ArrayList<>();
    for (T[] e: a) {
      result.add(Arrays.asList(e));
    }
    return result;
  }

  /////////////////// Linked List ////////////////////////
  public static String linkedList2String(ListNode list) {
    if (list == null) return "null";
    StringBuilder sb = new StringBuilder(String.valueOf(list.val));
    for (list = list.next; list != null; list = list.next) {
      sb.append(" -> ").append(list.val);
    }
    return sb.toString();
  }

  public static void printListNode(ListNode list) {
    System.out.println(linkedList2String(list));
  }

  public static ListNode buildLinkedList(int... array) {
    if (array.length == 0) return null;
    ListNode dummy = new ListNode(0), p = dummy;
    for (int i = 0; i < array.length; ++i) {
      p.next = new ListNode(array[i]);
      p = p.next;
    }
    return dummy.next;
  }

  /**
   * 根据数组和环的开始节点下标，构建一个带环链表。
   *
   * @param array 整数数组
   * @param startNodeIndex 0-based. 该下标指向的数字作为链表中环的开始节点
   * @return
   */
  public static ListNode buildLinkedListWithCycle(int[] array, int startNodeIndex) {
    if (array == null || array.length == 0) return null;
    ListNode dummy = new ListNode(0), p = dummy, start = null;
    for (int i = 0; i < array.length; ++i, --startNodeIndex) {
      p.next = new ListNode(array[i]);
      p = p.next;
      if (startNodeIndex == 0) start = p;
    }
    p.next = start;
    return dummy.next;

  }

  /////////////////// Tree ////////////////////////
  public static String tree2String(TreeNode root) {
    if (root == null) return "[]";
    List<Integer> list = new ArrayList<>();
    Queue<TreeNode> q = new LinkedList<>();
    q.add(root);
    while (!q.isEmpty()) {
      TreeNode node = q.poll();
      Integer value = node == null ? null : node.val;
      list.add(value);
      if (node != null) {
        q.add(node.left); q.add(node.right);
      }
    }
    // remove the trailing null
    while (list.get(list.size()-1) == null)
      list.remove(list.size()-1);
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < list.size()-1; ++i)
      sb.append(list.get(i)).append(',');
    return "[" + sb.append(list.get(list.size()-1)).toString() + "]";
  }

  public static TreeNode buildTree(int[] preorder, int[] inorder) {
    Map<Integer, Integer> inPos = new HashMap<>();
    for (int i = 0; i < inorder.length; ++i)
      inPos.put(inorder[i], i);
    return buildTree(preorder, 0, preorder.length-1, 0, inPos);
  }

  private static class Item {
    TreeNode parent;
    Integer childVal;
    boolean isLeft;
    Item(TreeNode parent, Integer childVal, boolean isLeft) {
      this.parent = parent;
      this.childVal = childVal;
      this.isLeft = isLeft;
    }
  }

  /*
   * 根据树的层序遍历数组，构建一棵二叉树。例如：
   * 输入：[3,2,3,null,3,null,1]
   *
   * 构建的二叉树是：
   *     3
   *    / \
   *   2   3
   *    \   \
   *     3   1
   *
   * 注意：如果某个位置上的节点为 null，那么它左右子树的 null 不需要写出来。
   * 比如对于下面这棵树：
   *     1
   *      \
   *       2
   *        \
   *         4
   * 应该表示为：[1,null,2,null,4]
   * 而不是：[1,null,2,null,null,null,4]
   *
   */
  public static TreeNode buildTree(Integer... nums) {
    if (nums == null || nums.length == 0) return null;
    TreeNode[] trees = new TreeNode[nums.length];
    for (int i = 0; i < nums.length; ++i)
      trees[i] = nums[i] == null ? null : new TreeNode(nums[i]);
    int p = 1;
    for (TreeNode tree : trees) {
      if (tree != null) {
        if (p < trees.length) tree.left = trees[p++];
        if (p < trees.length) tree.right = trees[p++];
      }
    }
    return trees[0];
  }

  public static TreeNode buildTreeOld(Integer... nums) {
    TreeNode dummy = new TreeNode(0);
    Queue<Item> q = new LinkedList<>();
    q.add(new Item(dummy, nums[0], true));
    int p = 1;

    while (!q.isEmpty()) {
      Item item = q.poll();
      TreeNode child = item.childVal == null ? null : new TreeNode(item.childVal);
      if (item.isLeft) item.parent.left = child;
      else item.parent.right = child;
      if (child != null) {
        if (p < nums.length) q.add(new Item(child, nums[p++], true));
        if (p < nums.length) q.add(new Item(child, nums[p++], false));
      }
    }
    return dummy.left;
  }

  /**
   * 在树 root 中找到节点值等于 value 的节点。如果找到，就返回这个节点；如果找不到就返回 null。
   */
  public static TreeNode findTreeNode(TreeNode root, int value) {
    if (root == null || root.val == value) return root;
    TreeNode left = findTreeNode(root.left, value);
    if (left != null) return left;
    return findTreeNode(root.right, value);
  }

  public static List<List<Integer>> levelOrderTraversal(TreeNode root) {
    if (root == null) return new ArrayList<>();
    List<List<Integer>> result = new ArrayList<>();
    Queue<TreeNode> q = new LinkedList<>();
    q.add(root);

    while (!q.isEmpty()) {
      List<Integer> elem = new ArrayList<>();
      int size = q.size();
      for (int i = 0; i < size; ++i) {
        TreeNode s = q.poll();
        elem.add(s.val);
        if (s.left != null) q.add(s.left);
        if (s.right != null) q.add(s.right);
      }
      result.add(elem);
    }

    return result;
  }

  public static void printTreeByLevel(TreeNode root){
    List<List<Integer>> result = levelOrderTraversal(root);
    for (List<Integer> level: result) {
      for (Integer num: level) {
        System.out.print(num + " ");
      }
      System.out.println();
    }
    System.out.println();
  }

  // [3,2,3,null,3,null,1]
  //     3
  //    / \
  //   2   3
  //    \   \
  //     3   1
  public static void printTreeByLevelWithNull(TreeNode root) {
    System.out.println(tree2String(root));
  }

  public static boolean isValidBST(TreeNode root) {
    return isValidBSTBound(root, null, null);
  }

  public static boolean isBalancedTree(TreeNode root) {
    return getHeightAndCheck(root) != -1;
  }

  public static boolean isSameTree(TreeNode p, TreeNode q) {
    if (p == null && q == null) return true;
    if (p == null || q == null) return false;
    return p.val == q.val && isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
  }



  private static final Random RANDOM = new Random();
  private static final int FACTOR = 10;

  public static void println(String str) {
    System.out.println(str);
  }

  public static void println() {
    System.out.println();
  }

  public static void printArray(final int[] arr) {
    if (arr == null || arr.length == 0) return;
    for (int i = 0; i < arr.length; ++i)
      System.out.print(arr[i] + " ");
    System.out.println();
  }

  public static void printArray(String message, final int[] arr) {
    System.out.println(message + ": ");
    printArray(arr);
  }

  public static void printMatrix(final int[][] matrix) {
    for (int i = 0; i < matrix.length; ++i) {
      for (int j = 0; j < matrix[0].length; ++j) {
        System.out.print(matrix[i][j] + " ");
      }
      System.out.println();
    }
    System.out.println();
  }

  public static void printList(final List<Integer> list) {
    for (int e: list) {
      System.out.print(e + " ");
    }
    System.out.println();
  }
  public static <T> void printListList(final List<List<T>> lists) {
    for (List<T> list: lists) {
      for (T e: list) {
        System.out.print(e + " ");
      }
      System.out.println();
    }
    System.out.println();
  }

  public static <K, V> void printMap(final Map<K, V> map) {
    System.out.println("{");
    for (Map.Entry<K, V> e: map.entrySet()) {
      System.out.println("  " + e.getKey() + " -> " + e.getValue() + ",");
    }
    System.out.println("}");
  }

  public static int getRandomInt(int upperBound) {
    return RANDOM.nextInt(upperBound);
  }

  // return a random int value between lowerBound(included) and upperBound(excluded)
  public static int getRandomInt(int lowerBound, int upperBound) {
    return RANDOM.nextInt(upperBound - lowerBound) + lowerBound;
  }

  public static int[] getRandomIntArray(int arrayLength) {
    int minValue = -arrayLength * FACTOR;
    int maxValue = arrayLength * FACTOR;
    int flag = RANDOM.nextInt(3);
    if (flag == 0) {
      // all positive (include 0)
      return getRandomIntArray(arrayLength, 0, maxValue);
    } else if (flag == 1) {
      // all negative
      return getRandomIntArray(arrayLength, minValue, 0);
    } else {
      // mixed positive and negative
      return getRandomIntArray(arrayLength, minValue, maxValue);
    }
  }


  // All elements are in [minValue, maxValue)
  public static int[] getRandomIntArray(int arrayLength, int minValue, int maxValue) {
    int[] arr = new int[arrayLength];
    for (int i = 0; i < arrayLength; ++i) {
      arr[i] = RANDOM.nextInt(maxValue - minValue) + minValue;
    }
    return arr;
  }

  public static int[][] getRandomMatrix(int row, int col, int minValue, int maxValue) {
    int[][] matrix = new int[row][col];
    for (int i = 0; i < row; ++i)
      for (int j = 0; j < col; ++j)
        matrix[i][j] = RANDOM.nextInt(maxValue - minValue) + minValue;

    return matrix;
  }

  public static int[] getSortedArray(int arrayLength) {
    int[] arr = getRandomIntArray(arrayLength);
    Arrays.sort(arr);
    return arr;
  }

  public static int[] sortByBuiltinMethod(final int[] arr) {
    if (arr == null) return null;
    int[] sorted = new int[arr.length];
    System.arraycopy(arr, 0, sorted, 0, arr.length);
    Arrays.sort(sorted);
    return sorted;
  }


  //////////////////// private method ///////////////////////
  private static TreeNode buildTree(
    int[] preorder, int preStart, int preEnd,
    int inStart, Map<Integer, Integer> inPos) {

    if (preStart > preEnd) return null;
    TreeNode root = new TreeNode(preorder[preStart]);
    int rootIdxInorder = inPos.get(preorder[preStart]);
    int leftLen = rootIdxInorder - inStart;
    root.left = buildTree(preorder, preStart+1, preStart+leftLen, inStart, inPos);
    root.right = buildTree(preorder, preStart+leftLen+1, preEnd, rootIdxInorder+1, inPos);
    return root;
  }


  private static boolean isValidBSTBound(TreeNode root, TreeNode lower, TreeNode upper) {
    if (root == null) return true;
    if (lower != null && lower.val >= root.val) return false;
    if (upper != null && upper.val <= root.val) return false;
    return isValidBSTBound(root.left, lower, root) && isValidBSTBound(root.right, root, upper);
  }

  private static int getHeightAndCheck(TreeNode root) {
    if (root == null) return 0;

    int left = getHeightAndCheck(root.left);
    if (left == -1) return -1;

    int right = getHeightAndCheck(root.right);
    if (right == -1) return -1;

    if (Math.abs(left - right) > 1) return -1;
    return Math.max(left, right) + 1;
  }

}
