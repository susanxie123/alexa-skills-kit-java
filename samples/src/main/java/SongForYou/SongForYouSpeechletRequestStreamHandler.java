package SongForYou;

import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;

import java.util.HashSet;
import java.util.Set;

public class SongForYouSpeechletRequestStreamHandler extends SpeechletRequestStreamHandler {
    private static final Set<String> supportedApplicationIds;

    static {
        /*
         * This Id can be found on https://developer.amazon.com/edw/home.html#/ "Edit" the relevant
         * Alexa Skill and put the relevant Application Ids in this Set.
         */
        supportedApplicationIds = new HashSet<String>();
        supportedApplicationIds.add("amzn1.ask.skill.864663ec-3b57-49bb-a62b-38c1eff2a75e");
    }

    public SongForYouSpeechletRequestStreamHandler() {
        super(new SongForYouSpeechlet(), supportedApplicationIds);
    }
}
