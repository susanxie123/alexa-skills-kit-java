package chatup;


import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;

import java.util.HashSet;
import java.util.Set;

public class ChatUpSpeechletRequestStreamHandler extends SpeechletRequestStreamHandler {
    private static final Set<String> supportedApplicationIds;

    static {
        supportedApplicationIds = new HashSet<String>();
        supportedApplicationIds.add("amzn1.ask.skill.b7f5ae00-756c-4713-8785-87a6eec1dc2a");
    }

    public ChatUpSpeechletRequestStreamHandler() {
        super(new ChatUpSpeechlet(), supportedApplicationIds);
    }
}
