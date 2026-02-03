package Model.ProgrState.Helper.Semaphore;

import java.util.ArrayList;
import java.util.List;

public class SemaphoreEntry {
    private final int capacity;
    private final List<Integer> threadIds;

    public SemaphoreEntry(int capacity) {
        this.capacity = capacity;
        this.threadIds = new ArrayList<>();
    }

    public int getCapacity() { return capacity; }
    public List<Integer> getThreadIds() { return threadIds; }

    @Override
    public String toString() {
        return "(" + capacity + ", " + threadIds.toString() + ")";
    }
}