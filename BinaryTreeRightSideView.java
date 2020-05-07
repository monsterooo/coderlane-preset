package io.algocasts.problem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author Hawstein
 */
public class BinaryTreeRightSideView {

  // Time: O(n), Space: O(n)
  public List<Integer> rightSideViewBFS(TreeNode root) {
    if (root == null) return Collections.emptyList();
    List<Integer> result = new ArrayList<>();
    Queue<TreeNode> q = new LinkedList<>();
    q.add(root);

    while (!q.isEmpty()) {
      result.add(q.peek().val);
      int size = q.size();
      for (int i = 0; i < size; ++i) {
        TreeNode node = q.poll();
        if (node.right != null) q.add(node.right);
        if (node.left != null) q.add(node.left);
      }
    }
    return result;
  }

  private void dfs(TreeNode root, List<Integer> result, int level) {
    if (root == null) return;
    if (level == result.size()) result.add(root.val);
    dfs(root.right, result, level+1);
    dfs(root.left, result, level+1);
  }

  // Time: O(n), Space: O(n)
  public List<Integer> rightSideViewDFS(TreeNode root) {
    List<Integer> result = new ArrayList<>();
    dfs(root, result, 0);
    return result;
  }

}
