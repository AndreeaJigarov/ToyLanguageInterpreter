package Model.ProgrState.Helper.Lock;

import Exceptions.MyException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;


public class LockTable implements ILockTable {
    private final Map<Integer, Integer> table = new HashMap<>();
    private int freeLocation = 0;
    private final ReentrantLock lock = new ReentrantLock(); // to assure the lock mechanism


    @Override
    public int allocate(int value) {
        lock.lock();
        try {
            freeLocation++;
            table.put(freeLocation, value);
            return freeLocation;
        } finally {
            lock.unlock();
        }
        // to put new lock identifiers in the LockTable
    }

    @Override
    public void update(int index, int value) throws MyException {
        lock.lock();
        try {
            if (!table.containsKey(index)) throw new MyException("Lock index not found");
            table.put(index, value);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int get(int index) throws MyException {
        lock.lock();
        try {
            if (!table.containsKey(index)) throw new MyException("Lock index not found");
            return table.get(index); // get the lock identifier at the specific index
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
    public Map<Integer, Integer> getContent() {
        lock.lock();
        try { return new HashMap<>(table); }
        finally { lock.unlock(); }
    }

    @Override
    public ReentrantLock getLock() {
        return this.lock;
    }

    @Override
    public String toString() {
        lock.lock();
        try { return table.toString(); }
        finally { lock.unlock(); }
    }
}