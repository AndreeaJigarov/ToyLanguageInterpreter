package Model.ProgrState.Helper.Semaphore;

import Exceptions.MyException;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public interface ISemaphoreTable {
    int allocate(SemaphoreTriple entry);
    SemaphoreTriple get(int index) throws MyException;
    void update(int index, SemaphoreTriple entry) throws MyException;
    boolean contains(int index);
    Map<Integer, SemaphoreTriple> getContent();
    ReentrantLock getLock();
    String toString();
}