package food.app.demo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;
public class FoodDetailActivity extends AppCompatActivity {

    ImageView food_back;
    TextView food_name, food_price;
    ImageView food_image;
    TextView ordernow;
//    FoodItemModel[] foodItems = new FoodItemModel[10];
    List<FoodItemModel> foodItemList = new ArrayList<>();
    int amount=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        food_back = findViewById(R.id.food_back);
        food_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Intent intent = getIntent();

        food_name = findViewById(R.id.food_name);
        food_price = findViewById(R.id.food_price);
        food_image = findViewById(R.id.food_image);
        String imageName = intent.getStringExtra("food_image");

        food_name.setText(intent.getStringExtra("food_name"));
        food_price.setText(intent.getStringExtra("food_price"));
        food_image.setImageResource(intent.getIntExtra("food_image",R.drawable.wheat));

        ordernow = findViewById(R.id.food_order);
        ordernow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                amount += Integer.parseInt(food_price.getText().toString());
//                Log.d("amount",String.valueOf(amount));

               ; // Create a new ArrayList to store food names
                String foodItemName=food_name.getText().toString();
                String foodItemPrice=food_price.getText().toString();
//                foodItemList.add(new FoodItemModel(0, food_name.getText().toString(), food_price.getText().toString()));
                Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                intent.putExtra("foodItemName", foodItemName);
                intent.putExtra("foodItemPrice", foodItemPrice);
                intent.putExtra("foodItemImage",imageName);
                startActivity(intent);

                Toast.makeText(getApplicationContext(), "Item added to cart", Toast.LENGTH_SHORT).show();

            }
        });

    }


}
