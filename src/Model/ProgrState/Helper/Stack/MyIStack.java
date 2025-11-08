package Model.ProgrState.Helper.Stack;

import java.util.List;

public interface MyIStack<T> extends Iterable<T> {
    public T peek();
    public boolean isEmpty();
    public void push(T item);
    public T pop();
    public String toString();
    public List<T> toList();
}
