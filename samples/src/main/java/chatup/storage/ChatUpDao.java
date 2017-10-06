package chatup.storage;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ChatUpDao {
    private final ChatUpDynamoDbClient dynamoDbClient;

    public ChatUpDao(ChatUpDynamoDbClient chatUpDynamoDbClient) {this.dynamoDbClient = chatUpDynamoDbClient;}

    /**
     * get userName from userId
     * @param UserId  unique user ID passed in Session
     * @return Optional of user name
     */
    public Optional<String> getUserNameFromDatabase(String UserId) {
        ChatUpDynamoDataItem item = ChatUpDynamoDataItem.builder().userId(UserId).build();
        item = dynamoDbClient.loadItem(item);
        return item == null? Optional.empty() : Optional.of(item.getUserName());
    }

    public Optional<String> findChatFromDatabase(String ownerId) {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":val1", new AttributeValue().withN(""));
        eav.put(":val2", new AttributeValue().withS("Book"));
        return Optional.empty();
    }
}
