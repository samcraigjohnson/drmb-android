package com.shufudesing.drmb.Offline;

import android.content.Context;
import android.provider.MediaStore;
import android.util.JsonWriter;
import android.util.Log;

import com.shufudesing.drmb.Collections.Budget;
import com.shufudesing.drmb.Collections.Expense;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.Map;
import java.util.Stack;

/**
 * Created by Sam on 6/6/2014.
 */
public class OfflineStack {

    private Budget budget;
    private Map<String, Expense> mExpenses;
    private Stack<SavedCall> callStack;
    private Context c;
    private final String OUTPUT_FILE = "offline_data.json";
    private final String TAG = "OfflineStack";

    public OfflineStack(Context context){
        c = context;
        callStack = new Stack<SavedCall>();
    }

    public void setBudget(Budget b){
        budget = b;
    }

    public Budget getBudget(){
        return budget;
    }

    public void setExpenses(Map<String, Expense> expenses){
        mExpenses = expenses;
    }

    public Map<String, Expense> getExpenses(){
        return mExpenses;
    }

    public void saveCall(String methodName, Object[] methodArgs){
        callStack.push(new SavedCall(methodName, methodArgs));
    }

    public Stack<SavedCall> getCallStack(){
        return callStack;
    }

    public void resetStack(){
        callStack = new Stack<SavedCall>();
    }

    public void writeData(){
        FileOutputStream fos;
        try {
            JSONObject jo = new JSONObject();
            if(callStack.size() > 0){
                jo = writeStack(jo);
            }
           //writeExpenses(writer);
           //writeBudget(writer);
            fos = c.openFileOutput(OUTPUT_FILE, Context.MODE_PRIVATE);
            fos.write(jo.toString().getBytes());
            fos.close();
        }
        catch (Exception e){
            Log.e(TAG, "File Writing Error: " + e.getMessage());
        }
    }

    public void readData(){
        FileInputStream fis;
        try{
            fis = c.openFileInput(OUTPUT_FILE);
            StringWriter writer = new StringWriter();
            IOUtils.copy(fis, writer, "UTF-8");
            fis.close();
            JSONObject jo = new JSONObject(writer.toString());
            readStack(jo);
        }
        catch (Exception e){
            Log.e(TAG, "File Read Exception: " + e.getMessage());
        }
    }

    private void readStack(JSONObject jo) throws JSONException {
        JSONArray jCallStack = jo.getJSONArray("callStack");
        Stack<SavedCall> newStack = new Stack<SavedCall>();
        for(int i=0; i < jCallStack.length(); i++){
            JSONObject jCall = jCallStack.getJSONObject(i);
            String methodName = (String) jCall.get("methodName");
            JSONArray args = jCall.getJSONArray("methodArgs");
            Object[] methodArgs = new Object[args.length()];
            for(int j=0; j < args.length(); j++){
                methodArgs[j] = args.get(j);
            }
            SavedCall call = new SavedCall(methodName, methodArgs);
            newStack.push(call);
        }

        callStack = newStack;
        Log.v(TAG, "STACK: "+ callStack.peek().getMethodName() + ": " + callStack.peek().getArgs().toString());
    }

    private JSONObject writeStack(JSONObject jo) throws JSONException {
        JSONArray stackArray = new JSONArray();
        while(!callStack.isEmpty()){
            JSONObject stackObj = new JSONObject();
            SavedCall sc = callStack.pop();
            stackObj.put("methodName", sc.getMethodName());
            JSONArray methodArgs = new JSONArray();
            for(Object o: sc.getArgs()){
                methodArgs.put(o.toString());
            }
            stackObj.put("methodArgs", methodArgs);
            stackArray.put(stackObj);
        }

        jo.put("callStack", stackArray);
        Log.v(TAG, "Writing callstack: " + jo.toString());
        return jo;
    }

    private void writeExpenses(JsonWriter w) throws IOException{

    }

    private void writeBudget(JsonWriter w) throws IOException{

    }
}
