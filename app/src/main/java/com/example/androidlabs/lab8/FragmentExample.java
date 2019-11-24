package com.example.androidlabs.lab8;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidlabs.Message;
import com.example.androidlabs.MyDatabaseOpenHelper;
import com.example.androidlabs.R;

import java.util.ArrayList;
import java.util.List;


public class FragmentExample extends AppCompatActivity {
//
////    public static final String ITEM_SELECTED = "ITEM";
////    public static final String ITEM_POSITION = "POSITION";
//    public static final String ITEM_ID = "ID";
//    public static final String ITEM_MESSAGE = "MESSAGE";
//    public static final String ITEM_ISSEND = "ISSEND";
//    public static final int EMPTY_ACTIVITY = 345;
//    Cursor results;
//    SQLiteDatabase db;
//    int idColIndex;
//    int isSentColIndex;
//    int messageColIndex;
//
//    BaseAdapter myAdapter;
//    List<Message> chatMessage = new ArrayList<>();
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.chat_activity);
//
//        ListView theList = (ListView)findViewById(R.id.messageField);
//        boolean isTablet = findViewById(R.id.fragmentLocation) != null; //check if the FrameLayout is loaded
//
//        MyDatabaseOpenHelper dbOpener = new MyDatabaseOpenHelper(this);
//        db = dbOpener.getWritableDatabase();
//
//        String [] columns = {MyDatabaseOpenHelper.COL_ID, MyDatabaseOpenHelper.COL_ISSENT, MyDatabaseOpenHelper.COL_MESSAGE};
//        results = db.rawQuery("SELECT * FROM " + MyDatabaseOpenHelper.TABLE_NAME, null);
//
//        idColIndex = results.getColumnIndex(MyDatabaseOpenHelper.COL_ID);
//        isSentColIndex = results.getColumnIndex(MyDatabaseOpenHelper.COL_ISSENT);
//        messageColIndex = results.getColumnIndex(MyDatabaseOpenHelper.COL_MESSAGE);
//
//        while(results.moveToNext())
//        {
//            String message = results.getString(messageColIndex);
//            int isSent = results.getInt(isSentColIndex);
//            long id = results.getLong(idColIndex);
//
//            chatMessage.add(new Message(id, isSent, message));
//        }
//
//        myAdapter = new MyListAdapter();
//        theList.setAdapter(myAdapter);
//;
//        theList.setOnItemClickListener( (list, item, position, id) -> {
//
//            Bundle dataToPass = new Bundle();
//            dataToPass.putString(ITEM_MESSAGE, chatMessage.get(position).getContent());
//            dataToPass.putInt(ITEM_ISSEND, chatMessage.get(position).isSentMessage());
//            dataToPass.putLong(ITEM_ID, chatMessage.get(position).getId());
//
//            if(isTablet)
//            {
//                DetailFragment dFragment = new DetailFragment(); //add a DetailFragment
//                dFragment.setArguments( dataToPass ); //pass it a bundle for information
//                dFragment.setTablet(true);  //tell the fragment if it's running on a tablet or not
//                getSupportFragmentManager()
//                        .beginTransaction()
//                        .add(R.id.fragmentLocation, dFragment) //Add the fragment in FrameLayout
//                        .addToBackStack("AnyName") //make the back button undo the transaction
//                        .commit(); //actually load the fragment.
//            }
//            else //isPhone
//            {
//                Intent nextActivity = new Intent(FragmentExample.this, EmptyActivity.class);
//                nextActivity.putExtras(dataToPass); //send data to next activity
//                startActivityForResult(nextActivity, EMPTY_ACTIVITY); //make the transition
//            }
//        });
//    }
//
//    //This function only gets called on the phone. The tablet never goes to a new activity
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//      super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == EMPTY_ACTIVITY)
//        {
//            if(resultCode == RESULT_OK) //if you hit the delete button instead of back button
//            {
//                long id = data.getLongExtra(ITEM_ID, 0);
//                deleteMessageId((int)id);
//            }
//        }
//    }
//
//    public void deleteMessageId(int id)
//    {
//        Log.i("Delete this message:" , " id="+id);
//        chatMessage.remove(id);
//        myAdapter.notifyDataSetChanged();
//    }
//
//    private class MyListAdapter extends BaseAdapter {
//
//        @Override
//        public int getCount() {
//            return chatMessage.size();
//        }
//
//        @Override
//        public Message getItem(int position) {
//            return chatMessage.get(position);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return getItem(position).getId();
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            LayoutInflater inflater = getLayoutInflater();
////            boolean type = DisplayMsgs.get(position).isSentMessage();
//            View thisRow;
//
//            if (chatMessage.get(position).isSentMessage() == 0){
//                thisRow  = inflater.inflate(R.layout.send, parent, false);
//            } else {
//                thisRow = inflater.inflate(R.layout.receive, parent, false);
//            }
//
//            TextView display = thisRow.findViewById(R.id.Text);
//            String showMsg = getItem(position).getContent();
//            display.setText(showMsg);
//
//            return thisRow;
//        }
//
//    }
}
