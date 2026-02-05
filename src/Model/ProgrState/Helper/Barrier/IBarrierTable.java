package Model.ProgrState.Helper.Barrier;

import Exceptions.MyException;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public interface IBarrierTable {
    int allocate(BarrierEntry entry);
    BarrierEntry get(int index) throws MyException;
    void update(int index, BarrierEntry entry) throws MyException;
    boolean contains(int index);
    Map<Integer, BarrierEntry> getContent();
    ReentrantLock getLock();
    String toString();
}