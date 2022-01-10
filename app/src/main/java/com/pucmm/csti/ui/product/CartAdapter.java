package com.pucmm.csti.ui.product;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.pucmm.csti.R;
import com.pucmm.csti.activity.LoginActivity;
import com.pucmm.csti.activity.MainActivity;
import com.pucmm.csti.activity.RegisterActivity;
import com.pucmm.csti.model.Product;
import com.pucmm.csti.utils.CommonUtil;
import com.pucmm.csti.utils.UserSession;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CartAdapter extends ArrayAdapter<JSONObject> {

    //to get user session data
    private UserSession session;

    private Context context;

    public CartAdapter(Context context, ArrayList<JSONObject> cartArrayList) {
        super(context, R.layout.cart_item, cartArrayList);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        retrieveSession();
        JSONObject cartItem = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.cart_item, parent, false);
        }

        ImageView cartImage = convertView.findViewById(R.id.cartItemImage);
        TextView cartName = convertView.findViewById(R.id.cartItemName);
        TextView cartItemQuantity = convertView.findViewById(R.id.cartItemQuantity);
        Button minusButton = convertView.findViewById(R.id.minusButton);
        Button plusButton = convertView.findViewById(R.id.plusButton);


        minusButton.setOnClickListener(view -> {
            try {
                updateQuantity(position, -1, cartItemQuantity);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

        plusButton.setOnClickListener(view -> {
            try {
                updateQuantity(position, 1, cartItemQuantity);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

        System.out.println("getCartQuantities");
        try {
            cartName.setText(cartItem.getJSONObject("product").getString("itemName"));
            cartItemQuantity.setText(session.getCartQuantities().get(position));
            System.out.println("cartItem.getJSONObject(\"carousels\")");
            System.out.println(cartItem.getJSONArray("carousels").getJSONObject(0).getString("photo"));
            CommonUtil.downloadImage(cartItem.getJSONArray("carousels").getJSONObject(0).getString("photo"), cartImage);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("getCartQuantities");

//        cartName.setText(product.getItemName());

        return convertView;
    }

    private void updateQuantity(int position, int amount, TextView cartItemQuantity) throws JSONException {
        int originalQuantity = Integer.parseInt(cartItemQuantity.getText().toString());
        int newQuantity = originalQuantity + amount;
        if(newQuantity >= 1) {
            cartItemQuantity.setText(String.valueOf(newQuantity));

            ArrayList<String> newSessionQuantities = session.getCartQuantities();
            newSessionQuantities.set(position, String.valueOf(newQuantity));
            session.replaceCartQuantities(newSessionQuantities);

            retrieveSession();
        }

    }

    private void retrieveSession() {
        //create new session object by passing application context
        session = new UserSession(context);
        //get User details if logged in
    }
}
