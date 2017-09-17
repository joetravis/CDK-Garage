

## Serverless Components

1. Lambda function (GarageEventsLambda)
2. API Gateway (`Api` in the `sam.yml` file)

## Learn

### Fix the Lambda (again)!

Once the test passes, deploy your application stack:

```bash
$ cd api
$ mvn install
$ mvn assembly:single
$ aws s3 cp target/garage-services-api-1.0-SNAPSHOT.zip s3://cdk-garage-test-pipeline-sources3location-mkb25zvq2e89/source.zip
```

Verify that the Code Pipeline deploy process has started: https://console.aws.amazon.com/codepipeline/home
