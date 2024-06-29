package me.levansj01.verus.check.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.ServerVersion;
import me.levansj01.verus.data.version.ClientVersion;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface CheckInfo {
   double minViolations() default 0.0D;

   boolean logData() default false;

   ClientVersion[] unsupportedVersions() default {};

   boolean schem() default false;

   CheckVersion version() default CheckVersion.RELEASE;

   int maxViolations() default Integer.MAX_VALUE;

   int priority() default 1;

   ClientVersion unsupportedAtleast() default ClientVersion.NONE;

   boolean butterfly() default false;

   boolean blocks() default false;

   String subType();

   String friendlyName() default "";

   boolean heavy() default false;

   CheckType type();

   ServerVersion[] unsupportedServers() default {};

   ServerVersion unsupportedServerAtleast() default ServerVersion.NONE;
}
