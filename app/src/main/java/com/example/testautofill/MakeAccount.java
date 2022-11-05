package com.example.testautofill;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MakeAccount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_account);

        Button createAccount = (Button) findViewById(R.id.CASubmit);
        EditText CAName = findViewById(R.id.CAname);
        EditText CAEmail = findViewById(R.id.CAemail);
        EditText CAPassword1 = findViewById(R.id.CApassword1);
        EditText CAPassword2 = findViewById(R.id.CApassword2);

        String nuName = CAName.getText().toString();
        String nuEmail = CAEmail.getText().toString();
        String nuPassword1 = CAPassword1.getText().toString();
        String nuPassword2 = CAPassword2.getText().toString();


        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //checks empty String TODO replace with better comparison for valid / used information
                if (nuName == "")
                {
                    CAName.setBackgroundColor(Color.RED);
                }
                if (nuEmail == "")
                {
                    CAEmail.setBackgroundColor(Color.RED);
                }
                if (nuPassword1 == "" || nuPassword1 != nuPassword2)
                {
                    CAPassword1.setBackgroundColor(Color.RED);
                }
                if (nuPassword2 == "" || nuPassword2 != nuPassword1)
                {
                    CAName.setBackgroundColor(Color.RED);

                    //can put non matching Password message in either of the above 2
                }
                else
                {
                    //Valid input

                    //  output Writer Stream no working but its not importatn if i can get it to work on a server instead
                    //  UserAccount temp = new UserAccount(getResources().openRawResource(R.raw.acccounts),nuEmail,nuPassword1,nuName);


                }



            }
        });
    }
}