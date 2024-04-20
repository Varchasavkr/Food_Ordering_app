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

public class LoginFragment extends Fragment {

    private EditText loginUsername , loginPassword;
    private Button loginButton;
    private DataBaseHelper myDb;
    public LoginFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        loginUsername = view.findViewById(R.id.email_txt);
        loginPassword = view.findViewById(R.id.password_txt);
        loginButton = view.findViewById(R.id.login_btn);

        myDb = new DataBaseHelper(getContext());

        loginUser();


        return view;
    }



    private void loginUser(){
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean var = myDb.checkUser(loginUsername.getText().toString() , loginPassword.getText().toString());
                if (var){
                    Toast.makeText(getContext(), "Login Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getContext() , DashboardActivity.class));
                    getActivity().finish();
                }else{
                    Toast.makeText(getContext(), "Login Failed !!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}