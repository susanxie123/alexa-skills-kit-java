package SongForYou.RequestedUrlStore;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

public class RequestedUrlDao {

    private AmazonDynamoDBClient dynamoDBClient;
    private DynamoDBMapper mapper;

    public RequestedUrlDao() {
        this.dynamoDBClient = new AmazonDynamoDBClient();
        this.mapper = new DynamoDBMapper(dynamoDBClient);
    }

    public String getRequestedUrl(String requestedFor) {
        RequestedUrlDataItem keyItem = new RequestedUrlDataItem();
        keyItem.setRequestedFor(requestedFor);
        RequestedUrlDataItem item = mapper.load(keyItem);
        //TODO: verify what will happen if multiple items responded
        return item.getUrl();
    }

    public String getRequestedUrl(String requestedFor, String requestedBy) {
        RequestedUrlDataItem keyItem = new RequestedUrlDataItem();
        keyItem.setRequestedFor(requestedFor);
        keyItem.setRequestedBy(requestedBy);
        RequestedUrlDataItem item = mapper.load(keyItem);
        return item.getUrl();
    }
}
