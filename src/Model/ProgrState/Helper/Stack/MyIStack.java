package Model.ProgrState.Helper.Stack;

public interface MyIStack<T> {
    public T peek();
    public boolean isEmpty();
    public void push(T item);
    public T pop();
    public String toString();

}
