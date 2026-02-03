package Model.ProgrState.Helper.Latch;

import Exceptions.MyException;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public interface ILatchTable {
    int allocate(int value);
    int get(int index) throws MyException;
    void update(int index, int value) throws MyException;
    boolean contains(int index);
    Map<Integer, Integer> getContent();
    ReentrantLock getLock();
    String toString();
}