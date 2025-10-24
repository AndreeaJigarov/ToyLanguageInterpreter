package Model.Type;

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
}
