#include <AFMotor.h>
#include <NewPing.h>

#define TRIGGER_PIN  22  // Arduino pin tied to trigger pin on the ultrasonic sensor.
#define ECHO_PIN     24  // Arduino pin tied to echo pin on the ultrasonic sensor.
#define MAX_DISTANCE 200

NewPing sonar(TRIGGER_PIN, ECHO_PIN, MAX_DISTANCE);

int state = -1;
// 0 forward; 1 backward; 2 left; 3 right
AF_DCMotor motor3(1);
AF_DCMotor motor4(2);

void setup() {
  // put your setup code here, to run once:

  motor3.setSpeed(1000);
  motor4.setSpeed(1000);
  
//  motor4.run( FORWARD );
//
//  motor3.run( BACKWARD );
  state = 0;
//  motor3.run( RELEASE );
}

void TurnLeft() {
  motor3.run(FORWARD);
  motor4.run(FORWARD);
  state = 2;
}

void GoForward() {
  motor3.run(FORWARD);
  motor4.run(BACKWARD);
  state = 0;
}

void loop() {
//  // put your main code here, to run repeatedly
  if (sonar.ping_cm() < 10 && state != 2) {
    TurnLeft();
  } else if (state != 0){
    GoForward();
  }
}
