package com.example.androidlabs;

public class Message {
    String content;
    long id;
    int isSent;

    public Message(){}

    public Message(String content, int isSent){
        this.content = content;
        this.isSent = isSent;
    }

    public Message (long id, int isSent, String content){
        this.id = id;
        this.isSent = isSent;
        this.content = content;
    }

    public String getContent(){
        return this.content;
    }

    public int isSentMessage(){
        return this.isSent;
    }

    public long getId(){return this.id;}
}
