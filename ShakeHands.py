import serial #module for serial port communication
import signal
import sys
import keyboard as keb

from picamera import PiCamera, Color
from time import sleep

def CarControl():
    while (1):
        print("Input the command:")
        inKey = keb.read_key()
        ser.write(inKey.encode('utf-8'))
        if (inKey == 'a' or inKey == 's' or inKey == 'd' or inKey == 'w'):
            while (1):
                if (keb.is_pressed(inKey) == False):
                    ser.write('x'.encode('utf-8'))
                    break;


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

