package Model.ProgrState.Helper.Heap;

import Model.Value.IValue;
import Exceptions.MyException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

public class MyHeap implements IHeap<Integer, IValue> {
    private Map<Integer, IValue> heap;
    private int nextFreeAddress = 1;

    public MyHeap() {
        heap = new HashMap<>();
    }

    @Override
    public int allocate(IValue value) {
        heap.put(nextFreeAddress, value);
        return nextFreeAddress++;
    }

    @Override
    public IValue get(Integer key) throws MyException {
        if (!heap.containsKey(key))
            throw new MyException("Address " + key + " not found in heap!");
        return heap.get(key);
    }

    // maybe add   a lookup here for similarity to my dictionary

    @Override
    public void put(Integer key, IValue value) {
        heap.put(key, value);
    }

    @Override
    public void update(Integer key, IValue value) throws MyException {
        if (!heap.containsKey(key))
            throw new MyException("Address " + key + " not in heap!");
        heap.put(key, value);
    }

    @Override
    public boolean containsKey(Integer key) {
        return heap.containsKey(key);
    }

    @Override
    public void setContent(Map<Integer, IValue> map) {
        this.heap = map;
    }

    @Override
    public Map<Integer, IValue> getContent() {
        return heap;
    }

    @Override
    public List<Integer> getKeys() {
        return new ArrayList<>(heap.keySet());
    }

    @Override
    public void deallocate(int address) {
        heap.remove(address);
    }

    @Override
    public String toString() {
        return heap.toString();
    }
}