package courseassignment.nitin.personal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nitin on 10/2/2016.
 */
public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "PhoneBookDatabase";
    private static final String TABLE_PHONEBOOK = "PhoneBook";
    private static final String KEY_ID = "id";
    private static final String KEY_FULLNAME = "full_name";
    private static final String KEY_CONTACT = "contact";
    private static final String KEY_ADDR = "address";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PHONEBOOK_TABLE = "CREATE TABLE " + TABLE_PHONEBOOK + "("
        + KEY_ID + " INTEGER PRIMARY KEY," + KEY_FULLNAME + " TEXT," + KEY_CONTACT + " TEXT,"
        + KEY_ADDR +" TEXT" + ")";
        db.execSQL(CREATE_PHONEBOOK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PHONEBOOK);
        onCreate(db);
    }

    public void addContact(PhoneBook phoneBook){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_FULLNAME,phoneBook.getFullName());
        values.put(KEY_CONTACT,phoneBook.getPhoneNo());
        values.put(KEY_ADDR,phoneBook.getAddress());

        db.insert(TABLE_PHONEBOOK,null,values);
        db.close();
    }

    public PhoneBook getContact(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PHONEBOOK,new String[] {KEY_ID, KEY_FULLNAME, KEY_CONTACT, KEY_ADDR}, KEY_ID + "=?",new String[]{String.valueOf(id)},null,null,null);
        if(cursor != null){
            cursor.moveToFirst();
        }

        PhoneBook contact = new PhoneBook(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2),cursor.getString(3));

        return contact;

    }

    public List<PhoneBook> getAllContacts(){
        List<PhoneBook> contactList = new ArrayList<PhoneBook>();

        String selectQuery = "SELECT * FROM " + TABLE_PHONEBOOK;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do{
                PhoneBook contact = new PhoneBook(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2),cursor.getString(3));
                contactList.add(contact);
            }while (cursor.moveToNext());
        }

        return contactList;
    }

    public int getContactsCount() {
        String countQuery = "SELECT * FROM " + TABLE_PHONEBOOK;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }

    public int updateContact(PhoneBook contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FULLNAME, contact.getFullName());
        values.put(KEY_CONTACT, contact.getPhoneNo());
        values.put(KEY_ADDR, contact.getAddress());
        return db.update(TABLE_PHONEBOOK, values, KEY_ID + " = ?",
                new String[]{String.valueOf(contact.getId())});
    }

    public void deleteContact(PhoneBook contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PHONEBOOK, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getId()) });
        db.close();
    }
}
