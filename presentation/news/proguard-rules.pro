# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
-renamesourcefileattribute SourceFile

## Android architecture components: Lifecycle
-keepclassmembers class * implements androidx.lifecycle.LifecycleObserver {
    <init>(...);
}
-keepclassmembers class * extends androidx.lifecycle.ViewModel {
    <init>(...);
}
-keepclassmembers class androidx.lifecycle.Lifecycle$State { *; }
-keepclassmembers class androidx.lifecycle.Lifecycle$Event { *; }
-keepclassmembers class * {
    @androidx.lifecycle.OnLifecycleEvent *;
}
-keepclassmembers class * implements androidx.lifecycle.LifecycleObserver {
    <init>(...);
}
-keep class * implements androidx.lifecycle.LifecycleObserver {
    <init>(...);
}
-keepclassmembers class androidx.lifecycle.** { *; }
-keep class androidx.lifecycle.* { *; }
-dontwarn androidx.lifecycle.*
-keep class * implements androidx.lifecycle.GeneratedAdapter {<init>(...);}

## DataBinding
-keepclassmembers class * extends androidx.databinding.ViewDataBinding {
    <init>(...);
}
-keepclassmembers class *.R$ {
    public static <fields>;
}
-keepclassmembers class androidx.databinding.** { *; }
-keep class androidx.databinding.* { *; }
-dontwarn androidx.databinding.*

-keepclassmembers enum * {
 public static **[] values();
 public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
 public static final android.os.Parcelable$Creator *;
}

-keepclassmembers class **.R$* {
 public static <fields>;
}
