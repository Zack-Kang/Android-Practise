//
// Created by zhoukang on 2020/8/28.
//
#include <jni.h>
#include <string>


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
    jmethodID jmethodId = env->GetMethodID(clazz,"<init>");
    jobjectArray array = env->NewObjectArray(5, clazz, NULL);
    jobject object =
    env->SetObjectArrayElement(array, 0, )
    return env->NewStringUTF(hello.c_str());
}

JNIEXPORT jint JNI_OnLoad(JavaVM* vm, void* reserved){
    JNIEnv *env;
    if (vm->GetEnv(reinterpret_cast<void **>((void *) &env), JNI_VERSION_1_6) != JNI_OK){
        return JNI_ERR;
    }
    jclass clazz = env->FindClass("com/example/ndk/MainActivity");
    if (clazz == nullptr){
        return JNI_ERR;
    }
    const JNINativeMethod methods[] = {{"dynamicMedth","(Ljava/lang/String;)Ljava/lang/String;", reinterpret_cast<void *>(dynamicMedth)}};
    jint result = env->RegisterNatives(clazz, methods, sizeof(methods)/ sizeof(JNINativeMethod));
    if (result != JNI_OK){
        return result;
    }
    env->DeleteLocalRef(clazz);

    clazz = env->FindClass("com/example/ndk/Persion");
    if (clazz == nullptr){
        return JNI_ERR;
    }
    const JNINativeMethod methods1[] = {{"createJavaObjects","()[Lcom/example/ndk/Persion;", reinterpret_cast<void *>(createJavaObjects)}};
    result = env->RegisterNatives(clazz, methods1, sizeof(methods1)/ sizeof(JNINativeMethod));
    if (result != JNI_OK){
        return result;
    }
    env->DeleteLocalRef(clazz);
    return JNI_VERSION_1_6;
}