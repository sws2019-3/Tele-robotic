import serial #module for serial port communication

ser = serial.Serial('/dev/ttyS0', 9600, timeout=1)

#Note: Serial port read/write returns "byte" instead of "str"
ser.write(1);

try:
    while 1:
        handInfo = ser.read()
        if handInfo == 2:
            print("Arduino Online")
            ser.write("3")

except KeyboardInterrupt:
    ser.close()
