package Model.ProgrState.Helper.Semaphore;

import java.util.ArrayList;
import java.util.List;

public class SemaphoreTriple {
    private final int n1;
    private final int n2;
    private final List<Integer> threadIds;

    public SemaphoreTriple(int n1, int n2) {
        this.n1 = n1;
        this.n2 = n2;
        this.threadIds = new ArrayList<>();
    }

    public int getN1() { return n1; }
    public int getN2() { return n2; }
    public List<Integer> getThreadIds() { return threadIds; }

    @Override
    public String toString() {
        return "(" + n1 + ", " + threadIds.toString() + ", " + n2 + ")";
    }

}