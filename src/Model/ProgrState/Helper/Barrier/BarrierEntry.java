package Model.ProgrState.Helper.Barrier;

import java.util.ArrayList;
import java.util.List;

public class BarrierEntry {
    private final int threshold;
    private final List<Integer> threadIds;

    public BarrierEntry(int threshold) {
        this.threshold = threshold;
        this.threadIds = new ArrayList<>(); // [cite: 123]
    }

    public int getThreshold() { return threshold; }
    public List<Integer> getThreadIds() { return threadIds; }

    @Override
    public String toString() {
        return "(" + threshold + ", " + threadIds.toString() + ")"; // [cite: 140]
    }
}