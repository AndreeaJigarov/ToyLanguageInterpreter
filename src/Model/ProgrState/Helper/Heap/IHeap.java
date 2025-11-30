package Model.ProgrState.Helper.Heap;

import Model.Value.IValue;
import Exceptions.MyException;
import java.util.Map;
import java.util.List;

public interface IHeap<K, V> {
    int allocate(V value);
    V get(K key) throws MyException;
    void put(K key, V value);
    void update(K key, V value) throws MyException;
    boolean containsKey(K key);
    void setContent(Map<K, V> map);
    Map<K, V> getContent();
    List<K> getKeys();
    void deallocate(int address);
}