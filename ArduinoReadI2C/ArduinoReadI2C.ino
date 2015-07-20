#include <Wire.h>

void setup()
{
  Serial.begin(9600);
  while (!Serial) {
    ; // wait for serial port to connect. Needed for Leonardo only
  }
  Serial.println ("foo!");
  pinMode (13, OUTPUT);
  Wire.begin(4); // join i2c bus with address #4
  Wire.onReceive(receiveEvent); // register event
}

void loop()
{
  delay(1000);
  // Serial.println ("loop");
}

// function that executes whenever data is received from master
// this function is registered as an event, see setup()
void receiveEvent(int howMany)
{
  String LED = "";
  Serial.print ("howmany = ");
  Serial.println (howMany);

  while ( Wire.available() > 0 )
  {
    int i = Wire.read();
    Serial.println(i);
  }

}
