# customcredentialsprovider
Custom credentials provider  for EMR on AWS, using IAM Environment Variable Credentials

### BUILD
On Master node of the EMR cluster:
```
javac -cp $(hadoop classpath) CustomAwsCredsProvider.java
jar -cvf customAwsCredsProvider.jar customAwsCredsProvider.class
```


### Configure Cluster to Use the CustomCredentialsProvider class

1. copy the jar in emrfs libs:
```
sudo cp customAwsCredsProvider.jar /usr/share/aws/emr/emrfs/auxlib/
```
2. set in /usr/share/aws/emr/emrfs/conf/emrfs-site.xml:
```
<configuration>

  <property>
    <name>fs.s3.customAWSCredentialsProvider</name>
    <value>CustomAwsCredsProvider</value>
  </property>

</configuration>
```

### Usage With HDFS:
```
export AWS_ACCESS_KEY_ID="xxxxxxxxx"
export AWS_SECRET_ACCESS_KEY="xxxxxxxxxxx"
export AWS_SESSION_TOKEN="xxxxxxx"

hdfs --loglevel DEBUG dfs  -ls s3://s3bucket/path/to/data/ 2> debuglog.txt
```
The logs will show the credentials being used:
```
...
19/02/20 14:31:19 DEBUG guice.CredentialsProviderOverrider: Using default credentials provider for com.amazon.ws.emr.hadoop.fs.s3.lite.call.DoesBucketExistCall@75504cef
19/02/20 14:31:19 DEBUG metrics.AwsSdkMetrics: Admin mbean registered under com.amazon.ws.emr.hadoop.fs.shaded.com.amazonaws.management:type=AwsSdkMetrics
19/02/20 14:31:19 DEBUG auth.AWSCredentialsProviderChain: Loading credentials from customAwsCredsProvider
19/02/20 14:31:19 DEBUG amazonaws.request: Sending Request: HEAD ...
...
```

### Usage With Spark
Launch spark-shell with Temporary creds in environment variables
```
spark-shell \
--conf 'spark.executorEnv.AWS_ACCESS_KEY_ID="xxxxxxxxxxx"' \
--conf 'spark.executorEnv.AWS_SECRET_ACCESS_KEY="xxxxxxxxx"' \
--conf 'spark.executorEnv.AWS_SESSION_TOKEN="xxxxxxxxx"'
```

Thanks!