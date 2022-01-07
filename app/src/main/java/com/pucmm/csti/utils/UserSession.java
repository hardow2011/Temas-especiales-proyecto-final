package com.pucmm.csti.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.pucmm.csti.activity.LoginActivity;
import com.pucmm.csti.databinding.BadgeLayoutBinding;
import com.pucmm.csti.model.Userr;
import com.pucmm.csti.model.relationships.ProductWithCarousel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class UserSession extends Fragment {

    // Shared Preferences
    private SharedPreferences sharedPreferences;
    // Editor for Shared preferences
    private SharedPreferences.Editor editor;
    // Context
    private Context context;
    // Shared pref mode
    int PRIVATE_MODE = 0;
    // SharedPreferences file name
    private static final String PREF_NAME = "userSessionPref";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "isLoggedIn";

    // User  (make variable public to access from outside)
    private static final String USER = "user";

    public static final String CARTS = "carts";
    public static final String KEY_QTY = "qty";
    public static final String CART_SIZE = "cart_size";

    private BadgeLayoutBinding binding;

    public UserSession(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    /**
     * Create login session
     */
    public void createLoginSession(final Userr user) {
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(USER, new Gson().toJson(user));
//        editor.putString(CARTS, "[]");
//        editor.putString(KEY_QTY, "[]");
        // commit changes
        editor.commit();
    }

    public void updateLoggedUser(final Userr user) {
        editor.putString(USER, new Gson().toJson(user));
        editor.commit();
    }

    /**
     * Get stored session data
     */
    public Userr getUserSession() {
        final String json = sharedPreferences.getString(USER, "{}");

        return new Gson().fromJson(json, Userr.class);
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     */
    public void checkLogin() {
        // Check login status
        if (!this.isLoggedIn()) {
            // user is not logged in redirect him to Login Activity
            Intent intent = new Intent(context, LoginActivity.class);
            // Closing all the Activities
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            context.startActivity(intent);
        }
    }

    /**
     * Quick check for logout
     **/
    public void logout() {
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, false);

        editor.putString(USER, null);
        // commit changes
        editor.commit();
    }

    /**
     * Quick check for login
     **/
    // Get Login State
    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(IS_LOGIN, false);
    }

    public void logoutUser() {
        // Clearing all data from Shared Preferences
        editor.putBoolean(IS_LOGIN, false);
        editor.clear();
        editor.commit();
    }

    public JsonArray getCart() {
        return sortedCart(sharedPreferences.getString(CARTS, "[]"));
    }

    public ArrayList<JSONObject> getSimpleCart() throws JSONException {

        JSONArray jsonArray = new JSONArray(sharedPreferences.getString(CARTS, "[]"));

        ArrayList<JSONObject> listdata = new ArrayList<JSONObject>();

        //Checking whether the JSON array has some value or not
        if (jsonArray != null) {

            //Iterating JSON array
            for (int i=0;i<jsonArray.length();i++){

                //Adding each element of JSON array into ArrayList
                JSONObject jsonObject = new JSONObject(jsonArray.get(i).toString());
                listdata.add(jsonObject);
            }
        }

        return listdata;
    }

    public ArrayList<String> getCartQuantities() throws JSONException {
        JSONArray jsonArray = new JSONArray(sharedPreferences.getString(KEY_QTY, "[]"));

        ArrayList<String> listdata = new ArrayList<String>();

        //Checking whether the JSON array has some value or not
        if (jsonArray != null) {

            //Iterating JSON array
            for (int i=0;i<jsonArray.length();i++){

                //Adding each element of JSON array into ArrayList
                listdata.add(jsonArray.get(i).toString());
            }
        }

        return listdata;
    }

    public void replaceCartQuantities(ArrayList<String> newQuantities) throws JSONException {

        JSONArray qtyJsonArray = new JSONArray();


        for (String newQuantity : newQuantities)
        {
            qtyJsonArray.put(new Gson().toJson(Integer.parseInt(newQuantity)));
        }

        editor.putString(KEY_QTY, qtyJsonArray.toString());
        editor.commit();
        System.out.println("COMMITED");
        System.out.println(qtyJsonArray.toString());
    }

    public int getCartLength() {
        return sharedPreferences.getString(CARTS,"[]").length();
    }

    /*
    *         // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(USER, new Gson().toJson(user));
        // commit changes
        editor.commit();
    * */
    public void addToCart(final ProductWithCarousel product, final int qty) throws JSONException {

        String cartsStrJson = sharedPreferences.getString(CARTS,"[]");//second parameter is necessary ie.,Value to return if this preference does not exist.
        String qtyStrJson = sharedPreferences.getString(KEY_QTY,"[]");//second parameter is necessary ie.,Value to return if this preference does not exist.

        JSONArray cartsJsonArray = new JSONArray(cartsStrJson);
        cartsJsonArray.put(new Gson().toJson(product));

        JSONArray qtyJsonArray = new JSONArray(qtyStrJson);
        qtyJsonArray.put(new Gson().toJson(qty));


        editor.putString(CARTS, cartsJsonArray.toString());
        editor.putString(KEY_QTY, qtyJsonArray.toString());
        editor.commit();

        cartsStrJson = sharedPreferences.getString(CARTS,"[]");
        qtyStrJson = sharedPreferences.getString(KEY_QTY,"[]");

        System.out.println("CARTS");
        System.out.println(cartsStrJson);
        System.out.println(qtyStrJson);
        System.out.println("CARTS");

    }

    private JsonArray sortedCart(String jsonArrStr) {

        JsonArray jsonArr = new Gson().fromJson(jsonArrStr, JsonArray.class);
        JsonArray sortedJsonArray = new JsonArray();


        List<JsonElement> jsonValues = new ArrayList<JsonElement>();
        for (int i = 0; i < jsonArr.size(); i++) {
            jsonValues.add(jsonArr.get(i));
        }
        Collections.sort(jsonValues, new Comparator<JsonElement>() {
            //You can change "Name" with "ID" if you want to sort by ID
            private static final String KEY_NAME = "itemCode";

            @Override
            public int compare(JsonElement a, JsonElement b) {

                return a.getAsString().compareTo(b.toString());
                //if you want to change the sort order, simply use the following:
                //return -valA.compareTo(valB);
            }
        });

        for (int i = 0; i < jsonArr.size(); i++) {
            sortedJsonArray.add(jsonValues.get(i));
        }

        return sortedJsonArray;

    }
}
