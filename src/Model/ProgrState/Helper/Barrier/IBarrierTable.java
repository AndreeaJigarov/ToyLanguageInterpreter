package Model.ProgrState.Helper.Barrier;

import Exceptions.MyException;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public interface IBarrierTable {
    int allocate(BarrierEntry entry); // [cite: 123]
    BarrierEntry get(int index) throws MyException; // [cite: 139]
    void update(int index, BarrierEntry entry) throws MyException; // [cite: 147]
    boolean contains(int index);
    Map<Integer, BarrierEntry> getContent();
    ReentrantLock getLock();
}