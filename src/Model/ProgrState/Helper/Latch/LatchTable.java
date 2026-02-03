package Model.ProgrState.Helper.Latch;

import Exceptions.MyException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class LatchTable implements ILatchTable {
    private final Map<Integer, Integer> table = new HashMap<>();
    private int freeLocation = 0;
    private final ReentrantLock lock = new ReentrantLock();

    @Override
    public int allocate(int value) {
        lock.lock();
        try {
            freeLocation++;
            table.put(freeLocation, value);
            return freeLocation;
        } finally { lock.unlock(); }
    }

    @Override
    public int get(int index) throws MyException {
        lock.lock();
        try {
            if (!table.containsKey(index)) throw new MyException("Latch index not found");
            return table.get(index);
        } finally { lock.unlock(); }
    }

    @Override
    public void update(int index, int value) throws MyException {
        lock.lock();
        try {
            table.put(index, value);
        } finally { lock.unlock(); }
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
    public ReentrantLock getLock() { return this.lock; }

    public String toString() {
        return table.toString();
    }
}