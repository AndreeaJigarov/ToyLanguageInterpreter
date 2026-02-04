package Model.ProgrState.Helper.Lock;

import Exceptions.MyException;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public interface ILockTable {
    int allocate(int value);
    void update(int index, int value) throws MyException;
    int get(int index) throws MyException;
    boolean contains(int index);
    Map<Integer, Integer> getContent();
    ReentrantLock getLock();

    boolean tryLock(int index, int value);
}