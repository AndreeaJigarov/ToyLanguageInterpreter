package Model.ProgrState.Helper.ProcTable;

import Model.Stmt.IStmt;
import javafx.util.Pair;

import java.util.*;

public class MyProcTable implements IProcTable {
    //maping procedure name to a pair
    private Map<String, Pair<List<String>, IStmt>> table;

    public MyProcTable() {
        this.table = new HashMap<>();
    }

    @Override
    public synchronized void put(String name, List<String> params, IStmt body) {
        table.put(name, new Pair<>(params, body));
    }

    @Override
    public synchronized boolean isDefined(String name) {
        return table.containsKey(name);
    }

    @Override
    public synchronized List<String> getParams(String name) {
        return table.get(name).getKey();
    }

    @Override
    public synchronized IStmt getBody(String name) {
        return table.get(name).getValue();
    }

    @Override
    public synchronized Map<String, Pair<List<String>, IStmt>> getContent() {
        return table;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String name : table.keySet()) {
            Pair<List<String>, IStmt> p = table.get(name);
            sb.append(name).append("(").append(String.join(",", p.getKey())).append(") { ")
                    .append(p.getValue().toString()).append(" }\n");
        }
        return sb.toString();
    }
}