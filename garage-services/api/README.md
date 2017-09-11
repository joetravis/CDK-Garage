

## Serverless Components

1. Lambda function (events-ingest-lambda)
2. API Gateway (`Api` in the `sam.yml` file)

## Learn

### Fix the Lambda (again)!

Once the test passes, deploy your application stack:

```bash
$ mvn install
$ mvn assembly:single
$ aws s3 cp target/garage-services-api-1.0-SNAPSHOT.zip s3://cdk-garage-test-pipeline-sources3location-1x7gtgb4yyr4r/source.zip
```

Verify that the Code Pipeline deploy process has started: https://console.aws.amazon.com/codepipeline/home



### Validate the data!

What happens if we send some data that's missing the `locationId` field?

We can do this using the `--invalid` flag to the `event-generator`:

```bash
$ java -jar target/event-generator-1.0-SNAPSHOT.jar --invalid --limit 1 --stack oscon-2017-tutorial-application
API url: https://o30vnzv5ci.execute-api.us-west-2.amazonaws.com/prod/events
request: {"timestamp":1494108607587,"locationName":"Montgomery, AL","latitude":32.361538,"longitude":-86.279118,"city":"Montgomery","state":"AL","temperature":85.5631845254945}
response: 500 / {:message "Internal Server Error"}
```

Find the Cloudwatch logs output for this Lambda invocation, and you'll see a `NullPointerException`. Clearly, we need
`locationId` to be set for our Lambda to run successfully.

We could put that validation in the Lambda code, but an interesting new feature of API Gateway is the ability to perform
validation within the API Gateway itself, using JSON Schema. This way, if the API Gateway detects invalid data, the Lambda
function isn't even invoked - why pay for trying to process bad data?

Look at the commented-out sections of the `sam.yml` file to see how to add validation to the API Gateway. Uncomment those line and deploy your stack.

Once it's deployed, try sending some invalid data - you should notice a different response.

## Explore

1. API Gateway console (https://console.aws.amazon.com/apigateway/home)
1. API Gateway request validation (http://docs.aws.amazon.com/apigateway/latest/developerguide/api-gateway-method-request-validation.html)
