package com.csclab.hc.socketsample;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.OutputStream;
import java.net.Socket;
import android.util.Log;

public class MainActivity extends Activity implements android.view.View.OnClickListener{
    /** Init Variable for IP page **/
    EditText inputIP;
    Button ipSend;
    String ipAdd = "";

    /** Init Variable for Page 1 **/
    Button btn;

    ///Android_Practice
    EditText inputNumTxt1;
    EditText inputNumTxt2;

    Button btnAdd;
    Button btnSub;
    Button btnMult;
    Button btnDiv;

    TextView textResult;

    Button return_button;

    float num1 = 0;
    float num2 = 0;
    float result = 0;

    String sendResult="";
    String strToSend;
    String oper = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ip_page);
        inputIP = (EditText)findViewById(R.id.edIP);
        ipSend = (Button)findViewById(R.id.ipButton);

        ipSend.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** Func() for setup page 1 **/

               ipAdd = inputIP.getText().toString();


                ///////~~
                Log.d("Client","Client Send");
                Thread t = new thread();
                t.start();
                ///////~~~


                jumpToMainLayout();
            }
        });
    }

    /** Function for page 1 setup */
    public void jumpToMainLayout() {
        //TODO: Change layout to activity_main
        setContentView(R.layout.activity_main);
               //   origin
         //       btn = (Button) findViewById(R.id.button);
        //        btn.setOnClickListener(this);



        //////////////////////////////          add~~~
        inputNumTxt1 = (EditText) findViewById(R.id.etNum1);
        inputNumTxt2 = (EditText) findViewById(R.id.etNum2);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnSub = (Button) findViewById(R.id.btnSub);
        btnMult = (Button) findViewById(R.id.btnMult);
        btnDiv = (Button) findViewById(R.id.btnDiv);

        btnAdd.setOnClickListener(this);
        btnSub.setOnClickListener(this);
        btnMult.setOnClickListener(this);
        btnDiv.setOnClickListener(this);
        //////////////////////////////////
    }

    /** Function for onclick() implement */
    @Override
    public void onClick(View v) {
        //         origin
        /*    Log.d("Client","Client Send");
            Thread t = new thread();
            t.start();*/

        ////////////////////    add~~~~~~~~~~~~~

        //float num1 = 0;
        //float num2 = 0;
        //float result = 0;

        if (TextUtils.isEmpty(inputNumTxt1.getText().toString())
                || TextUtils.isEmpty(inputNumTxt2.getText().toString())) {
            return;
        }
        num1 = Float.parseFloat(inputNumTxt1.getText().toString());
        num2 = Float.parseFloat(inputNumTxt2.getText().toString());
        switch (v.getId()) {
            case R.id.btnAdd:
                oper = "+";
                result = num1 + num2;
                break;
            case R.id.btnSub:
                oper = "-";
                result = num1 - num2;
                break;
            case R.id.btnMult:
                oper = "*";
                result = num1 * num2;
                break;
            case R.id.btnDiv:
                oper = "/";
                result = num1 / num2;
                break;
            default:
                break;
        }
        Log.d("debug", "ANS " + result);

    try{
        sendResult = new String(num1 + " " + oper + " " + num2 + " = " + result);
        strToSend = sendResult;
        Thread tt = new Thread();
        tt.start();
        /*sSocket socket = new Socket(ipAdd, 2000);
                OutputStream outer = socket.getOutputStream();

                byte[] sendB = new byte[1024];
                System.arraycopy(sendResult.getBytes(), 0, sendB, 0, sendResult.length());
                outer.write(sendB);*/
        jumpToResultLayout(new String(num1 + " " + oper + " " + num2 + " = " + result));
    }catch (Exception e){}



    }

    class thread extends Thread{
        public void run(){
            try{
                System.out.println("Client: Waiting to connect...");
                int serverPort = 2000;

                // Create socket connect server
                Socket socket = new Socket(ipAdd, serverPort);
                System.out.println("Connected!");

                // Create stream communicate with server
                OutputStream out = socket.getOutputStream();
                byte[] sendStrByte = new byte[1024];

                strToSend = "Hi I'm client";
                if(sendResult != null) {
                    System.arraycopy(sendResult.getBytes(), 0, sendStrByte, 0, sendResult.length());
                    out.write(sendStrByte);
                }else{
                    System.arraycopy(strToSend.getBytes(), 0, sendStrByte, 0, strToSend.length());
                    out.write(sendStrByte);
                }

            }catch (Exception e){
                System.out.println("Error" + e.getMessage());
            }
        }
    }

    ///////////////// add func~~~~~~~~~~~~~~~~~~~~~~~
    public void jumpToResultLayout(String resultStr){
        setContentView(R.layout.result_page);

        //TODO: Bind return_button and textResult form result view
        // HINT: findViewById()
        // HINT: Remember to give type
        return_button = (Button) findViewById(R.id.return_button);
        textResult = (TextView) findViewById(R.id.textResult);

        if (textResult != null) {
            //TODO: Set the result text
            textResult.setText(resultStr);
        }

        if (return_button != null) {
            //TODO: prepare button listener for return button
            // HINT:
            // mybutton.setOnClickListener(new View.OnClickListener(){
            //      public void onClick(View v) {
            //          // Something to do..
            //      }
            // }
            return_button.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v) {
                    // TODO
                    try {
                        /*Socket socket = new Socket(ipAdd, 2000);
                        OutputStream outer = socket.getOutputStream();
                        sendResult = new String(num1 + " " + oper + " " + num2 + " = " + result);
                        byte[] sendB = new byte[1024];
                        System.arraycopy(sendResult.getBytes(), 0, sendB, 0, sendResult.length());
                        outer.write(sendB);*/
                        Log.d("Client","Client Send");
                        Thread t = new thread();
                        t.start();

                        jumpToMainLayout();
                    }catch (Exception e){

                    }
                }

            });
        }
    }


}






