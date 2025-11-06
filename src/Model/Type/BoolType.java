package Model.Type;

import Model.Value.BoolValue;
import Model.Value.IValue;

public class BoolType implements IType {
    @Override
    public String toString() {
        return "Bool";
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof BoolType;
    }

    @Override
    public IValue defaultValue() {
        return new BoolValue(false);
    }
}
