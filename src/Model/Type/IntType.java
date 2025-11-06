package Model.Type;

import Model.Value.IValue;
import Model.Value.IntValue;

public class IntType implements IType {
    @Override
    public String toString() {return "int";}

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof IntType) {
            return true;
        }
        else return false;
    }

    @Override
    public IValue defaultValue() {
        return new IntValue(0);
    }
}
