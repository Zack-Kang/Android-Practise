// IBookManager.aidl
package com.example.ipc.aidl;
import com.example.ipc.aidl.Book;
import com.example.ipc.aidl.IUpdateListener;
// Declare any non-default types here with import statements

interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);

    void register(in IUpdateListener listener);

    void unregister(in IUpdateListener listener);
}
