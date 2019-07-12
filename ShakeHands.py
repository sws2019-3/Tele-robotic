import serial #module for serial port communication
import signal
import sys

from picamera import PiCamera, Color
from time import sleep

def CarControl():
    while (1):
        inKey = input("Input the command:")
        if inKey == "exit":
            ser.write("e".encode('utf-8'))
            break
        ser.write(inKey.encode('utf-8'))
        recByte = ser.read()
        if (recByte.decode('utf-8') == '0'):
            print("Control Success")

ser = serial.Serial('/dev/ttyS0', 9600, timeout=1)
demoCamera = PiCamera()

#Note: Serial port read/write returns "byte" instead of "str"
ser.write("1".encode('utf-8'))

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

