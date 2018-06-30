package com.example.abel.medib2;

import android.content.Context;
import android.support.test.filters.SmallTest;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.Observable;

import static org.junit.Assert.*;

/**
 * Created by Abel on 6/24/2018.
 */

@SmallTest
public class MedibRequestTest {



    class MedibRequest<T> extends Observable {
        protected Context context;
        public JSONObject response = null;
        private int status;
        protected boolean executeCalled = authNedded();
        protected int requestMethod;
        //different constructors to inforce paths
        public MedibRequest (){

            }
        public MedibRequest(boolean executeCalled ){
                this.executeCalled = executeCalled;

            }

        public MedibRequest(JSONObject response , boolean executeCalled ) {

                super();
                this.executeCalled = executeCalled;

                this.response = response;
                this.status = 0;

            }
        public void  setResponse(String jsonString){
                try {
                    response = new JSONObject(jsonString);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        public String getUrl() {
                  return null;
              }

        public boolean authNedded() {
                  return false;
              }

        public JSONObject getErrors(){
                if(executeCalled){
                    if(response != null){
                        try{
                            return response.getJSONObject("err");
                        }
                        catch(JSONException e){
                            return null;
                        }
                    }
                    else
                        return null;
                }
                else {
                    throw (new RuntimeException("You need to call execute first"));

                }
            }

        public String getString(String key ) {
                String object = null;
                try {
                    object = response.getString(key);
                } catch (JSONException except) {
                    except.printStackTrace();
                }
                return object;
            }
        public boolean success(String responseString){                //success changed to take string input (orginally string computed by calling getString method (other unit) )
            boolean success = false;
            success = Boolean.parseBoolean(responseString);
            return success;
        }
        public int status(){
            if(executeCalled){
                return status;
            }
            else
                throw (new RuntimeException("You need to call execute first"));
        }
        public Object getDoc(){
            if(executeCalled){
                if(response != null){
                    try{
                        return response.get("doc");
                    }
                    catch(JSONException e){
                        return null;
                    }
                }
                else
                    return null;
            }
            else {
                throw (new RuntimeException("You need to call execute first"));
            }
        }


    }

@Test
//test for get errors starts here
//test 1 executeCalled == true and response !=null
    public void test1(){
        String jsonString = "{'err':{'type':someError}}";
    JSONObject response = null;
    try {
        response = new JSONObject(jsonString);
    } catch (JSONException e) {
        e.printStackTrace();
    }
    MedibRequest mr = new MedibRequest(response , true);
    JSONObject returned = mr.getErrors();
    assertEquals(true , returned !=null);


}
@Test (expected =  RuntimeException.class)
//test 2 executeCalled == false (check if function throws execption)
public void test2 (){
    String jsonString = "{'err':{'type':someError}}";
    JSONObject response = null;
    try {
        response = new JSONObject(jsonString);
    } catch (JSONException e) {
        e.printStackTrace();
    }

    MedibRequest mr2 = new MedibRequest(response , false);
    mr2.getErrors();


}
@Test
//test 3 executeCalled == true , response == null (check if function returns null)
    public void test3(){
    JSONObject response = null;
    MedibRequest mr2 = new MedibRequest(response , true);
    JSONObject returned = mr2.getErrors();
    assertEquals(true ,returned == null);


}

//test for getErrors ends here

 //test for getString starts here

 @Test
 //give a valid json object as response and test if function returns String corresponding to the key
    public void test4(){
     String jsonString = "{'key1':value1}"; // mocking response
     JSONObject response = null;
     try {
         response = new JSONObject(jsonString);
     } catch (JSONException e) {
         e.printStackTrace();
     }

     MedibRequest mr5 = new MedibRequest(response , true);
     assertEquals(true , mr5.getString("key1").getClass() == String.class);
     assertEquals(true ,mr5.getString("key1").equals("value1") );

 }
//test for getString ends here

 //test for boolean success() starts here
 @Test
 //test if success returns a boolean
    public void test5(){

     MedibRequest mr6 = new MedibRequest();
     assertEquals(true  , mr6.success("true") == true );
     assertEquals(false  , mr6.success("false") == true );

 }
 //test for boolean success() ends here

    //test for  int status() starts here
    @Test
    //test6 (executeCalled == true) test if int status() returns status(defined in MedbibRequest Class)

    public void test6(){
     String jsonString = "{'key':value}"; // mocking response
     JSONObject response = null;
     try {
         response = new JSONObject(jsonString);
     } catch (JSONException e) {
         e.printStackTrace();
     }
            MedibRequest mr7 = new MedibRequest(response , true);
            assertEquals(true , mr7.status() == mr7.status);
 }
    //test7 (executeCalled == false) test if int status() throws runtime exception
    @Test(expected =  RuntimeException.class)
    public void test7(){
        String jsonString = "{'key':value}"; // mocking response
        JSONObject response = null;
        try {
            response = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MedibRequest mr8 = new MedibRequest(response , false);

        mr8.status();
    }
    //test for  int status() ends here

    //test for T getDoc() starts here
    //test8 (executeCalled == false)test if getDoc() throws runtime exception
    @Test(expected = RuntimeException.class)
    public void test8(){
        MedibRequest mr9 = new MedibRequest(false);
        mr9.getDoc();

    }
    @Test
    //test9 (executeCalled == true , response = null)test if getDoc() returns null
    public void test9(){
        JSONObject response = null;
        MedibRequest mr10 = new MedibRequest(response , true);
        assertEquals(mr10.getDoc() , null);

    }
    @Test
    //test10 (executeCalled == true , response != null)test if getDoc() return is different from null
    public void test10(){
        String jsonString = "{'doc':{'key':value}}";
        JSONObject response = null;
        try {
            response = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        MedibRequest mr11 = new MedibRequest(response , true);
        assertEquals( true, mr11.getDoc() != null);

    }
}
//test for T getDoc() ends here



