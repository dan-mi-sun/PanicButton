Then /^I verify "(.*?)" button is "(.*?)"$/ do|button_id,button_status|
  query("button text:'#{button_id}'")[0]["button_status"]
end

Then /^I verify action button is "(.*?)"$/ do|button_status|
  query("button id:'action_button'")[0]["button_status"]
end

Then /^I verify action button text is "(.*?)"$/ do|button_text|
  query("button id:'action_button'")[0]["text"] == button_text
end

Then /^I enter "(.*?)" into contact field (\d+)$/ do |text_to_enter, index|
  query("editText id:'contact_edit_text' index:#{index}", {:setText => text_to_enter})
end

Then /^I press back button$/ do
  CustomOps.adb_command("shell input keyevent 4")
end

Then /^I press home button$/ do
  CustomOps.adb_command("shell input keyevent 3")
end

And(/^I start application$/) do
  CustomOps.adb_command("shell am start -n com.apb.beacon/.HomeActivity")
end

And(/^I clear log$/) do
  CustomOps.adb_command("logcat -c")
end

And(/^I check sms text contains "(.*?)"$/) do |sms_text|
  # Get sms text
  log_input = "adb logcat -d -n5 -s \"com.apb.beacon.sms.SMSAdapter\""
  log_output = `#{log_input}`
  print log_output

  #check sms text
  if not log_output.include? sms_text
    fail(msg="text not found")
  end

end

Then /^I turn off wifi$/ do
  CustomOps.install_development_app
  CustomOps.open_dev_app_connection_settings
  CustomOps.adb_command("shell input keyevent 61")
  CustomOps.adb_command("shell input keyevent 23")
end

Then /^I switch gps$/ do
  CustomOps.cmd_command(ENV["ANDROID_HOME"] + "/tools/monkeyrunner #{CustomOps.get_more_dir}/gpsmonkey.py")
end

Then /^I turn on wifi$/ do
  CustomOps.install_development_app
  CustomOps.open_dev_app_connection_settings
  CustomOps.adb_command("shell input keyevent 23")
end

And(/^I press power button 5 times$/) do
  log ENV["ANDROID_HOME"] + "/tools/monkeyrunner #{CustomOps.get_more_dir}/presspowr5times.py"
  CustomOps.cmd_command(ENV["ANDROID_HOME"] + "/tools/monkeyrunner -plugin features/support/more/androidviewclient-3.2.0.jar #{CustomOps.get_more_dir}/presspowr5times.py")
end

And(/^I unlock device$/) do
  CustomOps.cmd_command(ENV["ANDROID_HOME"] + "/tools/monkeyrunner #{CustomOps.get_more_dir}/unlockscreen.py")
end


And(/^I click 5 times fast on calculation$/) do
  CustomOps.cmd_command(ENV["ANDROID_HOME"] + "/tools/monkeyrunner #{CustomOps.get_more_dir}/clikingoncalculation.py")
end

And(/^I long press custom$/) do
  CustomOps.cmd_command(ENV["ANDROID_HOME"] + "/tools/monkeyrunner #{CustomOps.get_more_dir}/longpress.py")
end