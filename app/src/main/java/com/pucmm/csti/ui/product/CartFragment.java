package com.pucmm.csti.ui.product;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.pucmm.csti.R;
import com.pucmm.csti.activity.CartActivity;
import com.pucmm.csti.activity.MainActivity;
import com.pucmm.csti.utils.UserSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //to get user session data
    private UserSession session;

    public CartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //retrieve session values and display
        retrieveSession();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cart, container, false);

//        String[] cartItems = {"Do someting!", "Do some else", "Saludps"};
//        ArrayList<JSONObject> cartItems = new ArrayList<>();
        ArrayList<JSONObject> cartItems = null;
//        ArrayList<String> cartQuantities = null;
        try {
            cartItems = session.getSimpleCart();
//            cartQuantities = session.getCartQuantities();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Button completePurchase = view.findViewById(R.id.completePurchase);

        completePurchase.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                session.clearCart();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        if(cartItems.size() > 0) {
            completePurchase.setVisibility(View.VISIBLE);
        } else {
            completePurchase.setVisibility(View.INVISIBLE);
        }

        ListView listView = (ListView) view.findViewById(R.id.cartList);

//        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<>(
//                getActivity(),
//                R.layout.cart_item,
//                cartItems
//        );

        CartAdapter cartAdapter = new CartAdapter(getActivity(), cartItems);

        listView.setAdapter(cartAdapter);

        // Inflate the layout for this fragment
        return view;
    }

    private void retrieveSession() {
        //create new session object by passing application context
        session = new UserSession(getActivity().getApplicationContext());
        //get User details if logged in
    }
}