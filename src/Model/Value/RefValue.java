package Model.Value;

import Model.Type.IType;
import Model.Type.RefType;

public class RefValue implements IValue {
    private final int address;
    private final IType locationType;

    public RefValue(int address, IType locationType) {
        this.address = address;
        this.locationType = locationType;
    }

    public int getAddress() {
        return address;
    }

    public IType getLocationType() {
        return locationType;
    }

    @Override
    public IType getType() {
        return new RefType(locationType);
    }

    @Override
    public String toString() {
        return "(" + address + ", " + locationType.toString() + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof RefValue))
            return false;
        RefValue other = (RefValue) obj;
        return address == other.address && locationType.equals(other.locationType);
    }
}