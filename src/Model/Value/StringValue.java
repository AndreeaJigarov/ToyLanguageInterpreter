package Model.Value;

import Model.Type.IType;
import Model.Type.StringType;

public class StringValue implements IValue {
    private final String val;
    public StringValue(String v) { this.val = v; }
    public String getVal() { return val; }

    @Override public IType getType() { return new StringType(); }
    @Override public boolean equals(Object o) {
        return o instanceof StringValue s && val.equals(s.val);
    }
    @Override public String toString() { return "\"" + val + "\""; }
}