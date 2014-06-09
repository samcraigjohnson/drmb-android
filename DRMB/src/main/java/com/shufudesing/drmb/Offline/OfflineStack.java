package com.shufudesing.drmb.Offline;

import android.content.Context;

//import android.util.JsonReader;
import android.util.Log;


import com.cedarsoftware.util.io.JsonObject;
import com.cedarsoftware.util.io.JsonWriter;
import com.cedarsoftware.util.io.JsonReader;
import com.google.gson.Gson;
import com.shufudesing.drmb.Collections.Budget;
import com.shufudesing.drmb.Collections.Expense;
import com.shufudesing.drmb.Collections.SavingsGoal;
import com.shufudesing.drmb.DrUTILS;


import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.FileInputStream;

import java.io.FileOutputStream;
import java.io.IOException;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Created by Sam on 6/6/2014.
 */
public class OfflineStack {

    private Budget budget;
    private Map<String, Expense> mExpenses;
    private SavingsGoal currGoal;
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

    public void setSavingsGoal(SavingsGoal sg){
        currGoal = sg;
    }

    public SavingsGoal getSavingsGoal(){
        return currGoal;
    }

    public void saveCall(String methodName, Object[] methodArgs){
        callStack.push(new SavedCall(methodName, methodArgs));
    }

    public Stack<SavedCall> getCallStack(){
        return callStack;
    }

    public void resetStack(){
        callStack = new Stack<SavedCall>();
        Log.v(TAG, "Stack Reset");
    }

    public void writeData(){
        FileOutputStream fos;
        try {
            JsonObject jo2 = new JsonObject();
            if(callStack.size() > 0)
                writeStack(jo2);
            if(budget.getFields().size() > 0)
                writeBudget(jo2);
            if(mExpenses.size() > 0){}
                writeExpenses(jo2);

            fos = c.openFileOutput(OUTPUT_FILE, Context.MODE_PRIVATE);
            fos.write(jo2.toString().getBytes());
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
            Gson gson = new Gson();
            Map<String, Object> jo = gson.fromJson(writer.toString(), HashMap.class);
            Log.v(TAG, "jo created" + jo.toString());
            if(jo.containsKey(DrUTILS.JSON_CALL_STACK)){}
               // readStack(jo.get(DrUTILS.JSON_CALL_STACK));
            if(jo.containsKey(DrUTILS.JSON_BUDGET)) {}
               // readBudget(jo.get(DrUTILS.JSON_BUDGET));
            if(jo.containsKey(DrUTILS.JSON_EXPENSES))
                readExpense((Map<String,Object>) jo.get(DrUTILS.JSON_EXPENSES));

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void readStack(JSONObject jo) throws JSONException {
        JSONArray jCallStack = jo.getJSONArray(DrUTILS.JSON_CALL_STACK);
        Stack<SavedCall> newStack = new Stack<SavedCall>();
        for(int i=0; i < jCallStack.length(); i++){
            JSONObject jCall = jCallStack.getJSONObject(i);
            String methodName = (String) jCall.get(DrUTILS.JSON_METHOD_NAME);
            JSONArray args = jCall.getJSONArray(DrUTILS.JSON_METHOD_ARGS);
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

    private void writeStack(JsonObject jo) throws JSONException {
        JSONArray stackArray = new JSONArray();
        while(!callStack.isEmpty()){
            JSONObject stackObj = new JSONObject();
            SavedCall sc = callStack.pop();
            stackObj.put(DrUTILS.JSON_METHOD_NAME, sc.getMethodName());
            JSONArray methodArgs = new JSONArray();
            for(Object o: sc.getArgs()){
                methodArgs.put(o.toString());
            }
            stackObj.put(DrUTILS.JSON_METHOD_ARGS, methodArgs);
            stackArray.put(stackObj);
        }

        jo.put(DrUTILS.JSON_CALL_STACK, stackArray);
        Log.v(TAG, "Writing callstack: " + jo.toString());
    }

    private void writeExpenses(JsonObject jo) throws JSONException, IOException{
        Gson gson = new Gson();
        String json = gson.toJson(mExpenses);
        //JsonArr
        jo.put(DrUTILS.JSON_EXPENSES, json);
    }

    private void readExpense(Map<String, Object> jo) throws JSONException, IOException{
        Map<String, Expense> newExpenses = new HashMap<String, Expense>();
        for(String docid: jo.keySet()){
            Expense e = new Expense(docid, (Map<String,Object>)((Map<String, Object>) jo.get(docid)).get("mFields"));
            newExpenses.put(docid, e);
        }

        mExpenses = newExpenses;
    }

    private void writeBudget(JsonObject jo) throws JSONException, IOException{
        jo.put(DrUTILS.JSON_BUDGET, budget.getJsonRepresentation());
        Log.v(TAG, "Budget Write: " + jo.get(DrUTILS.JSON_BUDGET).toString());
    }

    private void readBudget(JSONObject jo) throws JSONException, IOException{
        Log.v(TAG, "JO: " + jo.toString());
        //budget.setFromJson(jo.get(DrUTILS.JSON_BUDGET).toString());
        Log.v(TAG, "Budget Read: " + budget.toString());
    }
}
