package com.example.androidlabs;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

import static com.example.androidlabs.ProfileActivity.ACTIVITY_NAME;

public class ChatRoomActivity extends AppCompatActivity {

    ListView theList;
    Button btnSend;
    Button btnReceive;
    EditText editText;
    List<Message> DisplayMsgs = new ArrayList<>();
    BaseAdapter myAdapter;
    Cursor results;
    SQLiteDatabase db;
    int idColIndex;
    int isSentColIndex;
    int messageColIndex;
    public static final String CHAT_ACTIVITY = "CHAT_ROOM_ACTIVITY";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);

        theList =  findViewById(R.id.messageField);
        btnSend = findViewById(R.id.sendBtn);
        btnReceive = findViewById(R.id.receiveBtn);
        editText = findViewById(R.id.chatText);

        MyDatabaseOpenHelper dbOpener = new MyDatabaseOpenHelper(this);
        db = dbOpener.getWritableDatabase();

        String [] columns = {MyDatabaseOpenHelper.COL_ID, MyDatabaseOpenHelper.COL_ISSENT, MyDatabaseOpenHelper.COL_MESSAGE};
        results = db.rawQuery("SELECT * FROM " + MyDatabaseOpenHelper.TABLE_NAME, null);

        idColIndex = results.getColumnIndex(MyDatabaseOpenHelper.COL_ID);
        isSentColIndex = results.getColumnIndex(MyDatabaseOpenHelper.COL_ISSENT);
        messageColIndex = results.getColumnIndex(MyDatabaseOpenHelper.COL_MESSAGE);

        while(results.moveToNext())
        {
            String message = results.getString(messageColIndex);
            int isSent = results.getInt(isSentColIndex);
            long id = results.getLong(idColIndex);

            DisplayMsgs.add(new Message(id, isSent, message));
        }

        this.printCursor(results);

        myAdapter = new MyListAdapter();
        theList.setAdapter(myAdapter);



        btnSend.setOnClickListener( send ->{
            String message = editText.getText().toString();
            int isSent = 0;

            ContentValues newRowValues = new ContentValues();
            newRowValues.put(MyDatabaseOpenHelper.COL_ISSENT, isSent);
            newRowValues.put(MyDatabaseOpenHelper.COL_MESSAGE, message);
            long newId = db.insert(MyDatabaseOpenHelper.TABLE_NAME, null, newRowValues);

            Message newMessage = new Message(newId, isSent, message);
            DisplayMsgs.add(newMessage);

            myAdapter.notifyDataSetChanged();
            editText.setText("");

        });

        btnReceive.setOnClickListener( receive ->{
            String message = editText.getText().toString();
            int isSent = 1;

            ContentValues newRowValues = new ContentValues();
            newRowValues.put(MyDatabaseOpenHelper.COL_ISSENT, isSent);
            newRowValues.put(MyDatabaseOpenHelper.COL_MESSAGE, message);
            long newId = db.insert(MyDatabaseOpenHelper.TABLE_NAME, null, newRowValues);

            Message newMessage = new Message(newId,isSent, message);
            DisplayMsgs.add(newMessage);

            myAdapter.notifyDataSetChanged();
            editText.setText("");
        });

    }

    public void printCursor(Cursor c){
        Log.d(CHAT_ACTIVITY,"Database Version Number: " + db.getVersion());
        Log.d(CHAT_ACTIVITY,"Number of Columns: "+ c.getColumnCount());
        Log.d(CHAT_ACTIVITY,"Name of Columns: "+ c.getColumnName(c.getColumnIndex(MyDatabaseOpenHelper.COL_ID))
                            + " " + c.getColumnName(c.getColumnIndex(MyDatabaseOpenHelper.COL_ISSENT))
                            + " " + c.getColumnName(c.getColumnIndex(MyDatabaseOpenHelper.COL_MESSAGE)));
        Log.d(CHAT_ACTIVITY,"Number of Results: "+ c.getCount());
        Log.d(CHAT_ACTIVITY,"Each row of Results: ");
        results.moveToFirst();
        while(!c.isAfterLast() ) {
            Log.d(CHAT_ACTIVITY, "id " + c.getLong(c.getColumnIndex(MyDatabaseOpenHelper.COL_ID)) + "");
            Log.d(CHAT_ACTIVITY, "isSent " + c.getInt(c.getColumnIndex(MyDatabaseOpenHelper.COL_ISSENT)) + "");
            Log.d(CHAT_ACTIVITY, "message " + c.getString(c.getColumnIndex(MyDatabaseOpenHelper.COL_MESSAGE)) + "");
            c.moveToNext();
        }
    }

    private class MyListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return DisplayMsgs.size();
        }

        @Override
        public Message getItem(int position) {
            return DisplayMsgs.get(position);
        }

        @Override
        public long getItemId(int position) {
            return getItem(position).getId();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
//            boolean type = DisplayMsgs.get(position).isSentMessage();
            View thisRow;

            if (DisplayMsgs.get(position).isSentMessage() == 0){
                thisRow  = inflater.inflate(R.layout.send, parent, false);
            } else {
                thisRow = inflater.inflate(R.layout.receive, parent, false);
            }

            TextView display = thisRow.findViewById(R.id.Text);
            String showMsg = getItem(position).getContent();
            display.setText(showMsg);

            return thisRow;
        }

    }

}

