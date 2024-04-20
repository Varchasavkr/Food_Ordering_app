package food.app.demo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;


    public class DataBaseHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "USER_RECORD";
        private static final String TABLE_USER_DATA = "USER_DATA";
        private static final String TABLE_ORDER_DATA = "ORDER_DATA";
        private static final String COL_1 = "ID";
        private static final String COL_2 = "USERNAME";
        private static final String COL_3 = "EMAIL";
        private static final String COL_4 = "PASSWORD";
        private static final String COL_5 = "NUMBER";

        // Columns for ORDER_DATA table
        private static final String ORDER_COL_1 = "ID";
        private static final String ORDER_COL_2 = "ORDER_ID"; // Linked to ID column in USER_DATA table
        private static final String ORDER_COL_3 = "LIST_OF_ITEMS";
        private static final String ORDER_COL_4 = "PAID";
        private static final String ORDER_COL_5 = "DELIVERY_TIME";

        public DataBaseHelper(@Nullable Context context) {
            super(context, DATABASE_NAME, null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // Creating USER_DATA table
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_USER_DATA + "(ID INTEGER PRIMARY KEY AUTOINCREMENT , USERNAME TEXT , EMAIL TEXT , PASSWORD TEXT,NUMBER TEXT )");

            // Creating ORDER_DATA table
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_ORDER_DATA + "(ID TEXT  , ORDER_ID TEXT, LIST_OF_ITEMS TEXT, PAID TEXT, DELIVERY_TIME TEXT)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(" DROP TABLE IF EXISTS " + TABLE_USER_DATA);
            db.execSQL(" DROP TABLE IF EXISTS " + TABLE_ORDER_DATA);
            onCreate(db);
        }

        public boolean registerUser(String username, String email, String password, String number) {

            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COL_2, username);
            values.put(COL_3, email);
            values.put(COL_4, password);
            values.put(COL_5, number);

            long result = db.insert(TABLE_USER_DATA, null, values);
            return result != -1;
        }

        public boolean checkUser(String username, String password) {

            SQLiteDatabase db = this.getWritableDatabase();
            String[] columns = {COL_1};
            String selection = COL_3 + "=?" + " and " + COL_4 + "=?";
            String[] selectionargs = {username, password};
            Cursor cursor = db.query(TABLE_USER_DATA, columns, selection, selectionargs, null, null, null);
            int count = cursor.getCount();
            cursor.close();
            return count > 0;
        }

        // Method to add an order for a user
        public boolean addOrder(String id,String order_id, String listOfItems, String paid, String deliveryTime) {
            try {
                SQLiteDatabase db = this.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(ORDER_COL_1, id);
                values.put(ORDER_COL_2, order_id);
                values.put(ORDER_COL_3, listOfItems);
                values.put(ORDER_COL_4, paid);
                values.put(ORDER_COL_5, deliveryTime);

                long result = db.insert(TABLE_ORDER_DATA, null, values);
                return result != -1;
            } catch (Exception e) {
                Log.e("DataBaseHelper", "Error adding order: " + e.getMessage());
                return false;
            }

        }

        public Cursor getData(String id){

            SQLiteDatabase db = this.getWritableDatabase();

            String query = "SELECT * FROM " +TABLE_ORDER_DATA+ " WHERE ID='" +id+"'";
            Cursor cursor = db.rawQuery(query , null);

            return cursor;
        }
    }