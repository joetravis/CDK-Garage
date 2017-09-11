package com.garage;

import com.amazonaws.services.lambda.runtime.Context;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.garage.domain.GarageEvent;
import com.garage.events.ApiGatewayProxyRequest;
import com.garage.events.ApiGatewayProxyResponse;

public class GarageEventsLambda {

    private final ObjectMapper mapper = new ObjectMapper();

    public ApiGatewayProxyResponse handler(ApiGatewayProxyRequest request, Context context) throws Exception {
        ApiGatewayProxyResponse response = new ApiGatewayProxyResponse();
        GarageEvent responseObject = new GarageEvent("Lambda", "Baja", "2017-09-18");
        String json = mapper.writeValueAsString(responseObject);
        response.setBody(json);
        response.setStatusCode(200);
        return response;
    }
}
