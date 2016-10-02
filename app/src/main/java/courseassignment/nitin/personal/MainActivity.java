package courseassignment.nitin.personal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static String UserName;
    SharedPreferences sharedPref;
    EditText userNameEditText;
    EditText passwordEditText;
    EditText signInPassEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPref = getSharedPreferences(getString(R.string.user_data_pref), Context.MODE_PRIVATE);



        UserName = sharedPref.getString(getString(R.string.username),null);
        if(UserName == null){
            setContentView(R.layout.activity_main_signup);
        }
        else {
            setContentView(R.layout.activity_main);
        }

        passwordEditText = (EditText) findViewById(R.id.sign_up_pass);
        signInPassEditText = (EditText) findViewById(R.id.sign_in_pass);
        userNameEditText = (EditText) findViewById(R.id.sign_up_username);
    }

    public void loginSignUp(View view){
        Intent intent ;
        switch (view.getId()){
            case R.id.sign_in_btn:
                if (signInPassEditText.getText().toString().equals("")){
                    Toast.makeText(this,"Please enter password",Toast.LENGTH_SHORT).show();
                }
                else if(!signInPassEditText.getText().toString().equals(sharedPref.getString(getString(R.string.password),""))){
                    Toast.makeText(this,"Invalid password",Toast.LENGTH_SHORT).show();
                }
                else {
                    intent = new Intent(this,DashboardActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.sign_up_btn:
                if(passwordEditText.getText().toString().equals("")  || userNameEditText.getText().toString().equals("")){
                    Toast.makeText(this,"Username or password can't be blank",Toast.LENGTH_SHORT).show();
                }
                else {
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString(getString(R.string.username),userNameEditText.getText().toString());
                    editor.putString(getString(R.string.password),passwordEditText.getText().toString());
                    editor.commit();
                    intent = new Intent(this,DashboardActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }
}
