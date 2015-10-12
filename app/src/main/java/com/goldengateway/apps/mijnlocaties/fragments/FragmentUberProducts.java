package com.goldengateway.apps.mijnlocaties.fragments;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.goldengateway.apps.mijnlocaties.R;
import com.goldengateway.apps.mijnlocaties.activities.MainActivity;
import com.goldengateway.apps.mijnlocaties.api.ApiInterface;
import com.goldengateway.apps.mijnlocaties.model.Locatie;
import com.goldengateway.apps.mijnlocaties.model.Product;
import com.goldengateway.apps.mijnlocaties.model.Products;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentUberProducts extends Fragment {

    String BASEURLUBER = "https://api.uber.com";
    private Gson gson;

    public String strId;

    public View vw;

    ArrayList<Product> producten;
    ListView productList;
    ProductItemAdapter adapter;

    List<Product> plist;



    public FragmentUberProducts() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater
                                     inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_uber_products, container, false);
        Button button = (Button) v.findViewById(R.id.btnBackToMain);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MainActivity currentActivity = (MainActivity) getActivity();
                currentActivity.showFragmentMain();
            }
        });

        return v;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        vw = view;

        // In mainfragment one id is checked.
        MainActivity currentActivity = (MainActivity) getActivity();

        // fetch location
        Locatie loc = currentActivity.mDbHelper.getLocatieById(strId);

        // fetch and show available products
        getUberProducts(loc.getBgraad(), loc.getLgraad());

    }

    //----------------------------------------------------------------------------------------------
    public void getUberProducts( double mLatitude, double mLongitude) {

        GsonBuilder gsonBuilder = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
                    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                        return new Date(json.getAsJsonPrimitive().getAsLong());
                    }
                });
        gson = gsonBuilder.create();

//Retrofit section start from here...
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(BASEURLUBER)
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestInterceptor.RequestFacade requestFacade) {
                        requestFacade.addHeader("Authorization", "Token " + "put servertoken here");
                    }
                })
                .setConverter(new GsonConverter(gson))
                .build();


        ApiInterface apiInterface = restAdapter.create(ApiInterface.class);                            //creating a service for adapter with our GET class

        //Now ,we need to call for response
        //Retrofit using gson for JSON-POJO conversion

        apiInterface.getProducts(mLatitude, mLongitude, new Callback<Products>() {
            @Override
            public void success(Products products, Response response) {
                //we get json object from server into our POJO or model class
                plist = products.getProducts();
                // products is een List van type Product
                if (plist.size() > 0) {
                    initData(vw);
                } else {
                    Toast.makeText(getActivity(), R.string.request_succes_no_products, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getActivity(), R.string.request_failure, Toast.LENGTH_LONG).show();
            }
        });
    }


    private void initData(View v) {
        producten = new ArrayList<Product>();

        for (Product p : plist) {
            producten.add(p);
        }

        productList = (ListView) v.findViewById(R.id.listViewProducten);
        adapter = new ProductItemAdapter(getActivity(), R.layout.product_item, producten);
        productList.setAdapter(adapter);

    }

    public class ProductItemAdapter extends ArrayAdapter<Product> {
        private ArrayList<Product> producten;

        public ProductItemAdapter(Context context, int textViewResourceId,
                                  ArrayList<Product> producten) {
            super(context, textViewResourceId, producten);
            this.producten = producten;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;

            if (v == null) {
                LayoutInflater inflater = getActivity().getLayoutInflater();
                v = inflater.inflate(R.layout.product_item,null);
            }

            Product product = producten.get(position);
            if (product != null) {
                TextView tvProduct_id          = (TextView) v.findViewById(R.id.tvProduct_id);
                TextView tvProduct_description = (TextView) v.findViewById(R.id.tvProduct_description);
                TextView tvProduct_displayName = (TextView) v.findViewById(R.id.tvProduct_displayName);
                TextView tvProduct_capacity    = (TextView) v.findViewById(R.id.tvProduct_capacity);
                if (tvProduct_id != null) {
//                    tvProduct_id.setText(product.getProductId()+"");
                    tvProduct_id.setVisibility(View.GONE);
                }
                if (tvProduct_description != null) {
                    tvProduct_description.setText(product.getDescription());
                }
                if (tvProduct_displayName != null) {
                    tvProduct_displayName.setText(product.getDisplayName());
                }
                if (tvProduct_capacity != null) {
                    tvProduct_capacity.setText(product.getCapacity() + "");
                }

            }

            return v;
        }
    }

}
