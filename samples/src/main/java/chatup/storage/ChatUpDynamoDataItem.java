package chatup.storage;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Builder;
import lombok.Setter;

@Builder
@Setter
@DynamoDBTable(tableName = "ChatUpUserTable")
public class ChatUpDynamoDataItem {
    private String userId;
    private String userName;

    @DynamoDBHashKey(attributeName = "userId")
    public String getUserId() {return userId;}

    @DynamoDBAttribute(attributeName = "userName")
    public String getUserName() {return userName;}
}
