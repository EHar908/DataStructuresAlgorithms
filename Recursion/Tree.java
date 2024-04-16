import java.util.*;

public class Tree<E extends Comparable<? super E>> {
    private BinaryTreeNode root;  // Root of tree
    private String name;     // Name of tree

    /**
     * Create an empty tree
     *
     * @param label Name of tree
     */
    public Tree(String label) {
        name = label;
    }

    /**
     * Create BST from ArrayList
     *
     * @param arr   List of elements to be added
     * @param label Name of tree
     */
    public Tree(ArrayList<E> arr, String label) {
        name = label;
        for (E key : arr) {
            insert(key);
        }
    }

    /**
     * Create BST from Array
     *
     * @param arr   List of elements to be added
     * @param label Name of  tree
     */
    public Tree(E[] arr, String label) {
        name = label;
        for (E key : arr) {
            insert(key);
        }
    }

    public String traverse(BinaryTreeNode node, int depth){
        String result = "";
        if(node == null){
            return "";
        }
        result += traverse(node.right, depth+1);
        for(int i = 0; i < depth; i++){
            result += "  ";
        }
        result = result + node.key;
        if(node.parent == null){
            result += "[no parent]";
        }
        else{
            result = result + "[" + node.parent.key + "]";
        }
        result += "\n";
        result += traverse(node.left, depth+1);
        return result;
    }

    public String inOrderTraverse(BinaryTreeNode node){
        String result = "";
        if(node == null){
            return "";
        }
        result += inOrderTraverse(node.left);
        result = result + " " + node.key;
        result += inOrderTraverse(node.right);
        return result;
    }

    /**
     * Return a string containing the tree contents as a tree with one node per line
     */
    @Override
    public String toString() {
        return traverse(root, 0);
    }

    /**
     * Return a string containing the tree contents as a single line
     */
    public String inOrderToString() {
        String result = name + ": ";
        result += inOrderTraverse(root);
        return result;
    }

    /**
     * reverse left and right children recursively
     */
    public void flip() {
        recursiveFlipper(root);
    }

    public void recursiveFlipper(BinaryTreeNode node){
        if(node == null){
            return;
        }
        recursiveFlipper(node.right);
        recursiveFlipper(node.left);
        BinaryTreeNode temp = node.left;
        node.left = node.right;
        node.right = temp;
    }

    /**
     * Returns the in-order successor of the specified node
     * @param node node from which to find the in-order successor
     */
    public BinaryTreeNode inOrderSuccessor(BinaryTreeNode node) {
        //Ran out of time to complete this method.
        return null;
    }

    /**
     * Counts number of nodes in specified level
     *
     * @param level Level in tree, root is zero
     * @return count of number of nodes at specified level
     */
    public int nodesInLevel(int level) {
        return countNodesAtLevel(root, 0, level);
    }

    private int countNodesAtLevel(BinaryTreeNode node, int depth, int targetLevel){
        int counter = 0;
        if(node == null){
            return 0;
        }
        else if(targetLevel == 0){
            return 1;
        }
        else if(depth == targetLevel){
            counter += 1;
        }
        counter += countNodesAtLevel(node.right, depth + 1, targetLevel);
        counter += countNodesAtLevel(node.left, depth + 1, targetLevel);
        return counter;
    }

    /**
     * Print all paths from root to leaves
     */
    public void printAllPaths() {
        String paths = "";
        printingAllPaths(root, paths);
    }

    public void printingAllPaths(BinaryTreeNode node, String paths){
        if(node == null){
            return;
        }
        else if (node.left == null && node.right == null){
            paths += " " + node.key;
            System.out.println(paths);
        }
            paths += " " + node.key;
            printingAllPaths(node.left, paths);
            printingAllPaths(node.right, paths);
    }

    /**
     * Counts all non-null binary search trees embedded in tree
     *
     * @return Count of embedded binary search trees
     */
    public int countBST() {
        return countingBST(root);
    }

    public int countingBST(BinaryTreeNode node) {
        int counter = 0;
        if(node == null){
            return 0;
        }
        if(identifyBST(node)){
            counter += 1;
        }

        counter += countingBST(node.left);
        counter += countingBST(node.right);

        return counter;
    }

    public boolean identifyBST(BinaryTreeNode node){
        boolean result = false;
        if(node.left == null && node.right == null){
            return true;
        }
        if(node.left != null && node.left.key.compareTo(node.key) < 0 ||
                node.right != null && node.right.key.compareTo(node.key) > 0){
            return true;
        }
        return result;

    }



    /**
     * Insert into a bst tree; duplicates are allowed
     *
     * @param x the item to insert.
     */
    public void insert(E x) {
        root = insert(x, root, null);
    }

    public BinaryTreeNode traverseTreeForKey(BinaryTreeNode node, E key){
        if(node.key == key){
            return node;
        }
        else if(node.key.compareTo(key) < 0 ){
            traverseTreeForKey(node.right, key);
        }
        else if(node.key.compareTo(key) > 0){
            traverseTreeForKey(node.left, key);
        }
        return null;
    }

    public BinaryTreeNode getByKey(E key) {
        return traverseTreeForKey(root, key);
    }

    /**
     * Balance the tree
     */
    public void balanceTree() {
        String[] stringOrderTree = inOrderTraverse(root).split(" ");
        String[] newList = Arrays.copyOfRange(stringOrderTree, 1, stringOrderTree.length);
        ArrayList<E> newNumList = new ArrayList<E>();
        for(int i = 0; i < newList.length; i++){
            newNumList.add((E)(Integer.valueOf(newList[i])));
        }
        root = null; //Empty the tree
        makeBalancedTree(0, newNumList.size(), newNumList);
    }

    public void makeBalancedTree(int i, int j, ArrayList<E> list){
        if(i >= j){
            return;
        }
        int mid = (i + j) / 2;
        insert(list.get(mid));
        makeBalancedTree(mid + 1, j, list);
        makeBalancedTree(i, mid, list);
    }

    /**
     * Internal method to insert into a subtree.
     * In tree is balanced, this routine runs in O(log n)
     *
     * @param x the item to insert.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private BinaryTreeNode insert(E x, BinaryTreeNode t, BinaryTreeNode parent) {
        if (t == null) return new BinaryTreeNode(x, null, null, parent);

        int compareResult = x.compareTo(t.key);
        if (compareResult < 0) {
            t.left = insert(x, t.left, t);
        } else {
            t.right = insert(x, t.right, t);
        }

        return t;
    }


    /**
     * Internal method to find an item in a subtree.
     * This routine runs in O(log n) as there is only one recursive call that is executed and the work
     * associated with a single call is independent of the size of the tree: a=1, b=2, k=0
     *
     * @param x is item to search for.
     * @param t the node that roots the subtree.
     *          SIDE EFFECT: Sets local variable curr to be the node that is found
     * @return node containing the matched item.
     */
    private boolean contains(E x, BinaryTreeNode t) {
        if (t == null)
            return false;

        int compareResult = x.compareTo(t.key);

        if (compareResult < 0)
            return contains(x, t.left);
        else if (compareResult > 0)
            return contains(x, t.right);
        else {
            return true;    // Match
        }
    }

    // Basic node stored in unbalanced binary trees
    public class BinaryTreeNode {
        E key;            // The data/key for the node
        BinaryTreeNode left;   // Left child
        BinaryTreeNode right;  // Right child
        BinaryTreeNode parent; //  Parent node

        // Constructors
        BinaryTreeNode(E theElement) {
            this(theElement, null, null, null);
        }

        BinaryTreeNode(E theElement, BinaryTreeNode lt, BinaryTreeNode rt, BinaryTreeNode pt) {
            key = theElement;
            left = lt;
            right = rt;
            parent = pt;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Node:");
            sb.append(key);
            if (parent == null) {
                sb.append("<>");
            } else {
                sb.append("<");
                sb.append(parent.key);
                sb.append(">");
            }

            return sb.toString();
        }

    }
}
