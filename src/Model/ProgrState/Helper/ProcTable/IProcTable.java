package Model.ProgrState.Helper.ProcTable;

import Model.Stmt.IStmt;
import javafx.util.Pair;

import java.util.List;
import java.util.Map;


public interface IProcTable {
    void put(String name, List<String> params, IStmt body);
    boolean isDefined(String name);
    List<String> getParams(String name);
    IStmt getBody(String name);
    Map<String, Pair<List<String>, IStmt>> getContent();
}