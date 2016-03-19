// Patrick Russell pcrussel
// 
// Name:
//   treemap
//
// Synopsis:
//   unbalanced binary tree for jxref.java
//
//
// $Id: treemap.java,v 1.15 2014-02-13 13:45:32-08 - - $
//

import static java.lang.System.*;

class treemap <key_t extends Comparable <key_t>, value_t> {

   private class node {
      key_t key;
      value_t value;
      node left;
      node right;

      //get functions
      value_t getValue(){
         return value;
      }

      key_t getKey(){
         return key;
      }

      node getLeft(){
         return left;
      }

      node getRight(){
         return right;
      }

      //set functions
      void setNode(key_t key, value_t value){
         this.key = key;
         this.value = value;
      }

      void setValue(value_t value){
          this.value = value;
      }

      void setLeft(node newLeft){
         left = newLeft;
      }

      void setRight(node newRight){
         right = newRight;
      }

   }
   private node root = null;

   public node getRoot(){
      return root;
   }

   //debug dump that prints out the tree
   private void debug_dump_rec (node tree, int depth) {
      out.printf("Tree is: %s", tree);
      if(tree == null) return;
      debug_dump_rec(tree.getLeft(), depth++);
      out.printf("%"+depth*2+"S%2d%s%s%s%n", "", depth);
      debug_dump_rec(tree.getRight(), depth++);
      //throw new UnsupportedOperationException ();
   }

   //adds the value node to the tree
   private void do_visit_rec (visitor <key_t, value_t> visit_fn,
                              node tree) {
      throw new UnsupportedOperationException ();
   }

   //gets the desired value fron the tree if found
   public value_t get (key_t key) {
      throw new UnsupportedOperationException ();
   }

   //visits the node and finds a value, if it finds it, it is
   //replaced and the old value is returned. Otherwise,
   //a new leaf of the tree is made, and the value
   //attached to it.
   public value_t put (key_t key, value_t value) {
      node tmp = new node();
      tmp.setNode(key, value);
      value_t found;
      String sKey = (String)key;
      String sCompare = null;
      
      if (root != null){
         sCompare = (String)root.getKey();
      }
      //if the root is null, return null
      if (root == null){
        found = makeNewNode(root, key, value);
        return found;
      }
      if(root.getLeft() != null){
         node left = root.getLeft();
         found = left.put(key, value);
      }
      //if the key matches, swap the values in the node 
      //and return the old value

      if (sKey.compareTo(sCompare) == 0 && found == null){
        found = root.getValue();
        root.setValue(value);
        return found;
      }

      if(root.getRight() != null){
         node right = root.getRight();
         found = right.put(key, value);
      }
      //if the key is not found, set an empty leg of the 
      //tree to be the value
      if (found == null){
      /*   if (root == null){
           root = tmp;
         }else if (root.getLeft() == null && 
                   root.getRight() == null){
           root.setRight(tmp);
           return null;
         }else if (root.getRight == null){
           root.setRight(tmp);
           return null;
         }else if (root.getLeft == null){
           root.setLeft(tmp);
           return null;
         }else{
      */
         found = makeNewNode(root, key, value);
      }
      return found;
      //throw new UnsupportedOperationException(); 
   }

   public value_t makeNewNode(node root, key_t key, value_t value){
      node tmp = new node();
      tmp.setNode(key, value);
      if (root == null){
         root = tmp;
         return null;
      }else if(root.getLeft() == null){
         root.setLeft(tmp);
         return null;
      }else if(root.getRight() == null){
         root.setRight(tmp);
         return null;
      }else{
         makeNewNode(root.right, key, value);
      }
      return null;
   }

   //dumps the data fro the node
   public void debug_dump () {
      out.printf("debug dump was called");
      debug_dump_rec (root, 0);
   }

   //called if the program is not in debug mode
   public void do_visit (visitor <key_t, value_t> visit_fn) {
      do_visit_rec (visit_fn, root);
   }

}
