package Model.ProgrState.Helper.Semaphore;

import Exceptions.MyException;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public interface ISemaphoreTable {
    int allocate(SemaphoreEntry entry);
    SemaphoreEntry get(int index) throws MyException;
    void update(int index, SemaphoreEntry entry) throws MyException;
    boolean contains(int index);
    Map<Integer, SemaphoreEntry> getContent();
    ReentrantLock getLock();
}