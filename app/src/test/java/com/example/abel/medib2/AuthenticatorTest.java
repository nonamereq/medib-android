package com.example.abel.medib2;

/**
 * Created by Abel on 6/30/2018.
 */
import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.example.abel.lib.Authenticator;
import com.example.abel.lib.DatabaseHelper;

import org.junit.Test;

import static org.junit.Assert.*;

public class AuthenticatorTest {
    public class Authenticator {
        public Authenticator ourInstance = null;
        public boolean constructorCalled = false; //set to true when constructor2 is called
        public boolean ifJupmed  = true;
        public boolean tokenIfJumped = true;
        public boolean isAdminIfJumped = true;

        String token;
        Boolean isAdmin;
        DatabaseHelper helper;
        Context context;

        public Authenticator getInstance(Context context) {
            if (ourInstance == null) {
                ourInstance = new Authenticator(context.getApplicationContext());
                ifJupmed = false;
            }
            return ourInstance;
        }

        public Authenticator(Context context) {
            this.context = context;
            token = null;
            isAdmin = null;
            helper = new DatabaseHelper(context);

        }
        public Authenticator(Context context , boolean constructorCalled) { //consructor2
            this.context = context;
            token = null;
            isAdmin = null;
            helper = new DatabaseHelper(context);
            this.constructorCalled = constructorCalled;
        }
        public String getToken(){          //"sampleReturn" used instead of helper.get("token");
            if(token == null){
                token = "sampleReturn" ;
                tokenIfJumped = false;
            }
            return token;
        }
        public boolean isAdmin( String sampleReturn){   // sampleReturn used instead of helper.get("isAdmin")
            if(isAdmin == null){
                isAdmin = Boolean.parseBoolean(sampleReturn);
                isAdminIfJumped = false;

            }
            return isAdmin;
        }

    }

    @Test
    //getInstance test
    //test1 ourInstance == null (test if Authenticator constructor is called  )
    public void test1(){
        Context  c  = InstrumentationRegistry.getTargetContext();
        Authenticator au = new Authenticator(c , true);
        assertEquals(true , au.constructorCalled);
    }
    //test2 ourInstance !=null
    @Test
    public void test2(){
        Context  c  = InstrumentationRegistry.getTargetContext();
        Authenticator au2 = new Authenticator(c);
        au2.ourInstance = new Authenticator(c);
        assertEquals(true , au2.ifJupmed);

    }

    @Test
    //getToken test
    //test3 (token == null) (test if returned token != null and tokenIfJumped == false (if statement executed) )
    public void test3(){
        Context  c  = InstrumentationRegistry.getTargetContext();
        Authenticator au3 = new Authenticator(c);
        String returned =au3.getToken();
        assertEquals(false ,  returned == null);
        assertEquals(false , au3.tokenIfJumped);

    }
    @Test
    //test4 (token !=null ) (test if tokenIfJumped == true (if statement not executed) )
    public void test4(){
        Context  c  = InstrumentationRegistry.getTargetContext();
        Authenticator au4 = new Authenticator(c);
        au4.token = "tokenNotNull";
        au4.getToken();
        assertEquals(true , au4.tokenIfJumped);

    }
    @Test
    //isAdmin test
    //test 5 (isAdmin == null)
    public void test5(){
        Context  c  = InstrumentationRegistry.getTargetContext();
        Authenticator au5 = new Authenticator(c);
        Authenticator au6 = new Authenticator(c);
        au6.isAdmin("false");
        au5.isAdmin("true");
        assertEquals(false , au5.isAdminIfJumped);
        assertEquals(false , au6.isAdminIfJumped);
    }
    @Test
    //test 6 (isAdmin !=null)
    public void test6(){
        Context  c  = InstrumentationRegistry.getTargetContext();
        Authenticator au7 = new Authenticator(c);
        au7.isAdmin = true;
        Authenticator au8 = new Authenticator(c);
        au8.isAdmin = false;
        au7.isAdmin("false");
        au8.isAdmin("true");
        assertEquals(true , au7.isAdminIfJumped);
        assertEquals(true , au8.isAdminIfJumped);

    }
}