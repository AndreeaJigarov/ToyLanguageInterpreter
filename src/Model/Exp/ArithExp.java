package Model.Exp;

import Exceptions.MyException;
import Model.ProgrState.Helper.Dictionary.MyIDictionary;
import Model.ProgrState.Helper.Heap.IHeap;
import Model.Type.IntType;
import Model.Value.IValue;
import Model.Value.IntValue;

public class ArithExp implements IExp{
    IExp e1;
    IExp e2;
    int op; ///1-plus, 2-minus, 3-star, 4-divide

    public ArithExp(char opString,IExp e1, IExp e2 ) {
        this.e1 = e1;
        this.e2 = e2;
        if(opString=='+'){
            this.op=1;
        }
        else if(opString=='-'){
            this.op=2;
        }
        else if(opString=='*'){
            this.op=3;
        }
        else if(opString=='/'){
            this.op=4;
        }
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> tbl, IHeap<Integer, IValue> heap) throws MyException {
        IValue v1,v2;
        v1 =e1.eval(tbl,heap);
        if(v1.getType().equals(new IntType())){
            v2 =e2.eval(tbl,heap);
            if(v2.getType().equals(new IntType())){
                IntValue i1 = (IntValue) v1;
                IntValue i2 = (IntValue) v2;

                int n1, n2;
                n1=i1.getValue();
                n2=i2.getValue();
                if(op==1){
                    return new IntValue(n1+n2);
                }
                else if(op==2){
                    return new IntValue(n1-n2);
                }
                else if(op==3){
                    return new IntValue(n1*n2);
                }
                else if(op==4){
                    if(n2==0)throw new MyException("division by zero");
                    else return new IntValue(n1/n2);
                }

            }else throw new RuntimeException("second operand is not an integer");
        }else throw new RuntimeException("first operand is not an integer");

        return null; //STH IS NOT RIGHT HERE
    }

    public String getOpString(){
        return switch(op){
            case 1 -> "+";
            case 2 -> "-";
            case 3 -> "*";
            case 4 -> "/";
            default -> "";
        };
    }

    public IExp getExp1(){
        return e1;
    }
    public IExp getExp2(){
        return e2;
    }
    public int getOp(){
        return op;
    }

    @Override
    public String toString(){
        return e1.toString()+" "+getOpString()+" "+e2.toString();
    }

}
