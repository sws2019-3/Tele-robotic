import serial #module for serial port communication
import signal
import sys

import io
import picamera
import logging
import socketserver
from threading import Condition
from http import server

PAGE="""\
<html>
<head>
<title>SWS3009 PiCamera Peek-a-boo</title>
</head>
<body>
<h1>PiCamera Streaming Demo:</h1>
<img src="stream.mjpg" width="640" height="480" />
</body>
</html>
"""
class StreamingOutput(object):
    def __init__(self):
        self.frame = None
        self.buffer = io.BytesIO()
        self.condition = Condition()

    def write(self, buf):
        if buf.startswith(b'\xff\xd8'):
            # New frame, copy the existing buffer's content and notify all
            # clients it's available
            self.buffer.truncate()
            with self.condition:
                self.frame = self.buffer.getvalue()
                self.condition.notify_all()
            self.buffer.seek(0)
        return self.buffer.write(buf)

class StreamingHandler(server.BaseHTTPRequestHandler):
    def do_GET(self):
        if self.path == '/':
            self.send_response(301)
            self.send_header('Location', '/index.html')
            self.end_headers()
        elif self.path == '/index.html':
            content = PAGE.encode('utf-8')
            self.send_response(200)
            self.send_header('Content-Type', 'text/html')
            self.send_header('Content-Length', len(content))
            self.end_headers()
            self.wfile.write(content)
        elif self.path == '/stream.mjpg':
            self.send_response(200)
            self.send_header('Age', 0)
            self.send_header('Cache-Control', 'no-cache, private')
            self.send_header('Pragma', 'no-cache')
            self.send_header('Content-Type', 'multipart/x-mixed-replace; boundary=FRAME')
            self.end_headers()
            try:
                while True:
                    with output.condition:
                        output.condition.wait()
                        frame = output.frame
                    self.wfile.write(b'--FRAME\r\n')
                    self.send_header('Content-Type', 'image/jpeg')
                    self.send_header('Content-Length', len(frame))
                    self.end_headers()
                    self.wfile.write(frame)
                    self.wfile.write(b'\r\n')
            except Exception as e:
                logging.warning(
                    'Removed streaming client %s: %s',
                    self.client_address, str(e))
        else:
            self.send_error(404)
            self.end_headers()

class StreamingServer(socketserver.ThreadingMixIn, server.HTTPServer):
    allow_reuse_address = True
    daemon_threads = True

with picamera.PiCamera(resolution='640x480', framerate=24) as camera:
    output = StreamingOutput()
    camera.start_recording(output, format='mjpeg')
    try:
        address = ('', 8000)
        server = StreamingServer(address, StreamingHandler)
        server.serve_forever()
    finally:
        camera.stop_recording()

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

