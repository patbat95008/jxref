// Patrick Russell pcrussel
//
// Name:
//    jxref
//
// Synopsis:
//    jxref [-df] [filename...]
//
// Description:
//    Each file is read in sequence and a printout of the words
//    found in the file is generated at the end of each file,
//    one word per line, followed by a list of line numbers
//    where the word occurred. All normal output is sent to
//    stdout. Error messages are printed to stderr
//
// Options:
//    -d Tree is dumped in debug format, showing each key along
//       with its level in the tree
//    -f Upper case letters are folded into lower case before
//       insertion into the binary search tree
//
// Operands:
//     Each operand is a filename, each file causing to be
//     created a new tree. If any filename is specified 
//     as a minus sign (-), stdin is read at that point.
//     If no filenames are specified, stdin is read. As
//     an output filename, the minus sign is used as the
//     name of stdin.
//
// Exit Status:
//    0 No errors
//
//    1 Errors occurred, either in scanning options 
//      or opening files
//
// $Id: jxref.java,v 1.47 2014-02-13 13:42:11-08 - - $
//

import java.io.*;
import java.util.Scanner;
import static java.lang.System.*;

class jxref {
   static final String STDIN_NAME = "-";

   static class printer implements visitor <String, queue <Integer>> {
      public void visit (String key, queue <Integer> value) {
         out.printf ("%s %s", key, value);
         for (int linenr: value) out.printf (" %d", linenr);
         out.printf ("%n");
      }
   }

   static void xref_file (String filename, Scanner scan) {
      treemap <String, queue <Integer>> map =
            new treemap <String, queue <Integer>> ();
      queue lineNum = new queue <Integer> ();
      queue foundLine = new queue <Integer>();
      for (int linenr = 1; scan.hasNextLine (); ++linenr) {
         for (String word: scan.nextLine().split ("\\W+")) {
            if (word.matches ("^\\d*$")) continue;
           lineNum.insert(linenr);
           foundLine = map.put(word, lineNum);
           out.printf ("%s: %d: %s%n", filename, linenr, word);
         }
      }
      map.debug_dump();
      //visitor <String, queue <Integer>> print_fn = new printer ();
      //map.do_visit (print_fn);
   }

   public static void main (String[] args) {
      boolean isDebug = false;//tongles debug mode(-d)
      boolean ignoreCase = false;//tongles ignore case(-f)
      String command = null;//the command string
      int filenameStart = 0;//where the files actually start

      if (args.length > 1 && args[0].startsWith("-")){
          command = (String)args[0];
      }

      //if the first argument starts with a minus,
      //it is a command
      if (command != null){
        
         //used .contains so that any order of
         //d and f can be used as long as they
         //begin with a minus sign
         if (command.contains("d")){
           isDebug = true;
           //out.printf("debugmode = on\n");
           filenameStart = 1;
         }
         if(command.contains("f")){
           ignoreCase = true;
           //out.printf("ignorecase = on\n");
           filenameStart = 1;
         }
         if(!command.contains("d") && !command.contains("f")){
           out.printf("stupid, that's not a command\n");
         }
      }
      

      if (args.length == 0) {
         xref_file (STDIN_NAME, new Scanner (in));
      }else {
         for (int argi = filenameStart; argi < args.length; ++argi) {
            String filename = args[argi];
            //skip over filenames
            if (filename.equals (STDIN_NAME)) {
               xref_file (STDIN_NAME, new Scanner (in));
            }else {
               try {
                  Scanner scan = new Scanner (new File (filename));
                  xref_file (filename, scan);
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

