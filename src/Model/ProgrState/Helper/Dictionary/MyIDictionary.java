package Model.ProgrState.Helper.Dictionary;

import Model.Value.IValue;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface MyIDictionary<T,E>{
    public void put(T key, E value);
    public E get(T key);
    public E lookup(T key);
    public void remove(T key);
    public void update(T key, E value);
    public boolean containsKey(T key);
    List<T>
    getKeys();
    List<E> getValues();
    public MyIDictionary<T,E> deepCopy();
    public String toString();
    public MyIDictionary<T,E> clone();

    public Set<Map.Entry<T, E>> entrySet();
}
