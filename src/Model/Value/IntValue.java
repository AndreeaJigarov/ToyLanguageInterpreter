package Model.Value;

import Model.Type.IType;
import Model.Type.IntType;

public class IntValue implements IValue {
    private int value;
    public IntValue(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
    public void setValue(int value) {
        this.value = value;
    }
    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public IType getType(){
        return new IntType();
    }
}
