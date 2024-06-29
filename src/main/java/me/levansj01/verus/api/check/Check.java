package me.levansj01.verus.api.check;

import com.google.common.base.Objects;

public class Check {
   private final String type;
   private final String subType;
   private final String display;

   public Check(String type, String subType) {
      this(type, subType, (String)null);
   }

   public Check(String type, String subType, String display) {
      if (type == null) {
         throw new IllegalArgumentException("type cannot be null");
      } else if (subType == null) {
         throw new IllegalArgumentException("subType cacnnot be null");
      } else {
         this.type = type;
         this.subType = subType;
         this.display = display;
      }
   }

   public String getType() {
      return this.type;
   }

   public String getSubType() {
      return this.subType;
   }

   public String getDisplay() {
      return this.display;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         Check check = (Check)o;
         return this.type.equalsIgnoreCase(check.type) && this.subType.equalsIgnoreCase(check.subType);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Objects.hashCode(new Object[]{this.type.toLowerCase(), this.subType.toLowerCase()});
   }
}
