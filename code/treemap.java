// Patrick Russell pcrussel
// $Id: treemap.java,v 1.50 2014-02-15 13:43:27-08 - - $

import static java.lang.System.*;

class treemap <key_t extends Comparable <key_t>, value_t> {

   private class node {
      key_t key;
      value_t value;
      node left;
      node right;
   }
   private node root;

   //prints out the tree in debug mode
   private void debug_dump_rec (node tree, int depth) {
      if(tree == null) return;
      depth++;
      debug_dump_rec(tree.left, depth);
      out.printf("%"+depth*2+"S %2d %s %s %n", "", depth, tree.key, tree);
      //out.printf("node is: %s\n", tree.key);
      debug_dump_rec(tree.right, depth);
   }

   //visits the nodes in order, and prints the nodes
   private void do_visit_rec (visitor <key_t, value_t> visit_fn,
                              node tree) {
      if(tree == null) return;
      do_visit_rec(visit_fn, tree.left);
      visit_fn.visit(tree.key, tree.value);
      do_visit_rec(visit_fn, tree.right);
   }

   //gets the value from the specified node
   public value_t get (key_t key) {
     return null;
   }

   //Searches for the key. If key is found, value is swapped 
   //and the old value is returned. If no match is found, 
   //then a new node is created.
   public value_t put (key_t key, value_t value, boolean ignoreCaps) {
     node tmp = new node();
     tmp.key = key;
     tmp.value = value;
     value_t returnVal = null;
     if (root == null) root = tmp;

     root = rec_search(root, key, value, ignoreCaps);
     

     if(returnVal == tmp.value) return null;
     else return returnVal;
   }

   //searches for the key and returns the node, if found
   //returns null if the key wasn't found
   public node rec_search(node root, key_t key, value_t value, 
                          boolean ignoreCaps){
     int cmp;
     String strKey = (String) key;
     if (root == null) {
        node tmp = new node();
        tmp.key = key;
        tmp.value = value;
        return tmp;
     }
     String strCompare = (String) root.key;
     if(ignoreCaps) cmp = strKey.compareToIgnoreCase(strCompare);
     else cmp = key.compareTo(root.key);

     if (cmp<0){
        root.left = rec_search(root.left, key, value,
                               ignoreCaps);
     }else if (cmp>0) {
        root.right = rec_search(root.right, key, value,
                                ignoreCaps);
     }else{
        return root;
     }
     return root;      
   }

   //adds the node based on its lexicographic order to the 
   //other nodes in the list
   public void rec_add(node parent, node child, node addThis){
      if(child == null){
         child = addThis;
      }
      
      //compares lexicographic order of the tree with
      //the node supplied. If its order is positive,
      //the node heads left. If negitive, heads right
      if(child.key.compareTo(addThis.key) > 0){
         rec_add (child, child.left, addThis);
      }else if (child.key.compareTo(addThis.key) < 0){
         rec_add (child, child.right, addThis);
      }else if (child.key.compareTo(addThis.key) == 0){
         child = addThis;
      }
      
   }

   //calls the recursive debug print
   public void debug_dump () {
      debug_dump_rec (root, 0);
   }

   //calls the recursive visit function
   public void do_visit (visitor <key_t, value_t> visit_fn) {
      do_visit_rec (visit_fn, root);
   }

}
