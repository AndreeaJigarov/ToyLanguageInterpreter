package Model.Type;

import Model.Value.IValue;

public interface IType {
    public boolean equals(Object obj);
    String toString();
    IValue defaultValue();
}
