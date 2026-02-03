package Model.ProgrState.Helper.Semaphore;

import Exceptions.MyException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class SemaphoreTable implements ISemaphoreTable {
    private final Map<Integer, SemaphoreTriple> table = new HashMap<>();
    private int freeLocation = 0;
    private final ReentrantLock lock = new ReentrantLock();

    @Override
    public int allocate(SemaphoreTriple entry) {
        lock.lock();
        try {
            freeLocation++;
            table.put(freeLocation, entry);
            return freeLocation;
        } finally { lock.unlock(); }
    }

    @Override
    public SemaphoreTriple get(int index) throws MyException {
        lock.lock();
        try {
            if (!table.containsKey(index)) throw new MyException("Semaphore index not found");
            return table.get(index);
        } finally { lock.unlock(); }
    }

    @Override
    public void update(int index, SemaphoreTriple entry) throws MyException {
        lock.lock();
        try {
            if (!table.containsKey(index)) throw new MyException("Semaphore index not found");
            table.put(index, entry);
        } finally { lock.unlock(); }
    }

    @Override
    public boolean contains(int index) {
        lock.lock();
        try { return table.containsKey(index); }
        finally { lock.unlock(); }
    }

    @Override
    public Map<Integer, SemaphoreTriple> getContent() {
        lock.lock();
        try { return new HashMap<>(table); }
        finally { lock.unlock(); }
    }

    @Override
    public ReentrantLock getLock() { return this.lock; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SemaphoreTable:\n");
        lock.lock();
        try {
            for (Map.Entry<Integer, SemaphoreTriple> entry : table.entrySet()) {
                sb.append(entry.getKey())
                        .append(" -> ")
                        .append(entry.getValue().toString())
                        .append("\n");
            }
        } finally {
            lock.unlock();
        }
        return sb.toString();
    }
}