package com.shufudesing.drmb.Offline;

/**
 * Created by Sam on 6/6/2014.
 */
public class SavedCall {
    private String methodName;
    private Object[] args;

    public SavedCall(String methodName, Object[] args){
        this.methodName = methodName;
        this.args = args;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }
}
