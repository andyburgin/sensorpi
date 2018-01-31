import time
import datetime
from envirophat import light, motion, weather, leds
from kafka import KafkaProducer
import json

# uncomment for debug logging
#import logging
#logging.basicConfig(level=logging.DEBUG)

# connect to kafka for json payload
producer = KafkaProducer(bootstrap_servers='10.0.0.21:7000,10.0.0.22:7000,10.0.0.23:7000',value_serializer=lambda v: json.dumps(v).encode('utf-8'))

try:
    while True:

        # Get the measurements from the EnviroPhat
        temperature = weather.temperature()
        pressure = weather.pressure()
        heading = motion.heading()
        lightv = light.light()
        lightr, lightg, lightb = light.rgb()
        motionx, motiony, motionz = motion.accelerometer()

        # Get a local timestamp
        timestamp = int(datetime.datetime.now().strftime("%s")) * 1000 
        print ("Timestamp: {0} Temperature: {1} Pressure: {2} Heading: {3} Light: {4}({5},{6},{7}) Motion: {8},{9},{10}" .format(
        timestamp, temperature, pressure, heading, lightv, lightr, lightg, lightb, motionx, motiony, motionz 
        ))
        
        # Create datapoints and send to kafka sensors topic
        datapoints =  {
                    "timestamp": timestamp,
                    "temperature":temperature,
                    "pressure":pressure,
                    "heading":heading,
                    "lightv":lightv,
                    "lightr":lightr,
                    "lightg":lightg,
                    "lightb":lightb,
                    "motionx":motionx,
                    "motiony":motiony,
                    "motionz":motionz 
                    }
        producer.send("sensors",datapoints)
 
		# wait 1 second
        time.sleep(1)

except KeyboardInterrupt:
    leds.off()

