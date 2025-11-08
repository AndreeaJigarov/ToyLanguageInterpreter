package Model.Type;

import Model.Value.IValue;
import Model.Value.StringValue;

public class StringType implements IType {
    @Override public IValue defaultValue() { return new StringValue(""); }
    @Override public boolean equals(Object o) { return o instanceof StringType; }
    @Override public String toString() { return "string"; }
}