// package io.algocasts.problem;

import org.junit.Test;

/**
 * @author Hawstein
 */
public class BinaryTreeRightSideViewTest {
  /*
       1            <---
     /   \
    2     4         <---
     \
      8             <---

       1            <---
     /   \
    2     4         <---
   / \
  6   8             <---
 */

  @Test
  public void testBFSVersion() {
    BinaryTreeRightSideView b = new BinaryTreeRightSideView();
    AlgoAssert.assertEquals(
      Helper.buildList(1, 4, 8),
      b.rightSideViewBFS(Helper.buildTree(1, 2, 4, 6, 8))
    );
    AlgoAssert.assertEquals(
      Helper.buildList(1, 4, 10),
      b.rightSideViewBFS(Helper.buildTree(1, 2, 4, null, 8, null, 10))
    );
    AlgoAssert.assertEquals(
      Helper.buildList(1, 4, 8),
      b.rightSideViewBFS(Helper.buildTree(1, 2, 4, null, 8))
    );
    AlgoAssert.assertEquals(
      Helper.buildList(1, 2, 8),
      b.rightSideViewBFS(Helper.buildTree(1, 2, null, null, 8))
    );
  }

  @Test
  public void testDFSVersion() {
    BinaryTreeRightSideView b = new BinaryTreeRightSideView();
    AlgoAssert.assertEquals(
      Helper.buildList(1, 4, 8),
      b.rightSideViewDFS(Helper.buildTree(1, 2, 4, 6, 8))
    );
    AlgoAssert.assertEquals(
      Helper.buildList(1, 4, 10),
      b.rightSideViewDFS(Helper.buildTree(1, 2, 4, null, 8, null, 10))
    );
    AlgoAssert.assertEquals(
      Helper.buildList(1, 4, 8),
      b.rightSideViewDFS(Helper.buildTree(1, 2, 4, null, 8))
    );
    AlgoAssert.assertEquals(
      Helper.buildList(1, 2, 8),
      b.rightSideViewDFS(Helper.buildTree(1, 2, null, null, 8))
    );
  }

}
