package SongForYou.RequestedUrlStore;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import java.util.Map;

public class RequestedUrlDao {

    private AmazonDynamoDBClient dynamoDBClient;
    private DynamoDBMapper mapper;

    public RequestedUrlDao() {
        this.dynamoDBClient = new AmazonDynamoDBClient();
        this.mapper = new DynamoDBMapper(dynamoDBClient);
    }

    public String getRequestedUrl(String requestedFor) {
        Map<String, String> url = getUrlMap(requestedFor);
        if (url.isEmpty()) {
            return "";
        }
        final String[] requesterList = (String[]) url.keySet().toArray();
        final String firstRequester = requesterList[0];
        final String result = url.get(firstRequester);
        url.remove(firstRequester);
        return result;
    }

    public String getRequestedUrl(String requestedFor, String requestedBy) {
        Map<String, String> url = getUrlMap(requestedFor);
        if (!url.containsKey(requestedBy)) {
            return "";
        }
        final String result = url.get(requestedBy);
        url.remove(requestedBy);
        return result;
    }

    private Map<String, String> getUrlMap(String requestedFor) {
        RequestedUrlDataItem keyItem = new RequestedUrlDataItem();
        keyItem.setRequestedFor(requestedFor);
        RequestedUrlDataItem item = mapper.load(keyItem);
        return item.getUrl();
    }
}
