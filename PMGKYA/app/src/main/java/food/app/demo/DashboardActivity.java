package food.app.demo;

import static java.security.AccessController.getContext;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {
    ArrayList<FoodItemModel> fooditems = new ArrayList<>();

    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        if (!isConnected(this)) {
            showInternetDialog();
        }

        ImageView foodCart =findViewById(R.id.food_cart);
        Intent intent1 = getIntent();
        String foodItemName= intent1.getStringExtra("foodItemName");
        String foodItemPrice= intent1.getStringExtra("foodItemPrice");
        String foodItemImage= intent1.getStringExtra("foodItemImage");
        int foodImageResourceId = R.drawable.wheat;
        fooditems.add(new FoodItemModel(foodImageResourceId,foodItemName,foodItemPrice));
        foodCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add your code here to handle the click event
                // For example, you can navigate to the Cart activity

                Intent intent = new Intent(getApplicationContext(), Cart.class);
                intent.putExtra("food_items",fooditems);

                startActivity(intent);
            }
        });

        Button logout=findViewById(R.id.logout_button);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext() , LoginActivity.class));

            }
        });

        tabLayout = findViewById(R.id.food_tab);
        viewPager = findViewById(R.id.food_viewpager);

        tabLayout.addTab(tabLayout.newTab().setText("FOOD"));     //0
        tabLayout.addTab(tabLayout.newTab().setText("VEGETABLES"));    //1
        tabLayout.addTab(tabLayout.newTab().setText("PULSES"));    //2
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
//
        final FoodItemAdapter adapter = new FoodItemAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void showInternetDialog() {

        Dialog dialog = new Dialog(this);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        View view = LayoutInflater.from(this).inflate(R.layout.no_internet_dialog, findViewById(R.id.no_internet_layout));
        view.findViewById(R.id.try_again).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isConnected(DashboardActivity.this)) {
                    showInternetDialog();
                } else {
                    startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                    finish();
                }
            }
        });

        dialog.setContentView(view);
        dialog.show();

    }

    private boolean isConnected(DashboardActivity dashboardActivity) {
        ConnectivityManager connectivityManager = (ConnectivityManager) dashboardActivity.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        return (wifiConn != null && wifiConn.isConnected()) || (mobileConn != null && mobileConn.isConnected());
    }

}