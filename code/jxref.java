// Patrick Russell pcrussel
//
// $Id: jxref.java,v 1.76 2014-02-15 13:58:19-08 - - $

import java.io.*;
import java.util.Scanner;
import static java.lang.System.*;

class jxref {
   static final String STDIN_NAME = "-";

   static class printer implements visitor <String, queue <Integer>> {
      public void visit (String key, queue <Integer> value) {
         out.printf ("%s", key);
         //for (int linenr: value) out.printf (" %d", linenr);
         out.printf ("%n");
      }
   }

   static void xref_file (String filename, Scanner scan, boolean isDebug,
                          boolean ignoreCaps) {
      treemap <String, queue <Integer>> map =
            new treemap <String, queue <Integer>> ();
      queue<Integer> lineNum = new queue <Integer> ();
      for (int linenr = 1; scan.hasNextLine (); ++linenr) {
         //lineNum.insert(linenr);
         for (String word: scan.nextLine().split ("\\W+")) {
            if (word.matches ("^\\d*$")) continue;
               
               map.put(word, lineNum, ignoreCaps);
               
         }
      }
      
      if(isDebug) map.debug_dump();
      visitor <String, queue <Integer>> print_fn = new printer ();
      if(!isDebug) map.do_visit (print_fn);
   }

   public static void main (String[] args) {
      String command;
      boolean isDebug = false;//debug dump (-d)
      boolean ignoreCaps = false;//merge cases (-f)
      int fileNameStart = 0;//if there's a command, don't try
                            //to read it as a filename
      if(args.length > 0) command = args[0];
 
      if (args.length > 1 && args[0].startsWith("-")){
         if (args[0].contains("d")){
            isDebug = true;
         }
         if (args[0].contains("f")){
            ignoreCaps = true;
         }
         fileNameStart = 1;
      }

      if (args.length == 0) {
         xref_file (STDIN_NAME, new Scanner (in),
                    isDebug, ignoreCaps);
      }else {
         for (int argi = fileNameStart; argi < args.length; ++argi) {
            String filename = args[argi];
            if (filename.equals (STDIN_NAME)) {
               xref_file (STDIN_NAME, new Scanner (in), 
                          isDebug, ignoreCaps);
            }else {
               try {
                  Scanner scan = new Scanner (new File (filename));
                  xref_file (filename, scan, isDebug, ignoreCaps);
                  scan.close ();
               }catch (IOException error) {
                  auxlib.warn (error.getMessage ());
               }
            }
         }
      }
      auxlib.exit ();
   }

}

