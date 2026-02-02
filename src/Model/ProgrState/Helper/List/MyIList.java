package Model.ProgrState.Helper.List;

import java.util.List;

public interface MyIList<T> extends Iterable<T>{

    public void add(T element);
    public void remove(T element);
    public void clear();
    public int size();
    public boolean isEmpty();

    public String toString();
    public List<T> getArrayList();

}
