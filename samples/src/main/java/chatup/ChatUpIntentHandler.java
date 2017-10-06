package chatup;

import chatup.storage.ChatUpDao;
import chatup.storage.ChatUpDynamoDbClient;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.OutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SsmlOutputSpeech;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

import java.util.Optional;

public class ChatUpIntentHandler {
    public static SpeechletResponse handle(IntentRequest request, Session session) {
        String intent = request.getIntent().getName();
        if (intent.equals("FindChatIntent")) {
            return handleFindChatIntent(request, session);
        }
        else if (intent.equals("StartChatIntent")) {
            return handleStartChatIntent(request, session);
        }
        else if (intent.equals("EndChatIntent")) {
            return handleEndChatIntent(request, session);
        }
        else if (intent.equals("SetUserNameIntent")) {
            return handleSetUserNameIntent(request, session);
        }
        else if (intent.equals("GetUserNameIntent")) {
            return handleGetUserNameIntent(request, session);
        }
        else if (intent.equals("AMAZON.HelpIntent")) {
            return handleHelpIntent(request, session);
        }
        else if (intent.equals("AMAZON.StopIntent")) {
            return handleStopIntent(request, session);
        }
        else if (intent.equals("AMAZON.CancelIntent")) {
            return handleCancelIntent(request, session);
        }
        else {
            return handleUnknownIntent(request, session);
        }
    }

    private static SpeechletResponse handleFindChatIntent(IntentRequest request, Session session) {
        if (session.getAttribute(ChatUpSessionAttribute.matchedAttribute).equals(true)) {
            //already has a match, prompt for rematch
        }
        else{
            if (session.getAttribute(ChatUpSessionAttribute.searchingAttribute).equals(false)) {
                session.setAttribute(ChatUpSessionAttribute.searchingAttribute, true);
            }
            Optional<String> chatMate = dao.findChatFromDatabase(
                    session.getAttribute(ChatUpSessionAttribute.userNameAttribute).toString());
            if (chatMate.isPresent()) {
                session.setAttribute(ChatUpSessionAttribute.chatMateNameAttribute, chatMate.get());
            }
            else {
                session.setAttribute(ChatUpSessionAttribute.chatMateNameAttribute, defaultChatter);
            }
        }
        SsmlOutputSpeech speech = new SsmlOutputSpeech();
        speech.setSsml("I find for you to chat");
        Reprompt reprompt = new Reprompt();
        SpeechletResponse response = SpeechletResponse.newAskResponse(speech, reprompt);
        return new SpeechletResponse();
    }

    private static SpeechletResponse handleStartChatIntent(IntentRequest request, Session session) {
        return new SpeechletResponse();
    }

    private static SpeechletResponse handleEndChatIntent(IntentRequest request, Session session) {
        return new SpeechletResponse();
    }

    private static SpeechletResponse handleSetUserNameIntent(IntentRequest request, Session session) {
        return new SpeechletResponse();
    }

    private static SpeechletResponse handleGetUserNameIntent(IntentRequest request, Session session) {
        return new SpeechletResponse();
    }

    private static SpeechletResponse handleHelpIntent(IntentRequest request, Session session) {
        return helpResponse();
    }

    private static SpeechletResponse handleStopIntent(IntentRequest request, Session session) {
        return new SpeechletResponse();
    }

    private static SpeechletResponse handleCancelIntent(IntentRequest request, Session session) {
        return new SpeechletResponse();
    }

    private static SpeechletResponse handleUnknownIntent(IntentRequest request, Session session) {
        return new SpeechletResponse();
    }

    public static SpeechletResponse helpResponse() {
        return new SpeechletResponse();
    }

    private static final String defaultChatter = "Idiot";
    private static ChatUpDao dao = new ChatUpDao(new ChatUpDynamoDbClient(new AmazonDynamoDBClient()));
}
