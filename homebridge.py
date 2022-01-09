import RPi.GPIO as GPIO
import time
from time import strftime
import pyrebase
import board
import neopixel
pixels1 = neopixel.NeoPixel(board.D21, 2)

#Initialize Firebase
firebaseConfig={"apiKey": "AIzaSyAx9dlQ4BwzJtSsvBG6lEmfFyBTM-YgTAQ",
    "authDomain": "homebridge-ef297.firebaseapp.com",
    "databaseURL": "https://homebridge-ef297-default-rtdb.firebaseio.com",
    "projectId": "homebridge-ef297",
    "storageBucket": "homebridge-ef297.appspot.com",
    "messagingSenderId": "556416575529",
    "appId": "1:556416575529:web:4ae27a926174c59fe75438"}

firebase=pyrebase.initialize_app(firebaseConfig)
db=firebase.database()

#Initialize Relay
in1 = 18
in2 = 23
in3 = 24
in4 = 25


GPIO.setmode(GPIO.BCM)
GPIO.setup(in1, GPIO.OUT)
GPIO.setup(in2, GPIO.OUT)
GPIO.setup(in3, GPIO.OUT)
GPIO.setup(in4, GPIO.OUT)


GPIO.output(in1, True)
GPIO.output(in2, True)
GPIO.output(in3, True)
GPIO.output(in4, True)

    
# try:
while True:
    lamptime = strftime("%I:%M%p")
    print(lamptime)
    #LAMPU
    modelamp = db.child("modelamp").get()
    contlamphour = db.child("countlamphour").get()
    contlampmins = db.child("countlampmins").get()
    if modelamp.val() == "timer":
        timerlampon = db.child("timerlampon").get()
        timerlampoff = db.child("timerlampoff").get()
        if lamptime == timerlampon.val():
            pixels1.fill((255,255,255))
            db.child("statuslamp").set("hidup")
            db.child("minsementaraon").set(strftime("%M"))
            db.child("hoursementaraon").set(strftime("%I"))
        elif lamptime == timerlampoff.val():
            pixels1.fill((0,0,0))
            db.child("statuslamp").set("mati")
            db.child("minsementaraoff").set(strftime("%M"))
            db.child("hoursementaraoff").set(strftime("%I"))
            
            
    if modelamp.val() == "manual":
        led = db.child("led").get()
        if led.val() == "1":
            pixels1.fill((255, 255, 255))
            db.child("statuslamp").set("hidup")
        elif led.val() == "0":
            pixels1.fill((0, 0, 0))
            db.child("statuslamp").set("mati")
        elif led.val() == "2":
            pixels1.fill((255, 0, 0))
            db.child("statuslamp").set("hidup")
        elif led.val() == "3":
            pixels1.fill((0, 255, 0))
            db.child("statuslamp").set("hidup")
        elif led.val() == "4":
            pixels1.fill((0, 0, 255))
            db.child("statuslamp").set("hidup")
        pixels1.show()
    
    #FAN
    modefan = db.child("modefan").get()
    if modefan.val() == "timer":
        timerfanon = db.child("timerfanon").get()
        timerfanoff = db.child("timerfanoff").get()
        if lamptime == timerfanon.val():
            GPIO.output(in1, False)
            db.child("statusfan").set("hidup")
        elif lamptime == timerfanoff.val():
            GPIO.output(in1, True)
            db.child("statusfan").set("mati")
    if modefan.val() == "manual":
        data1 = db.child("relay1").get()
        if data1.val() == "0":
            GPIO.output(in1, True)
            db.child("statusfan").set("mati")
        elif data1.val() == "1":
            GPIO.output(in1, False)
            db.child("statusfan").set("hidup")
    
    #FAN
    modetv = db.child("modetv").get()
    if modetv.val() == "timer":
        timertvon = db.child("timertvon").get()
        timertvoff = db.child("timertvoff").get()
        if lamptime == timertvon.val():
            GPIO.output(in2, False)
            db.child("statustv").set("hidup")
        elif lamptime == timertvoff.val():
            GPIO.output(in2, True)
            db.child("statustv").set("mati")
    if modetv.val() == "manual":
        data2 = db.child("relay2").get()
        if data2.val() == "0":
            GPIO.output(in2, True)
            db.child("statustv").set("mati")
        elif data2.val() == "1":
            GPIO.output(in2, False)
            db.child("statustv").set("hidup")
        

    #REFRIGATOR
    moderef = db.child("moderef").get()
    if moderef.val() == "timer":
        timerrefon = db.child("timerrefon").get()
        timerrefoff = db.child("timerrefoff").get()
        if lamptime == timerrefon.val():
            GPIO.output(in3, False)
            db.child("statusref").set("hidup")
        elif lamptime == timerrefoff.val():
            GPIO.output(in3, True)
            db.child("statusref").set("mati")
    if moderef.val() == "manual":
        data3 = db.child("relay3").get()
        if data3.val() == "0":
            GPIO.output(in3, True)
            db.child("statusref").set("mati")
        elif data3.val() == "1":
            GPIO.output(in3, False)
            db.child("statusref").set("hidup")
    
    #FAN
    moderelay4 = db.child("moderelay4").get()
    if moderelay4.val() == "timer":
        timerrelay4on = db.child("timerrelay4on").get()
        timerrelay4off = db.child("timerrelay4off").get()
        if lamptime == timerrelay4on.val():
            GPIO.output(in4, False)
            db.child("statusrelay4").set("hidup")
        elif lamptime == timerrelay4off.val():
            GPIO.output(in4, True)
            db.child("statusrelay4").set("mati")
    if moderelay4.val() == "manual":
        data4 = db.child("relay4").get()
        if data4.val() == "0":
            GPIO.output(in4, True)
            db.child("statusrelay4").set("mati")
        elif data4.val() == "1":
            GPIO.output(in4, False)
            db.child("statusrelay4").set("hidup")
            
    
    #KALKULASI
    if lamptime == "00:00AM":
        db.child("countlamp").set("0")
        db.child("countfan").set("0")
        db.child("counttv").set("0")
        db.child("countref").set("0")
        db.child("countrelay4").set("0")
        
    minlamp = int(db.child("minsementaraoff").get().val()) - int(db.child("minsementaraon").get().val())
    hourlamp = int(db.child("hoursementaraoff").get().val()) - int(db.child("hoursementaraon").get().val())
    totallamp = minlamp + (hourlamp*60)
    savelamp = int(db.child("countlamp").get().val()) + totallamp
    db.child("countlamp").set(savelamp)
        
    
        
            
    
    #Control Relay Manual
    #data1 = db.child("relay1").get()
    #if data1.val() == "0":
    #    GPIO.output(in1, True)
    #elif data1.val() == "1":
    #    GPIO.output(in1, False)
    #data2 = db.child("relay2").get()
    #if data2.val() == "0":
    #    GPIO.output(in2, True)
    #elif data2.val() == "1":
    #    GPIO.output(in2, False)
    #data3 = db.child("relay3").get()
    #if data3.val() == "0":
    #    GPIO.output(in3, True)
    #elif data3.val() == "1":
    #    GPIO.output(in3, False)
    #data4 = db.child("relay4").get()
    #if data4.val() == "0":
    #    GPIO.output(in4, True)
    #elif data4.val() == "1":
    #    GPIO.output(in4, False)
        
    #NeoPixel
    #led = db.child("led").get()
    #if led.val() == "1":
    #    pixels1.fill((255, 255, 255))
    #elif led.val() == "0":
    #    pixels1.fill((0, 0, 0))
    #elif led.val() == "2":
    #    pixels1.fill((255, 0, 0))
    #elif led.val() == "3":
    #    pixels1.fill((0, 255, 0))
    #elif led.val() == "4":
    #    pixels1.fill((0, 0, 255))
    #pixels1.show()