package Model.ProgrState.Helper.Semaphore;

import Exceptions.MyException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class SemaphoreTable implements ISemaphoreTable {
    private final Map<Integer, SemaphoreEntry> table = new HashMap<>();
    private int freeLocation = 0;
    private final ReentrantLock lock = new ReentrantLock();

    @Override
    public int allocate(SemaphoreEntry entry) {
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
    public SemaphoreEntry get(int index) throws MyException {
        lock.lock();
        try {
            if (!table.containsKey(index)) throw new MyException("Semaphore index not found");
            return table.get(index);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void update(int index, SemaphoreEntry entry) throws MyException {
        lock.lock();
        try {
            if (!table.containsKey(index)) throw new MyException("Semaphore index not found");
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
    public Map<Integer, SemaphoreEntry> getContent() {
        lock.lock();
        try { return new HashMap<>(table); }
        finally { lock.unlock(); }
    }

    @Override
    public ReentrantLock getLock() {
        return this.lock; // This allows statements to synchronize on the same lock
    }

    @Override
    public String toString() {
        return "SemaphoreTable [freeLocation=" + freeLocation + ", table=" + table.toString() + "]";
    }
}