package com.amazon.speech.speechlet.services.householdlist;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.Date;
import java.util.List;
import java.util.Locale;

@JsonTypeName("AlexaHouseholdListEvent.ItemsDeleted")
public class ListItemsDeletedRequest extends AlexaHouseholdListEventRequest {
    private final ListItemBody body;

    public static Builder builder() {
        return new Builder();
    }

    private ListItemsDeletedRequest(final Builder builder) {
        super(builder);
        this.body = builder.body;
    }

    private ListItemsDeletedRequest(@JsonProperty("requestId") final String requestId,
                                    @JsonProperty("timestamp") final Date timestamp,
                                    @JsonProperty("locale") final Locale locale,
                                    @JsonProperty("body") final ListItemBody body) {
        super(requestId, timestamp, locale);
        this.body = body;
    }

    public String getListId() {
        return body.getListId();
    }

    public List<String> getListItemIds() {
        return body.getListItemIds();
    }

    public static final class Builder extends SpeechletRequestBuilder<Builder, ListItemsDeletedRequest> {
        private ListItemBody body;

        private Builder() {

        }

        public Builder withListItemBody(final ListItemBody body) {
            this.body = body;
            return this;
        }

        @Override
        public ListItemsDeletedRequest build() {
            return new ListItemsDeletedRequest(this);
        }
    }
}
