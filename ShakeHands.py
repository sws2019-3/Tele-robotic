import serial #module for serial port communication

def CarControl():
    while (1):
        inKey = input("Input the command:")
        if inKey == "exit":
            ser.write("x".encode('utf-8'))
            break
        ser.write(inKey.encode('utf-8'))
        recByte = ser.read()
        if (recByte.decode('utf-8') == '0'):
            print("Control Success")

ser = serial.Serial('/dev/ttyS0', 9600, timeout=1)

#Note: Serial port read/write returns "byte" instead of "str"
ser.write("1".encode('utf-8'))

try:
    while 1:
        handInf = ser.read()
        handInfo = handInf.decode('utf-8')
        if handInfo == '2':
            print("Arduino Online")
            ser.write("3".encode('utf-8'))
            CarControl();

except KeyboardInterrupt:
    ser.close()

