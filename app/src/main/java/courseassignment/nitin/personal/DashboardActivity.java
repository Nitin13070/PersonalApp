package courseassignment.nitin.personal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        this.getSupportActionBar().setTitle("Dashboard");
    }

    public void imagesPhonebook(View view){
        Intent intent;
        switch (view.getId()){
            case R.id.store_images_dash_btn:
                intent = new Intent(this,ImagesActivity.class);
                startActivity(intent);
                break;

            case R.id.personal_phonebook_dash_btn:
                intent = new Intent(this,PhoneBookActivity.class);
                startActivity(intent);
                break;

        }
    }
}
