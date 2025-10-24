package Model.Type;

public class BoolType implements IType {
    @Override
    public String toString() {
        return "Bool";
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof BoolType;
    }

}
