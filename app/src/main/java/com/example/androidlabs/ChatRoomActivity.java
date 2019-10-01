package com.example.androidlabs;

import android.os.Bundle;
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

public class ChatRoomActivity extends AppCompatActivity {

    ListView theList;
    Button btnSend;
    Button btnReceive;
    EditText editText;
    List<Message> DisplayMsgs;
    BaseAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);

        DisplayMsgs = new ArrayList<>();

        theList =  findViewById(R.id.messageField);
        btnSend = findViewById(R.id.sendBtn);
        btnReceive = findViewById(R.id.receiveBtn);
        editText = findViewById(R.id.chatText);

        theList.setAdapter(myAdapter = new MyListAdapter());

        btnSend.setOnClickListener( send ->{
            Message sendMsg = new Message(editText.getText().toString(), true);
            DisplayMsgs.add(sendMsg);
            myAdapter.notifyDataSetChanged();
            editText.setText("");
        });

        btnReceive.setOnClickListener( receive ->{
            Message receiveMsg = new Message(editText.getText().toString(), false);
            DisplayMsgs.add(receiveMsg);
            myAdapter.notifyDataSetChanged();
            editText.setText("");
        });
    }

    private class MyListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return DisplayMsgs.size();
        }

        @Override
        public String getItem(int position) {
            return DisplayMsgs.get(position).getContent();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            boolean type = DisplayMsgs.get(position).isSentMessage();
            View thisRow;

            if (type){
                thisRow  = inflater.inflate(R.layout.send, parent, false);
            } else {
                thisRow = inflater.inflate(R.layout.receive, parent, false);
            }

            TextView display = thisRow.findViewById(R.id.Text);
            String showMsg = getItem(position);
            display.setText(showMsg);

            return thisRow;

        }
    }

    protected class Message {
        String content;
        boolean sentMessage;

        public Message(){}

        public Message(String content, boolean sentMessage){
            this.content = content;
            this.sentMessage = sentMessage;
        }

        public String getContent(){
            return this.content;
        }

        public boolean isSentMessage(){
            return this.sentMessage;
        }
    }
}

