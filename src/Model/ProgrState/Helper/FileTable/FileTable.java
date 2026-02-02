package Model.ProgrState.Helper.FileTable;
import Model.ProgrState.Helper.Dictionary.MyIDictionary;

import java.util.List;
import java.util.Map;

public interface FileTable<T, E> {
    public void put(T key, E value);
    public E get(T key);
    public E lookup(T key);
    public void remove(T key);
    public void update(T key, E value);
    public boolean containsKey(T key);
    List<T> getKeys();
    List<E> getValues();
    public FileTable<T,E> deepCopy();
    public String toString();

}