## Inspiration
We were planning on doing an Android app that used Vision API to live translate images on a mobile screen using an overlay, but soon realized that learning all those tools would take too much time through the hackathon.  Instead we were inspired by a suggestion from a mentor, Emmanuel, to create an Android browser that stabilizes for people with Parkinson's disease or other neurological diseases that cause tremors.

## What it does
SteadyView uses a phone's accelerometers to analyze the shaking of that phone and uses that data to stabilize a mobile web browser, making it easier for people suffering from tremors to access the internet, wherever they want.

## How we built it
We split the work between each other to focus on specific parts of the project like the notification system, the GUI, obtaining and storing the accelerometer data, and figuring out how to stabilize the screen with the data we obtained.  We all used Android Studio and wrote the majority of our code in Java, with bits of XML.

## Challenges we ran into
None of us have ever worked on Android, so this was a very new experience for all of us.  We also wanted to create an app that stabilized the screen using an overlay, but due to time restraints, decided to focus on a more specific problem, stabilizing web browsers.

## Accomplishments that we're proud of
We're proud of being able to finish an Android app for the first time, and relatively on schedule.  We also like to think that we've created something that will improve the lives of thousands of people.

## What we learned
We learned how the Android app development process was like and also how to work with mobile sensors and APIs.

## What's next for SteadyView
We'd like to be able to improve the regression algorithm to better the stabilization effect on our app, and also to allow our app to be a system-wide stabilizer for a mobile screen overlay, instead of just being restricted to a browser.
