package com.example.propertymanagmentsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

public class StripePayment extends AppCompatActivity {

    Button mPay;

    String SECRET_KEY = "sk_test_51O8dOZJ9nvNqi0CJULKvaioqPHvaRuzq998L4DNAnGl34u1nKvD8B10T73IHqmfzeqK6642hoQj4UhhL83Ni1rIm00shZu0wQJ";
    String PUBLISH_KEY = "pk_test_51O8dOZJ9nvNqi0CJ4h15jmQTb025YlYxiDgR0ukeb17nFYGHQchDbWJcEIdHhi30yEdyT7DHDM3Pjtv9iPz1zT43000968k1eG";
    PaymentSheet paymentSheet;
    String customerID = "cus_OxE4pN1mFGcxGf";
    String EphericalKey = "ephkey_1O9JfyJ9nvNqi0CJ2Z2gOYGC";
    String ClientSecret = "pi_3O9Jh6J9nvNqi0CJ0ZGdYbY0_secret_SOn6fkEEFtW5GD0vxFkFzRTb6";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stripe_payment);

        mPay = findViewById(R.id.payBtn);

        PaymentConfiguration.init(this,PUBLISH_KEY);

        paymentSheet = new PaymentSheet(this, paymentSheetResult ->
        {
            onPaymentResult(paymentSheetResult);
        });

        mPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                PaymentFlow();

            }
        });

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://api.stripe.com/v1/customers",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        try {
                            JSONObject object = new JSONObject(response);
                            customerID = object.getString("id");
                            Toast.makeText(StripePayment.this,customerID,Toast.LENGTH_LONG).show();


                            getEphericalKey(customerID);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                //Pending

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                Map<String, String> header = new HashMap<>();
                header.put("Authorization","Bearer" + SECRET_KEY);
                return header;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(StripePayment.this);
        requestQueue.add(stringRequest);
    }

    private void onPaymentResult(PaymentSheetResult paymentSheetResult)
    {
        if(paymentSheetResult instanceof PaymentSheetResult.Completed)
        {
            Toast.makeText(this, "Payment was successful",Toast.LENGTH_SHORT).show();
        }else
        {
            Toast.makeText(this, "Payment was not successful!",Toast.LENGTH_SHORT).show();

        }

    }

    private void getEphericalKey(String customerID)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://api.stripe.com/v1/ephemeral_keys",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        try {
                            JSONObject object = new JSONObject(response);
                            EphericalKey = object.getString("id");
                            Toast.makeText(StripePayment.this,EphericalKey,Toast.LENGTH_LONG).show();


                            getClientSecret(customerID, EphericalKey);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                //Pending

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                Map<String, String> header = new HashMap<>();
                header.put("Authorization","Bearer" + SECRET_KEY);
                header.put("Stripe-Version","2023-10-16");
                return header;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<>();
                params.put("customer", customerID);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(StripePayment.this);
        requestQueue.add(stringRequest);

    }

    private void getClientSecret(String customerID, String ephericalKey)
    {

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://api.stripe.com/v1/payment_intents",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        try {
                            JSONObject object = new JSONObject(response);
                            ClientSecret = object.getString("client_secret");
                            Toast.makeText(StripePayment.this,ClientSecret,Toast.LENGTH_LONG).show();



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                //Pending

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                Map<String, String> header = new HashMap<>();
                header.put("Authorization","Bearer" + SECRET_KEY);
                return header;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<>();
                params.put("customer", customerID);
                params.put("amount", "100"+"00");
                params.put("currency", "usd");
                params.put("automatic_payment_methods[enabled]", "true");

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(StripePayment.this);
        requestQueue.add(stringRequest);
    }

    private void PaymentFlow()
    {
        if (customerID != null && EphericalKey != null) {
            paymentSheet.presentWithPaymentIntent(
                    ClientSecret, new PaymentSheet.Configuration("LLZ PROPERTIES",
                            new PaymentSheet.CustomerConfiguration(
                                    customerID,
                                    EphericalKey
                            ))
            );
        } else {
            // Handle the case where customerID or EphericalKey is null
            Toast.makeText(this, "Customer information is missing.", Toast.LENGTH_SHORT).show();
        }

    }
}