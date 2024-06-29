package me.levansj01.verus.util.java;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;

public class WordUtils {
   public static String capitalizeFully(String var0, char[] var1) {
      int var2 = var1 == null ? -1 : var1.length;
      if (var0 != null && var0.length() != 0 && var2 != 0) {
         var0 = var0.toLowerCase();
         return capitalize(var0, var1);
      } else {
         return var0;
      }
   }

   public static String wrap(String var0, int var1, String var2, boolean var3) {
      if (var0 == null) {
         return null;
      } else {
         if (var2 == null) {
            var2 = SystemUtils.LINE_SEPARATOR;
         }

         if (var1 < 1) {
            var1 = 1;
         }

         int var4 = var0.length();
         int var5 = 0;
         StringBuffer var6 = new StringBuffer(var4 + 32);

         while(var4 - var5 > var1) {
            if (var0.charAt(var5) == ' ') {
               ++var5;
            } else {
               int var7 = var0.lastIndexOf(32, var1 + var5);
               if (var7 >= var5) {
                  var6.append(var0.substring(var5, var7));
                  var6.append(var2);
                  var5 = var7 + 1;
               } else if (var3) {
                  var6.append(var0.substring(var5, var1 + var5));
                  var6.append(var2);
                  var5 += var1;
               } else {
                  var7 = var0.indexOf(32, var1 + var5);
                  if (var7 >= 0) {
                     var6.append(var0.substring(var5, var7));
                     var6.append(var2);
                     var5 = var7 + 1;
                  } else {
                     var6.append(var0.substring(var5));
                     var5 = var4;
                  }
               }
            }
         }

         var6.append(var0.substring(var5));
         return var6.toString();
      }
   }

   public static String wrap(String var0, int var1) {
      return wrap(var0, var1, (String)null, false);
   }

   public static String capitalize(String var0) {
      return capitalize(var0, (char[])null);
   }

   public static String capitalize(String var0, char[] var1) {
      int var2 = var1 == null ? -1 : var1.length;
      if (var0 != null && var0.length() != 0 && var2 != 0) {
         int var3 = var0.length();
         StringBuffer var4 = new StringBuffer(var3);
         boolean var5 = true;

         for(int var6 = 0; var6 < var3; ++var6) {
            char var7 = var0.charAt(var6);
            if (isDelimiter(var7, var1)) {
               var4.append(var7);
               var5 = true;
            } else if (var5) {
               var4.append(Character.toTitleCase(var7));
               var5 = false;
            } else {
               var4.append(var7);
            }
         }

         return var4.toString();
      } else {
         return var0;
      }
   }

   public static String swapCase(String var0) {
      int var1;
      if (var0 != null && (var1 = var0.length()) != 0) {
         StringBuffer var2 = new StringBuffer(var1);
         boolean var3 = true;
         boolean var4 = false;
         boolean var5 = false;

         for(int var6 = 0; var6 < var1; ++var6) {
            char var7 = var0.charAt(var6);
            char var8;
            if (Character.isUpperCase(var7)) {
               var8 = Character.toLowerCase(var7);
            } else if (Character.isTitleCase(var7)) {
               var8 = Character.toLowerCase(var7);
            } else if (Character.isLowerCase(var7)) {
               if (var3) {
                  var8 = Character.toTitleCase(var7);
               } else {
                  var8 = Character.toUpperCase(var7);
               }
            } else {
               var8 = var7;
            }

            var2.append(var8);
            var3 = Character.isWhitespace(var7);
         }

         return var2.toString();
      } else {
         return var0;
      }
   }

   public static String initials(String var0, char[] var1) {
      if (var0 != null && var0.length() != 0) {
         if (var1 != null && var1.length == 0) {
            return "";
         } else {
            int var2 = var0.length();
            char[] var3 = new char[var2 / 2 + 1];
            int var4 = 0;
            boolean var5 = true;

            for(int var6 = 0; var6 < var2; ++var6) {
               char var7 = var0.charAt(var6);
               if (isDelimiter(var7, var1)) {
                  var5 = true;
               } else if (var5) {
                  var3[var4++] = var7;
                  var5 = false;
               }
            }

            return new String(var3, 0, var4);
         }
      } else {
         return var0;
      }
   }

   public static String uncapitalize(String var0) {
      return uncapitalize(var0, (char[])null);
   }

   public static String abbreviate(String var0, int var1, int var2, String var3) {
      if (var0 == null) {
         return null;
      } else if (var0.length() == 0) {
         return "";
      } else {
         if (var1 > var0.length()) {
            var1 = var0.length();
         }

         if (var2 == -1 || var2 > var0.length()) {
            var2 = var0.length();
         }

         if (var2 < var1) {
            var2 = var1;
         }

         StringBuffer var4 = new StringBuffer();
         int var5 = StringUtils.indexOf(var0, " ", var1);
         if (var5 == -1) {
            var4.append(var0.substring(0, var2));
            if (var2 != var0.length()) {
               var4.append(StringUtils.defaultString(var3));
            }
         } else if (var5 > var2) {
            var4.append(var0.substring(0, var2));
            var4.append(StringUtils.defaultString(var3));
         } else {
            var4.append(var0.substring(0, var5));
            var4.append(StringUtils.defaultString(var3));
         }

         return var4.toString();
      }
   }

   public static String initials(String var0) {
      return initials(var0, (char[])null);
   }

   private static boolean isDelimiter(char var0, char[] var1) {
      if (var1 == null) {
         return Character.isWhitespace(var0);
      } else {
         int var2 = 0;

         for(int var3 = var1.length; var2 < var3; ++var2) {
            if (var0 == var1[var2]) {
               return true;
            }
         }

         return false;
      }
   }

   public static String uncapitalize(String var0, char[] var1) {
      int var2 = var1 == null ? -1 : var1.length;
      if (var0 != null && var0.length() != 0 && var2 != 0) {
         int var3 = var0.length();
         StringBuffer var4 = new StringBuffer(var3);
         boolean var5 = true;

         for(int var6 = 0; var6 < var3; ++var6) {
            char var7 = var0.charAt(var6);
            if (isDelimiter(var7, var1)) {
               var4.append(var7);
               var5 = true;
            } else if (var5) {
               var4.append(Character.toLowerCase(var7));
               var5 = false;
            } else {
               var4.append(var7);
            }
         }

         return var4.toString();
      } else {
         return var0;
      }
   }

   public static String capitalizeFully(String var0) {
      return capitalizeFully(var0, (char[])null);
   }
}
