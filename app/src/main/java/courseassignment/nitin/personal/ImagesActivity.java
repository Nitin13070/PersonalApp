package courseassignment.nitin.personal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ImagesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);
        getSupportActionBar().setTitle(getString(R.string.images_title));
    }
}
