package Model.ProgrState.Helper.Stack;
import Model.ProgrState.Helper.Stack.MyIStack;

import java.util.*;

public class MyStack <T> implements MyIStack<T> {
    private Stack<T> stack;
    public MyStack() {
        stack = new Stack<T>();
    }
    public T peek(){
        return stack.peek();
    }
    public boolean isEmpty(){
        return stack.isEmpty();
    }
    public void push(T item){
        stack.push(item);
    }
    public T pop(){
        return stack.pop();
    }
    public String toString(){
        return (stack.stream().toList().reversed().toString());
    }

}
