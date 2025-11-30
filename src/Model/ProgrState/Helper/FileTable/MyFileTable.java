package Model.ProgrState.Helper.FileTable;
import Model.ProgrState.Helper.Dictionary.MyDictionary;
import Model.ProgrState.Helper.Dictionary.MyIDictionary;

import java.util.*;

public class MyFileTable<T, E> implements FileTable<T, E> {
    private Map<T, E> dictionary = new HashMap<>();


    public MyFileTable(){
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
    public FileTable<T,E> deepCopy() {
        FileTable<T, E> newDictionary = new Model.ProgrState.Helper.FileTable.MyFileTable<>();
        for (T key : getKeys()) {
            newDictionary.put(key, get(key));
        }
        return newDictionary;
    }

    @Override
    public String toString(){
        return dictionary.keySet().toString();
    }


}