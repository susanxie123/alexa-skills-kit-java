### Basic Design
- Since we are unable to access contact list, we should build 
an external contact list. Before we get time to do that, I simply use
a dynamoDB table with one entry.
- Since we do not have access to the notification feature, I use Twilio
API to send SMS notification, simply because I already have a Twilio 
account. 
- After a user request to send a song, we should be able to look up a 
media url for the song, and store it in another table (possibly dynamoDB)

### How to build
Go to the the root directory containing pom.xml, and run 
`mvn assembly:assembly -DdescriptorId=jar-with-dependencies package`. 
This will generate a zip file named 
`alexa-skills-kit-samples-1.0-jar-with-dependencies.jar` in the `target` directory.

Our major logic is in folder `src/main/java/SongForYou`, and the lambda function
endpoint is `SongForYou.SongForYouSpeechletRequestStreamHandler`

### TODO
- Find media url for the song
- Store the media url somewhere
- Figure out how to play audio
- Enable account linking, if time permitted. 
We need to use account linking to keep track of who the user is,
and to generate real contact.

### Finished
- Set up basic speechlet structure and response. Tested
- Created fake contact list. Tested
- Set up Twilio SMS notification. Tested
