package Model.ProgrState.Helper.Dictionary;

import Model.Value.IValue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class MyDictionary<T,E> implements MyIDictionary<T,E>
{
    private final Map<T,E> dictionary;

    public MyDictionary(){
        dictionary = new HashMap<>();
    }

    public void put(T key, E value){
        dictionary.put(key,value);
    }
    public E get(T key){
        return  dictionary.get(key);
    }
    public E lookup(T key){
        return  dictionary.get(key);
    }
    public void remove(T key){
        dictionary.remove(key);
    }
    public void update(T key, E value){
        dictionary.put(key,value);
    }
    public boolean containsKey(T key){
        return dictionary.containsKey(key);
    }

    @Override
    public List<T> getKeys(){
        return new ArrayList<>(dictionary.keySet());
    }
    @Override
    public List<E> getValues(){
        return new ArrayList<>(dictionary.values());
    }

    @Override
    public MyIDictionary<T,E> deepCopy() {
        MyIDictionary<T, E> newDictionary = new MyDictionary<>();
        for (T key : getKeys()) {
            newDictionary.put(key, get(key));
        }
        return newDictionary;
    }
}
