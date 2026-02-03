package Model.ProgrState.Helper.Barrier;

import Exceptions.MyException;
import javafx.util.Pair;
import java.util.List;
import java.util.Map;

public interface IBarrierTable {
    int add(Pair<Integer, List<Integer>> value);
    void update(int address, Pair<Integer, List<Integer>> value) throws MyException;
    Pair<Integer, List<Integer>> get(int address) throws MyException;
    boolean contains(int address);
    Map<Integer, Pair<Integer, List<Integer>>> getContent();
    void setContent(Map<Integer, Pair<Integer, List<Integer>>> newContent);
}