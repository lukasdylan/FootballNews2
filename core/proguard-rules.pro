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

## KOIN
-keep class org.koin.** { *; }

## Retrofit 2
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions
-keepattributes *Annotation*
-keepattributes RuntimeVisibleAnnotations
-keepattributes RuntimeInvisibleAnnotations
-keepattributes RuntimeVisibleParameterAnnotations
-keepattributes RuntimeInvisibleParameterAnnotations
-keepattributes EnclosingMethod
-keepclasseswithmembers class * {
    @retrofit2.* <methods>;
}
-keepclasseswithmembers interface * {
    @retrofit2.* <methods>;
}
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}
-dontnote retrofit2.Platform
-dontnote retrofit2.Platform$IOS$MainThreadExecutor
-dontwarn retrofit2.Platform$Java8

## OKIO
-keep class sun.misc.Unsafe { *; }
-dontwarn java.nio.file.*
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn okio.**

## OkHttp
-keepattributes Signature
-keepattributes *Annotation*
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**
-keep class okhttp3.logging.** { *; }

## Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
    **[] $VALUES;
    public *;
}

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

## Moshi
-dontwarn org.jetbrains.annotations.**
-keep class kotlin.Metadata { *; }
-dontwarn kotlin.reflect.jvm.internal.**
-keepclassmembers class kotlin.Metadata {
    public <methods>;
}

## Kotlin Coroutine
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-keepclassmembernames class kotlinx.** {
    volatile <fields>;
}