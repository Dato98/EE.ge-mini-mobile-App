package com.example.shop.helpers;

public class ErrorManage {
    private StringBuilder ErrorData;

    public ErrorManage() {
        ErrorData = new StringBuilder();
    }

    public void AddError(String msg){
        ErrorData.append(msg+"\n");
    }

    public String getErrorData(){return ErrorData.toString();}
}
