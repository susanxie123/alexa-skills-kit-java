package SongForYou;

import java.io.*;
import java.net.*;

import com.amazon.speech.slu.Slot;
import org.json.JSONObject;
import SongForYou.Contacts.ContactsDao;
import SongForYou.Notifications.NotificationSender;
import SongForYou.RequestedUrlStore.RequestedUrlDao;
import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.*;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SongForYouSpeechlet implements Speechlet {
    private static final Logger log = LoggerFactory.getLogger(SongForYouSpeechlet.class);
    private ContactsDao contactsDao;
    private RequestedUrlDao requestedUrlDao;
    private NotificationSender notificationSender;

    private static final String CONTACT_NAME_SLOT_TYPE = "ContactName";
    private static final String SONG_NAME_SLOT_TYPE = "SongName";
    private static final String DEFAULT_USERNAME = "Alice"; // This is the user that request and receive the song

    @Override
    public void onSessionStarted(final SessionStartedRequest request, final Session session)
            throws SpeechletException {
        log.info("onSessionStarted requestId={}, sessionId={}", request.getRequestId(),
                session.getSessionId());

        initializeComponents();

    }

    @Override
    public SpeechletResponse onLaunch(final LaunchRequest request, final Session session)
            throws SpeechletException {
        log.info("onLaunch requestId={}, sessionId={}", request.getRequestId(),
                session.getSessionId());

        return new SpeechletResponse();
    }

    @Override
    public SpeechletResponse onIntent(IntentRequest request, Session session)
            throws SpeechletException {
        log.info("onIntent requestId={}, sessionId={}", request.getRequestId(),
                session.getSessionId());
        initializeComponents();

        Intent intent = request.getIntent();

        if ("RequestSongIntent".equals(intent.getName())) {
            // Find the url for this song
            final String songName = intent.getSlot(SONG_NAME_SLOT_TYPE).getValue();
            final String songUrl;
            try {
                songUrl = getSongUrl(songName);
            }
            catch (Exception ex) {
                log.error("Cannot find url for the song {} with error " + ex.getMessage(), songName);
                PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
                outputSpeech.setText("Sorry, I cannot find the song named " + songName);
                return SpeechletResponse.newTellResponse(outputSpeech);
            }
            log.info("Found url {} for song {}", songUrl, songName);

            // Find the contact TODO: use account linking
            final String contactName = intent.getSlot(CONTACT_NAME_SLOT_TYPE).getValue();
            String phoneNumber;
            try {
                phoneNumber = contactsDao.retrieveContactNumber(contactName);
            }
            catch (NullPointerException ex) {
                log.error("Contact {} is not in the database", contactName);
                PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
                outputSpeech.setText("Sorry, I cannot find " + contactName + " in your contacts list");
                return SpeechletResponse.newTellResponse(outputSpeech);
            }
            log.info("Retrieved phone number {} for contact {}", phoneNumber, contactName);

            // Send notification
            final String result = notificationSender.sendNotificationTo(phoneNumber);
            log.info("Sent notification with result {}", result);

            // TODO: Record song url for this contact


            // Return response
            PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
            outputSpeech.setText("OK, sending song to " + contactName);
            return SpeechletResponse.newTellResponse(outputSpeech);
        }
        else if ("PlaySongIntent".equals(intent.getName())) {
            // Maybe disambiguate the name??

            // Find the song url from database
            String url;
            Slot requestedBySlot = intent.getSlot(CONTACT_NAME_SLOT_TYPE);
            if (requestedBySlot == null) {
                log.info("User did not specify who requested the song.");
                url = requestedUrlDao.getRequestedUrl(DEFAULT_USERNAME);
                if (url.isEmpty()) {
                    PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
                    outputSpeech.setText("Sorry, nobody has requested you a song. Please try again later.");
                    return SpeechletResponse.newTellResponse(outputSpeech);
                }
            }
            else {
                String requestedBy = requestedBySlot.getValue();
                url = requestedUrlDao.getRequestedUrl(DEFAULT_USERNAME, requestedBy);
                if (url.isEmpty()) {
                    PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
                    outputSpeech.setText("Sorry, I cannot find the song requested by " + requestedBy);
                    return SpeechletResponse.newTellResponse(outputSpeech);
                }
            }
            log.info("Found the url for the requested song: {}", url);

            // TODO: Return audio playback response
            return new SpeechletResponse();
        }
        else if ("AMAZON.PauseIntent".equals(intent.getName())) {
            // what to do?
            return new SpeechletResponse();
        }
        else if ("AMAZON.ResumeIntent".equals(intent.getName())) {
            // what to do?
            return new SpeechletResponse();
        }
        else {
            throw new IllegalArgumentException("Unrecognized intent: " + intent.getName());
        }
    }

    @Override
    public void onSessionEnded(final SessionEndedRequest request, final Session session)
            throws SpeechletException {
        log.info("onSessionEnded requestId={}, sessionId={}", request.getRequestId(),
                session.getSessionId());
        //any cleanup logic
    }

    private void initializeComponents() {
        //Database stuff and other components
        this.contactsDao = new ContactsDao();
        this.requestedUrlDao = new RequestedUrlDao();
        this.notificationSender = new NotificationSender();
    }

    public static String getSongUrl(String songName) throws Exception {
        String query = songName.replace(' ', '+');
        String url = "https://itunes.apple.com/search?term=" + query + "&limit=1";
        String result = getHTTP(url);
        JSONObject jsonObj = new JSONObject(result);
        return jsonObj.getJSONArray("results").getJSONObject(0).getString("previewUrl");
    }
    
    public static String getHTTP(String urlToRead) throws Exception {
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlToRead);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
           result.append(line);
        }
        rd.close();
        return result.toString();
    }
}
