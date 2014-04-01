from com.android.monkeyrunner import MonkeyRunner, MonkeyDevice
from subprocess import call
import commands	
import sys
import os
from com.dtmilano.android.viewclient import ViewClient
#print "restart adb"
#call(os.environ['ANDROID_HOME'] + '/platform-tools/adb kill-server', shell=True)
#call(os.environ['ANDROID_HOME'] + '/platform-tools/adb start-server', shell=True)

device = MonkeyRunner.waitForConnection(15, os.environ['ADB_DEVICE_ARG'] )
 
print "press Learn"

vc = ViewClient(device, os.environ['ADB_DEVICE_ARG'])
vc.dump()

button = vc.findViewWithText("Learn")

button.touch()

print "start pressing power button"

device.press('KEYCODE_POWER' , MonkeyDevice.DOWN_AND_UP)
MonkeyRunner.sleep(1)
device.press('KEYCODE_POWER' , MonkeyDevice.DOWN_AND_UP)
MonkeyRunner.sleep(0.2)
device.press('KEYCODE_POWER' , MonkeyDevice.DOWN_AND_UP)
MonkeyRunner.sleep(0.2)
device.press('KEYCODE_POWER' , MonkeyDevice.DOWN_AND_UP)
MonkeyRunner.sleep(0.2)
device.press('KEYCODE_POWER' , MonkeyDevice.DOWN_AND_UP)
MonkeyRunner.sleep(0.2)
device.press('KEYCODE_POWER' , MonkeyDevice.DOWN_AND_UP)
MonkeyRunner.sleep(1)
device.press('KEYCODE_POWER' , MonkeyDevice.DOWN_AND_UP)
MonkeyRunner.sleep(0.2)
device.press('KEYCODE_POWER' , MonkeyDevice.DOWN_AND_UP)
MonkeyRunner.sleep(0.2)
device.press('KEYCODE_POWER' , MonkeyDevice.DOWN_AND_UP)
MonkeyRunner.sleep(0.2)
device.press('KEYCODE_POWER' , MonkeyDevice.DOWN_AND_UP)
device.press('KEYCODE_POWER' , MonkeyDevice.DOWN_AND_UP)
MonkeyRunner.sleep(1)
device.press('KEYCODE_POWER' , MonkeyDevice.DOWN_AND_UP)
MonkeyRunner.sleep(0.2)
device.press('KEYCODE_POWER' , MonkeyDevice.DOWN_AND_UP)
MonkeyRunner.sleep(0.2)
device.press('KEYCODE_POWER' , MonkeyDevice.DOWN_AND_UP)

print "done"