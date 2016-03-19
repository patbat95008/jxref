// Patrick Russell pcrussel
// $Id: queue.java,v 1.6 2014-02-13 15:07:52-08 - - $

import java.util.Iterator;
import java.util.NoSuchElementException;

class queue <item_t> implements Iterable <item_t> {

   private class node {
      item_t item;
      node link;
   }
   private node head = null;
   private node tail = null;

   public boolean isempty () {
      return head == null;
   }

   //insert a node onto the rear of the queue
   public void insert (item_t newitem) {
      node tmp = new node();
      tmp.item = newitem;
      if (head == null){
         head = tmp;
         tail = tmp;
      }else{
      tail.link = tmp;
      tail = tmp;
      }
   }

   public Iterator <item_t> iterator () {
      return new itor ();
   }

   class itor implements Iterator <item_t> {
      node next = head;
      public boolean hasNext () {
         return next != null;
      }
      public item_t next () {
         if (! hasNext ()) throw new NoSuchElementException ();
         item_t result = next.item;
         next = next.link;
         return result;
      }
      public void remove () {
         if(! hasNext()) throw new NoSuchElementException ();
         head = head.link;
      }
   }

}
