package Model.ProgrState.Helper.Barrier;

import Exceptions.MyException;
import javafx.util.Pair;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BarrierTable implements IBarrierTable {
    private Map<Integer, Pair<Integer, List<Integer>>> table;
    private int freeLocation = 0;

    public BarrierTable() {
        this.table = new HashMap<>();
    }

    @Override
    public synchronized int add(Pair<Integer, List<Integer>> value) {
        freeLocation++;
        table.put(freeLocation, value);
        return freeLocation;
    }

    @Override
    public synchronized void update(int address, Pair<Integer, List<Integer>> value) throws MyException {
        if (table.containsKey(address)) {
            table.put(address, value);
        } else {
            throw new MyException("Barrier address " + address + " is not in the table.");
        }
    }

    @Override
    public synchronized Pair<Integer, List<Integer>> get(int address) throws MyException {
        if (table.containsKey(address)) {
            return table.get(address);
        } else {
            throw new MyException("Barrier address " + address + " is not in the table.");
        }
    }

    @Override
    public synchronized boolean contains(int address) {
        return table.containsKey(address);
    }

    @Override
    public synchronized Map<Integer, Pair<Integer, List<Integer>>> getContent() {
        return table;
    }

    @Override
    public synchronized void setContent(Map<Integer, Pair<Integer, List<Integer>>> newContent) {
        this.table = newContent;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("BarrierTable:\n");
        for (int key : table.keySet()) {
            Pair<Integer, List<Integer>> entry = table.get(key);
            sb.append(key).append(" -> (").append(entry.getKey()).append(", ").append(entry.getValue()).append(")\n");
        }
        return sb.toString();
    }
}