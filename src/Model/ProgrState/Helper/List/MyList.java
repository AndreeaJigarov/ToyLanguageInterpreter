package Model.ProgrState.Helper.List;

import Model.ProgrState.Helper.List.MyIList;
import java.util.*;

public class MyList<T> implements MyIList<T> {
    //int size = 0;
    private final List<T> list;

    public MyList(){
        list = new ArrayList<>();
    }

    @Override
    public void add(T element) {
        list.add(element);
    }

    @Override
    public void remove(T element) {
        list.remove(element);
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public void clear(){
        list.clear();
    }

    @Override
    public int size(){
        return list.size();
    }

    @Override
    public String toString(){
        return list.toString();
    }

    @Override
    public Iterator<T> iterator(){
        return list.iterator();
    }

}
