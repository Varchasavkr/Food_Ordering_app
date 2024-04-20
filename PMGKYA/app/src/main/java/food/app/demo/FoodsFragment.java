package food.app.demo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FoodsFragment extends Fragment {

    RecyclerView recyclerView;
    FoodAdapter foodAdapter;
    List<FoodItemModel> list;

    public FoodsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_foods, container, false);

        list = new ArrayList<>();
        list.add(new FoodItemModel(R.drawable.wheat, "WHEAT/GEHU", "Rs:20"));
        list.add(new FoodItemModel(R.drawable.potato, "POTATO/ALOO", "Rs:12"));
        list.add(new FoodItemModel(R.drawable.lady_finger, "BHINDI", "Rs:14"));
        list.add(new FoodItemModel(R.drawable.pulses, "DAL", "Rs:30"));
        list.add(new FoodItemModel(R.drawable.rice, "RICE/CHAWAL", "Rs:5"));
        list.add(new FoodItemModel(R.drawable.brinjal, "BRINJAL", "Rs:25"));
        list.add(new FoodItemModel(R.drawable.mirchi, "MIRCHI", "Rs:5"));
        list.add(new FoodItemModel(R.drawable.nimbo, "NIMBO", "Rs:1"));
        list.add(new FoodItemModel(R.drawable.dhaniya, "DHANIYA", "Rs:1"));
        list.add(new FoodItemModel(R.drawable.onion, "ONION", "Rs:10"));

        recyclerView = view.findViewById(R.id.food_recycler);
        foodAdapter = new FoodAdapter(list, getActivity().getApplicationContext());
        LinearLayoutManager manager = new LinearLayoutManager(getActivity().getApplicationContext(), RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(foodAdapter);


        return view;
    }
}