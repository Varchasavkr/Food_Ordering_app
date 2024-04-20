package food.app.demo;

import static food.app.demo.Cart.findSum;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;

import food.app.demo.FoodItemModel;



public class CartFragment extends ArrayAdapter<FoodItemModel> {
    private ArrayList<FoodItemModel> foodItems;

    // invoke the suitable constructor of the ArrayAdapter class

//  Context c;
int amount=0;
    public CartFragment(@NonNull Context context, ArrayList<FoodItemModel> arrayList) {

        // pass the context and arrayList for the super
        // constructor of the ArrayAdapter class
        super(context, 0, arrayList);

//        this.c=context;
        this.foodItems = arrayList;
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
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // convertView which is recyclable view
        View currentItemView = convertView;

        // of the recyclable view is null then inflate the custom layout for the same
        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.cart_item, parent, false);
        }

        // get the position of the view from the ArrayAdapter
        FoodItemModel currentNumberPosition = getItem(position);

        // then according to the position of the view assign the desired image for the same
        ImageView numbersImage = currentItemView.findViewById(R.id.imageView);
        assert currentNumberPosition != null;
        numbersImage.setImageResource(currentNumberPosition.getFood_image());

        // then according to the position of the view assign the desired TextView 1 for the same
        TextView textView1 = currentItemView.findViewById(R.id.textView1);
        textView1.setText(currentNumberPosition.getFood_name());

        // then according to the position of the view assign the desired TextView 2 for the same
        TextView textView2 = currentItemView.findViewById(R.id.textView2);
        textView2.setText(currentNumberPosition.getFood_price());

        Button   b1 = currentItemView.findViewById(R.id.button2);



        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                FoodItemModel selectedItem = getItem(position);

                // Perform the desired action, e.g., decrease quantity or remove from cart
                foodItems.remove(position);




//                         TextView v1= v.findViewById(R.id.Rs);
//                        String Price="";
//                        for (FoodItemModel foodItem : foodItems) {
//                            // Extract the foodPrice from each FoodItemModel object
//                            Price=foodItem.food_price;
//
//                            amount+= findSum(Price);
//                            // Add the foodPrice to the total amount
//
//                        }
//
//                        v1.setText(String.valueOf(amount));
//



                    // Decrease the quantity by 1
                    // Update the UI to reflect the changes

                    notifyDataSetChanged();
                // Notify the adapter that the dataset has changed


            }
        });



        // then return the recyclable view
        return currentItemView;
    }
}
