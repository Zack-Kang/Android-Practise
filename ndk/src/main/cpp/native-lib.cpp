//
// Created by zhoukang on 2020/8/28.
//
#include <jni.h>
#include <string>
#include <android/log.h>
#include <locale>

#define LOGI(...) ((void)__android_log_print(ANDROID_LOG_INFO, "NDK", __VA_ARGS__))
#define LOGW(...) ((void)__android_log_print(ANDROID_LOG_WARN, "NDK", __VA_ARGS__))
#define LOGE(...) ((void)__android_log_print(ANDROID_LOG_ERROR, "NDK", __VA_ARGS__))

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_ndk_MainActivity_staticMethod(JNIEnv *env, jobject thiz) {
    // TODO: implement SayHello()
    std::string hello = "Call Static Native Method, Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

jstring dynamicMedth(JNIEnv *env, jobject thiz, jstring jstr) {
    // TODO: implement SayHello()
    std::string hello = "Call Dynamic Register Native Method, Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

jobjectArray createJavaObjects(JNIEnv *env, jobject thiz) {
    // TODO: implement SayHello()
    jclass clazz = env->FindClass("com/example/ndk/Persion");
    jmethodID jmethodId = env->GetMethodID(clazz,"<init>","()V");
    jmethodID setName = env->GetMethodID(clazz,"setName","(Ljava/lang/String;)V");
    jmethodID setAge = env->GetMethodID(clazz,"setAge","(I)V");
    jobjectArray array = env->NewObjectArray(10, clazz, NULL);
    for (int i=0;i<10;i++){
        jobject object = env->NewObject(clazz, jmethodId);
        jstring name = env->NewStringUTF( "name-student");
        env->CallVoidMethod(object, setName,name);
        jint age = 18+i;
        env->CallVoidMethod(object, setAge,age);
        env->SetObjectArrayElement(array, i, object);
        env->DeleteLocalRef(object);
    }
    return array;
}

JNIEXPORT jint JNI_OnLoad(JavaVM* vm, void* reserved){
    LOGI("ONLOAD INIT");
    JNIEnv *env;
    if (vm->GetEnv(reinterpret_cast<void **>((void *) &env), JNI_VERSION_1_6) != JNI_OK){
        return JNI_ERR;
    }
    jclass clazz = env->FindClass("com/example/ndk/MainActivity");
    if (clazz == nullptr){
        return JNI_ERR;
    }
    const JNINativeMethod methods[] = {{"dynamicMedth","(Ljava/lang/String;)Ljava/lang/String;", reinterpret_cast<void *>(dynamicMedth)},
                                       {"createJavaObjects","()[Lcom/example/ndk/Persion;", reinterpret_cast<void *>(createJavaObjects)} };
    jint result = env->RegisterNatives(clazz, methods, sizeof(methods)/ sizeof(JNINativeMethod));
    if (result != JNI_OK){
        return result;
    }
    env->DeleteLocalRef(clazz);
    return JNI_VERSION_1_6;

}