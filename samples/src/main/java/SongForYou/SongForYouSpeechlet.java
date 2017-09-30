package SongForYou;

import SongForYou.Contacts.ContactsDao;
import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.*;
import com.amazon.speech.ui.OutputSpeech;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SongForYouSpeechlet implements Speechlet {
    private static final Logger log = LoggerFactory.getLogger(SongForYouSpeechlet.class);
    private ContactsDao contactsDao;

    private static final String CONTACT_NAME_SLOT_TYPE = "ContactName";
    private static final String SONG_NAME_SLOT_TYPE = "SongName";
    private static final String DEFAULT_PHONENUMBER = "7348461740";

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

            // Find the contact
            final String contactName = intent.getSlot(CONTACT_NAME_SLOT_TYPE).getValue();
            String phoneNumber = DEFAULT_PHONENUMBER;
            try {
                phoneNumber = contactsDao.retrieveContactNumber(contactName);
            }
            catch (NullPointerException ex) {
                log.error("Contact {} is not in the database", contactName);
            }
            log.info("Retrieved phone number {} for contact {}", phoneNumber, contactName);

            // Send notification

            // Return response

            PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
            outputSpeech.setText("OK, sending song to " + contactName);
            return SpeechletResponse.newTellResponse(outputSpeech);
        }
        else if ("PlaySongIntent".equals(intent.getName())) {
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
    }
}
