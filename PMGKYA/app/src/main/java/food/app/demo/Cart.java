package food.app.demo;

import static java.security.AccessController.getContext;
import static java.sql.Types.NULL;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Cart extends AppCompatActivity implements PaymentResultListener {
    ArrayList<FoodItemModel> foodItems;
    Integer amount=0;
    int count=0;
    String orderItem="";
    private DataBaseHelper myDB;
    TabLayout tabLayout;
    ViewPager viewPager;
    Random random = new Random();
    int randomNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_fragment);
        Intent intent = getIntent();
         foodItems = (ArrayList<FoodItemModel>) intent.getSerializableExtra("food_items");

        final ArrayList<FoodItemModel> arrayList = new ArrayList<FoodItemModel>();
        myDB = new DataBaseHelper(getApplicationContext());
        // add all the values from 1 to 15 to the arrayList
        // the items are of the type NumbersView
        foodItems.add(new FoodItemModel(R.drawable.onion, "Onion", "Rs:10"));
        foodItems.add(new FoodItemModel(R.drawable.dhaniya, "Dhaniya", "Rs:1"));
        foodItems.add(new FoodItemModel(R.drawable.mirchi, "Mirchi", "Rs:5"));


        CartFragment adapter =new CartFragment(this,foodItems);



        // create the instance of the ListView to set the numbersViewAdapter
        ListView numbersListView = findViewById(R.id.listView);

        // set the numbersViewAdapter for ListView
        numbersListView.setAdapter(adapter);

        for (FoodItemModel item : foodItems) {
            orderItem += item.food_name + ", " +item.food_price + "\n "; // Concatenate item followed by a comma and space
        }

        String price="";


        for (FoodItemModel foodItem : foodItems) {
            // Extract the foodPrice from each FoodItemModel object
             price=foodItem.food_price;

           amount+= findSum(price);
            // Add the foodPrice to the total amount

        }
        TextView t1=findViewById(R.id.Rs);
            try {

                t1.setText("Rs:" +amount);



            }catch(Exception e)
            {
                t1.setText("Rs:" + amount );
            }



        Button pay=findViewById(R.id.payButton);
        Checkout.preload(getApplicationContext());
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPayment();

            }
        });



    }
    private void startPayment()
    {

        /**
         * Instantiate Checkout
         */

        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_QE0qrRVq6Ry7RU");

        /**
         * Set your logo here
         */
//        checkout.setImage(R.drawable.logo);

        /**
         * Reference to current activity
         */
        final Activity activity = this;

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();

            options.put("name", "PMGAY_APP");
            options.put("description", "Reference No. #123456");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg");
//            options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", amount*100);//pass amount in currency subunits
            options.put("prefill.email", "varchasav25@example.com");
            options.put("prefill.contact","7903672601");
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(activity, options);



        } catch(Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
        }
    }







    static int findSum(String str)
    {
        // A temporary string
        String temp = "0";

        // holds sum of all numbers present in the string
        int sum = 0;

        // read each character in input string
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);

            // if current character is a digit
            if (Character.isDigit(ch))
                temp += ch;

                // if current character is an alphabet
            else {
                // increment sum by number found earlier
                // (if any)
                sum += Integer.parseInt(temp);

                // reset temporary string to empty
                temp = "0";
            }
        }

        // atoi(temp.c_str()) takes care of trailing
        // numbers
        return sum + Integer.parseInt(temp);
    }


    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(this,"Successful payment done",Toast.LENGTH_SHORT).show();



        // Generate a random number within the specified range
        randomNumber = random.nextInt(8999999) + 1000000;
           adddata();




        Intent intent =new Intent(getApplicationContext(),payment.class);
        startActivity(intent);
    }

    private void adddata()
    {
        try{
            boolean var = myDB.addOrder( String.valueOf(1),String.valueOf(randomNumber),orderItem ,String.valueOf(amount),"Delivered between monday aug and thursday 20 from 8pm to 9:32 pm");
        }catch (Exception e)
        {
            Log.d("error",e.toString());
        }

    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this," payment failed! , try again",Toast.LENGTH_SHORT).show();

    }
}


