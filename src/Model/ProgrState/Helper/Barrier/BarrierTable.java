package Model.ProgrState.Helper.Barrier;

import Exceptions.MyException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class BarrierTable implements IBarrierTable {
    private final Map<Integer, BarrierEntry> table = new HashMap<>();
    private int freeLocation = 0;
    private final ReentrantLock lock = new ReentrantLock();

    @Override
    public int allocate(BarrierEntry entry) {
        lock.lock();
        try {
            freeLocation++;
            table.put(freeLocation, entry);
            return freeLocation;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public BarrierEntry get(int index) throws MyException {
        lock.lock();
        try {
            if (!table.containsKey(index))
                throw new MyException("Barrier index " + index + " not found!");
            return table.get(index);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void update(int index, BarrierEntry entry) throws MyException {
        lock.lock();
        try {
            table.put(index, entry);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean contains(int index) {
        lock.lock();
        try { return table.containsKey(index); }
        finally { lock.unlock(); }
    }

    @Override
    public Map<Integer, BarrierEntry> getContent() {
        lock.lock();
        try { return new HashMap<>(table); }
        finally { lock.unlock(); }
    }

    @Override
    public ReentrantLock getLock() {
        return this.lock;
    }

    public String toString() {
        return table.toString();
    }
}

