// IUpdateListener.aidl
package com.example.ipc.aidl;

import com.example.ipc.aidl.Book;
// Declare any non-default types here with import statements

interface IUpdateListener {
    void onUpdate(in Book book);
}
