package com.example.pitcp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;


import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private EditText et_send;
    private Button bt_send;

    private Button goForward;
    private Button goBackward;
    private Button turnRight;
    private Button turnLeft;

    private TextView tv_recv;

    private String send_buff=null;
    private String recv_buff=null;

    private Handler handler = null;

    Socket socket = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        handler = new Handler();

        //单开一个线程来进行socket通信
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket("172.31.117.140" , 7654);
                    if (socket!=null) {
                        System.out.println("###################");
                        while (true) {      //循环进行收发
                            recv();
                            send();
                            Thread.sleep(50);
                        }
                    }
                    else
                        System.out.println("socket is null");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        send();
    }


    private void recv() {

        //单开一个线程循环接收来自服务器端的消息
        InputStream inputStream = null;
        try {
            inputStream = socket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (inputStream!=null){
            try {
                byte[] buffer = new byte[1024];
                int count = inputStream.read(buffer);//count是传输的字节数
                recv_buff = new String(buffer);//socket通信传输的是byte类型，需要转为String类型
                System.out.println(recv_buff);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //将受到的数据显示在TextView上
        if (recv_buff!=null){
            handler.post(runnableUi);

        }
    }

    //不能在子线程中刷新UI，应为textView是主线程建立的
    Runnable runnableUi = new Runnable() {
        @Override
        public void run() {
            tv_recv.append("\n"+recv_buff);
        }
    };

    @SuppressLint("ClickableViewAccessibility")
    private void send() {
        bt_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        send_buff = et_send.getText().toString();
                        //向服务器端发送消息
                        System.out.println("------------------------");
                        OutputStream outputStream=null;
                        try {
                            outputStream = socket.getOutputStream();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        if(outputStream!=null){
                            try {
                                outputStream.write(send_buff.getBytes());
                                System.out.println("1111111111111111111111");
                                outputStream.flush();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }).start();

            }
        });

        goForward.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View view, final MotionEvent motionEvent) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (view.getId() == R.id.goForward){
                            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                                send_buff = "w";
                                //向服务器端发送消息
                                System.out.println("------------------------");
                                OutputStream outputStream=null;
                                try {
                                    outputStream = socket.getOutputStream();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                if(outputStream!=null){
                                    try {
                                        outputStream.write(send_buff.getBytes());
                                        System.out.println("1111111111111111111111");
                                        outputStream.flush();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            if (motionEvent.getAction() == MotionEvent.ACTION_UP){
                                send_buff = "x";
                                //向服务器端发送消息
                                System.out.println("------------------------");
                                OutputStream outputStream=null;
                                try {
                                    outputStream = socket.getOutputStream();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                if(outputStream!=null){
                                    try {
                                        outputStream.write(send_buff.getBytes());
                                        System.out.println("1111111111111111111111");
                                        outputStream.flush();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                }).start();
                return false;
            }
        });

        goBackward.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View view, final MotionEvent motionEvent) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (view.getId() == R.id.goBackward){
                            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                                send_buff = "s";
                                //向服务器端发送消息
                                System.out.println("------------------------");
                                OutputStream outputStream=null;
                                try {
                                    outputStream = socket.getOutputStream();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                if(outputStream!=null){
                                    try {
                                        outputStream.write(send_buff.getBytes());
                                        System.out.println("1111111111111111111111");
                                        outputStream.flush();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            if (motionEvent.getAction() == MotionEvent.ACTION_UP){
                                send_buff = "x";
                                //向服务器端发送消息
                                System.out.println("------------------------");
                                OutputStream outputStream=null;
                                try {
                                    outputStream = socket.getOutputStream();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                if(outputStream!=null){
                                    try {
                                        outputStream.write(send_buff.getBytes());
                                        System.out.println("1111111111111111111111");
                                        outputStream.flush();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                }).start();
                return false;
            }
        });

        turnRight.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View view, final MotionEvent motionEvent) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (view.getId() == R.id.turnRight){
                            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                                send_buff = "d";
                                //向服务器端发送消息
                                System.out.println("------------------------");
                                OutputStream outputStream=null;
                                try {
                                    outputStream = socket.getOutputStream();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                if(outputStream!=null){
                                    try {
                                        outputStream.write(send_buff.getBytes());
                                        System.out.println("1111111111111111111111");
                                        outputStream.flush();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            if (motionEvent.getAction() == MotionEvent.ACTION_UP){
                                send_buff = "x";
                                //向服务器端发送消息
                                System.out.println("------------------------");
                                OutputStream outputStream=null;
                                try {
                                    outputStream = socket.getOutputStream();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                if(outputStream!=null){
                                    try {
                                        outputStream.write(send_buff.getBytes());
                                        System.out.println("1111111111111111111111");
                                        outputStream.flush();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                }).start();
                return false;
            }
        });

        turnLeft.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View view, final MotionEvent motionEvent) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (view.getId() == R.id.turnLeft){
                            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                                send_buff = "a";
                                //向服务器端发送消息
                                System.out.println("------------------------");
                                OutputStream outputStream=null;
                                try {
                                    outputStream = socket.getOutputStream();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                if(outputStream!=null){
                                    try {
                                        outputStream.write(send_buff.getBytes());
                                        System.out.println("1111111111111111111111");
                                        outputStream.flush();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            if (motionEvent.getAction() == MotionEvent.ACTION_UP){
                                send_buff = "x";
                                //向服务器端发送消息
                                System.out.println("------------------------");
                                OutputStream outputStream=null;
                                try {
                                    outputStream = socket.getOutputStream();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                if(outputStream!=null){
                                    try {
                                        outputStream.write(send_buff.getBytes());
                                        System.out.println("1111111111111111111111");
                                        outputStream.flush();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                }).start();
                return false;
            }
        });

        goForward.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View view, final MotionEvent motionEvent) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (view.getId() == R.id.goForward){
                            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                                send_buff = "w";
                                //向服务器端发送消息
                                System.out.println("------------------------");
                                OutputStream outputStream=null;
                                try {
                                    outputStream = socket.getOutputStream();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                if(outputStream!=null){
                                    try {
                                        outputStream.write(send_buff.getBytes());
                                        System.out.println("1111111111111111111111");
                                        outputStream.flush();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            if (motionEvent.getAction() == MotionEvent.ACTION_UP){
                                send_buff = "x";
                                //向服务器端发送消息
                                System.out.println("------------------------");
                                OutputStream outputStream=null;
                                try {
                                    outputStream = socket.getOutputStream();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                if(outputStream!=null){
                                    try {
                                        outputStream.write(send_buff.getBytes());
                                        System.out.println("1111111111111111111111");
                                        outputStream.flush();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                }).start();
                return false;
            }
        });

    }

    private void initView() {
        et_send = (EditText) findViewById(R.id.et_send);
        bt_send = (Button) findViewById(R.id.bt_send);
        goForward = (Button) findViewById(R.id.goForward);
        goBackward = (Button) findViewById(R.id.goBackward);
        turnRight = (Button) findViewById(R.id.turnRight);
        turnLeft = (Button) findViewById(R.id.turnLeft);
        tv_recv = (TextView) findViewById(R.id.tv_recv);
    }
}
