package com.example.b_project;

public class MyApp{
    private String text;
    private String rearmoviename="",rearid="",rearcomment="",rearrating="3.0";

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    private MyApp(){

    }

    public String getRearmoviename() {
        return rearmoviename;
    }

    public void setRearmoviename(String rearmoviename) {
        this.rearmoviename = rearmoviename;
    }

    public String getRearid() {
        return rearid;
    }

    public void setRearid(String rearid) {
        this.rearid = rearid;
    }

    public String getRearcomment() {
        return rearcomment;
    }

    public void setRearcomment(String rearcomment) {
        this.rearcomment = rearcomment;
    }

    public String getRearrating() {
        return rearrating;
    }

    public void setRearrating(String rearrating) {
        this.rearrating = rearrating;
    }

    private volatile static MyApp instance = null;
    public static MyApp getInstance(){
        if(instance==null){
            synchronized (MyApp.class){
                if(instance==null){
                    instance = new MyApp();
                }
            }
        }
        return instance;
    }

}
