#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time    : 2019/7/13 18:56
# @Author  : FrankLiu
# @Contact : liufft98@gmail.com
# @File    : ShakeHands.py
# @Software: PyCharm


import serial #module for serial port communication
import signal
import sys
import keyboard as keb

from picamera import PiCamera, Color
from time import sleep

import socket
import time
import sys

HOST_IP = "192.168.12.1"    #我的树莓派作为AP热点的ip地址
HOST_PORT = 7654            #端口号

print("Starting socket: TCP...")
socket_tcp = socket.socket(socket.AF_INET, socket.SOCK_STREAM)    #创建socket

print("TCP server listen @ %s:%d!" %(HOST_IP, HOST_PORT) )
host_addr = (HOST_IP, HOST_PORT)
socket_tcp.bind(host_addr)    #绑定我的树莓派的ip地址和端口号
socket_tcp.listen(1)    #listen函数的参数是监听客户端的个数，这里只监听一个，即只允许与一个客户端创建连接

ser = serial.Serial('/dev/ttyS0', 9600, timeout=1)
demoCamera = PiCamera()

#Note: Serial port read/write returns "byte" instead of "str"
ser.write("1".encode('utf-8'))


# def CarControl(data):
#     while (1):
#         print("Input the command:")
#
#         ser.write(data.encode('utf-8'))
#         if (data == 'a' or data == 's' or data == 'd' or data == 'w'):
#             while (1):
#                 if (keb.is_pressed(data) == False):
#                     ser.write('x'.encode('utf-8'))
#                     break;

while True:
        print ('waiting for connection...')
        socket_con, (client_ip, client_port) = socket_tcp.accept()    #接收客户端的请求
        print("Connection accepted from %s." %client_ip)


        while True:
                data=socket_con.recv(1024)    #接收数据

                if (data == 'a' or data == 's' or data == 'd' or data == 'w'):
                    while (1):
                        if (data == 'x'):
                            ser.write('x'.encode('utf-8'))
                            break;
                        socket_con.send(data)

socket_tcp.close()






try:
    while 1:
        handInf = ser.read()
        handInfo = handInf.decode('utf-8')
        if handInfo == '2':
            print("Arduino Online")
            ser.write("3".encode('utf-8'))
            demoCamera.start_preview()
            demoCamera.annotate_background = Color('white')
            demoCamera.annotate_foreground = Color('red')
            demoCamera.annotate_text = " SWS3009B - 2019"
            CarControl();

except KeyboardInterrupt:
    ser.close()
