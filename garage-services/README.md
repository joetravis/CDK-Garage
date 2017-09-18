# CDK Garage Lamda Session

## AWS Inline Lambda Function

    'use strict'
    console.log("Loading random-number-generator")
    exports.handler = (event, context, callback) => {
    // TODO implement
    let min=0;
    let max=10;
    
    let randomNumber = Math.floor(Math.random() * max) + min
    callback(null, randomNumber);
    };
    

## Initial Setup


1. Make sure your AWS user has full permissions within your AWS account.

    The most flexible scenario is one in which your user is part of the `Admin` group. 
    
    You can check from the command line:
    ```bash
    $ aws configure
    $ aws iam get-group --group-name Admin --query 'Users[].UserName'
    ```
    The output will be the list of users in the `Admin` group:
    ```json
      [
          "sudipta"
      ]
    ```
    
2. Download the tutorial project:

    ```bash
    $ git clone https://github.com/sudiptamodak1987/CDK-Garage.git
     
    ```
    
3. Create the build pipeline using Cloudformation. The build pipeline will create the application, also 
with Cloudformation. Note that the build pipeline stack name is specified using the `--stack-name` command line flag, while
the application stack name is specified as a parameter to the stack template.
   
    ```bash
    $ cd garage-services
    $ aws cloudformation create-stack \
            --capabilities CAPABILITY_IAM \
            --stack-name cdk-garage-test-pipeline \
            --parameters ParameterKey=ApplicationStackName,ParameterValue=cdk-garage-test-application \
            --template-body file://build-pipeline.yml
    ```
   The output should look something like this:
    ```json
    {
        "StackId": "arn:aws:cloudformation:us-west-2:1234567890:stack/cdk-garage-test-pipeline/7972b720-2f5b-11e7-bd3d-503acbd4dcfd"
    }
    ```
    
4. Get the S3 url where you'll upload code during the tutorial. Use your build pipeline stack name if it's different from this:
    ```bash
    $ aws cloudformation describe-stacks \
            --query 'Stacks[?StackName==`cdk-garage-test-pipeline`].Outputs[0][?OutputKey==`SourceS3Bucket`].OutputValue' \
            --output text
    ```
    The output should look something like this:
    ```
    s3://cdk-garage-test-pipeline-sources3location-1x7gtgb4yyr4r
    
    ```
    
## Cleanup

:warning: These steps must be completed in order, or resources might be stranded.

1. Delete the application stack:
    ```bash
    $ aws cloudformation delete-stack --stack-name cdk-garage-test-pipeline
    ```

2. Find the physical resource IDs for the build pipeline's S3 buckets
    ```bash
    $ aws cloudformation list-stack-resources --stack-name cdk-garage-test-pipeline \
          --query 'StackResourceSummaries[?ResourceType==`AWS::S3::Bucket`].PhysicalResourceId' \
          --output text
    ```

3. Delete those buckets using the the S3 web console (https://console.aws.amazon.com/s3/home)

4. Delete the build pipeline stack:
    ```bash
    $ aws cloudformation delete-stack --stack-name cdk-garage-test-pipeline
    ```
    

