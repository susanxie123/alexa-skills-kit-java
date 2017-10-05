package chatup;

import chatup.storage.ChatUpDao;
import chatup.storage.ChatUpDynamoDbClient;
import com.amazon.speech.speechlet.*;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

import java.util.Optional;

public class ChatUpSpeechlet implements Speechlet{
    @Override
    public void onSessionStarted(final SessionStartedRequest request, final Session session)
            throws SpeechletException {
        String userId = session.getUser().getUserId();
        Optional<String> userName = dao.getUserNameFromDatabase(userId);
        if (userName.isPresent()) {
            session.setAttribute(ChatUpSessionAttribute.userNameAttribute, userName);
        }
        session.setAttribute(ChatUpSessionAttribute.matchedAttribute, false);
        session.setAttribute(ChatUpSessionAttribute.searchingAttribute, false);
    }

    @Override
    public SpeechletResponse onLaunch(final LaunchRequest request, final Session session)
            throws SpeechletException {
        return ChatUpIntentHandler.helpResponse();
    }

    @Override
    public void onSessionEnded(final SessionEndedRequest request, final Session session)
            throws SpeechletException {

    }

    @Override
    public SpeechletResponse onIntent(IntentRequest request, Session session)
            throws SpeechletException {
        return ChatUpIntentHandler.handle(request, session);
    }

    /**
     * Initializes the instance components if needed.
     */
    private void initializeComponents() {

    }

    private static ChatUpDao dao = new ChatUpDao(new ChatUpDynamoDbClient(new AmazonDynamoDBClient()));
}
