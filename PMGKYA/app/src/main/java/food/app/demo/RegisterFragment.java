package food.app.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class RegisterFragment extends Fragment {
    private EditText emailSignUp , usernameSignUp , passwordSignUp ,Number;
    private Button signUpButton;
    private DataBaseHelper myDB;
    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        emailSignUp = view.findViewById(R.id.email_txt);
        usernameSignUp = view.findViewById(R.id.name_txt);
        passwordSignUp = view.findViewById(R.id.password_txt);

        signUpButton = view.findViewById(R.id.register_btn);
        Number = view.findViewById(R.id.phone_txt);

        myDB = new DataBaseHelper(getContext());
        insertUser();
        return view;
    }

    private void insertUser(){
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean var = myDB.registerUser(usernameSignUp.getText().toString() , emailSignUp.getText().toString() , passwordSignUp.getText().toString(),Number.getText().toString());
                if(var){
                    Toast.makeText(getContext(), "User Registered Successfully !!", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(getContext() , DashboardActivity.class));
                    getActivity().finish();
                }
                else
                    Toast.makeText(getContext(), "Registration Error !!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}