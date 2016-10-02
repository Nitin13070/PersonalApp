package courseassignment.nitin.personal;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PhoneBookActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PhoneBookAdapter adapter;
    private List<PhoneBook> contactList;
    Context mContext;
    DBHandler db;
    EditText newName;
    EditText newPhoneNo;
    EditText newAddress;
    EditText showName;
    EditText showPhoneNo;
    EditText showAddress;
    Button showEditBtn;
    Button showDeleteBtn;

    PhoneBook selectedContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_book);
        getSupportActionBar().setTitle(getString(R.string.phone_book_title));

        db = new DBHandler(this);

        mContext = this;

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_contact);
        contactList = new ArrayList<PhoneBook>();

        contactList = db.getAllContacts();

        adapter = new PhoneBookAdapter(this,contactList);

        RecyclerView.LayoutManager mLayoutManger = new GridLayoutManager(this, 2);

        recyclerView.setLayoutManager(mLayoutManger);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener( new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener(){

            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getApplicationContext(),String.valueOf(position),Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                View dialogView = getLayoutInflater().inflate(R.layout.show_contact_dialog,null);
                showName = (EditText) dialogView.findViewById(R.id.show_contact_name);
                showPhoneNo = (EditText) dialogView.findViewById(R.id.show_contact_no);
                showAddress = (EditText) dialogView.findViewById(R.id.show_contact_address);
                showEditBtn = (Button) dialogView.findViewById(R.id.edit_contact_btn);
                showDeleteBtn = (Button) dialogView.findViewById(R.id.delete_contact_btn);

                selectedContact = db.getContact(position+1);

                showName.setText(selectedContact.getFullName());
                showPhoneNo.setText(selectedContact.getPhoneNo());
                showAddress.setText(selectedContact.getAddress());

                builder.setView(dialogView);
                builder.create().show();
            }
        }));
    }

    public void addContact(View view){
        View dialogView;

        switch (view.getId()){
            case R.id.add_contact_btn:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                dialogView = getLayoutInflater().inflate(R.layout.addcontactdialog,null);
                newName = (EditText) dialogView.findViewById(R.id.new_contact_name);
                newPhoneNo = (EditText) dialogView.findViewById(R.id.new_contact_no);
                newAddress = (EditText) dialogView.findViewById(R.id.new_contact_address);
                builder.setView(dialogView);
                builder.create().show();

                break;

            case R.id.submit_contact_btn:
                if(newName.getText().toString().equals("")){
                    newName.setError("Please Enter Name");
                }
                else if(newAddress.getText().toString().equals("")){
                    newAddress.setError("Please Enter Address");
                }
                else if (newPhoneNo.getText().toString().equals("")){
                    newPhoneNo.setError("Please Enter Phone No");
                }
                else {
                    db.addContact(new PhoneBook(newName.getText().toString(),newPhoneNo.getText().toString(),newAddress.getText().toString()));
                    Toast.makeText(this,"Added",Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    public void editContact(View view){
        PhoneBook updateContact = new PhoneBook(selectedContact.getId(),showName.getText().toString(),showPhoneNo.getText().toString(),showAddress.getText().toString());
        db.updateContact(updateContact);
    }

    public void deleteContact(View view){
        db.deleteContact(selectedContact);
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view);
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
