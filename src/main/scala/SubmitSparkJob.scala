import GetJobStatus.{jobControllerClient, jobId, projectId, region}
import com.google.api.gax.longrunning.OperationFuture
import com.google.cloud.dataproc.v1.{Cluster, ClusterConfig, ClusterControllerClient, ClusterControllerSettings, ClusterOperationMetadata, InstanceGroupConfig, Job, JobControllerClient, JobControllerSettings, JobPlacement, PySparkJob, SparkJob}
//import com.google.cloud.storage.Blob
//import com.google.cloud.storage.Storage
//import com.google.cloud.storage.StorageOptions
import com.google.protobuf.Empty
import java.io.IOException
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutionException
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

import com.google.auth.oauth2.ComputeEngineCredentials


/**
 * @since 09/07/20
 */
object SubmitSparkJob extends App {
  val projectId = "dev-dp-platform-148b"
  val bucketName = "dataplatform"
  val region = "us-east4"
  val clusterName = "cluster-test"
  val jobFilePath = "gs://dataproc-examples/pyspark/hello-world/hello-world.py"

  val myEndpoint = String.format("%s-dataproc.googleapis.com:443", region)

  // Configure the settings for the job controller client.
  val jobControllerSettings: JobControllerSettings =
    JobControllerSettings.newBuilder().setEndpoint(myEndpoint).build();
  val jobControllerClient: JobControllerClient =
    JobControllerClient.create(jobControllerSettings)

  // Configure the settings for our job.
  val jobPlacement = JobPlacement.newBuilder.setClusterName(clusterName).build
  val sparkJob = SparkJob.newBuilder.setMainClass("org.apache.spark.examples.SparkPi").addJarFileUris("gs://dataplatform/scala/spark-examples_2.12-2.4.5.jar").build
  val job = Job.newBuilder.setPlacement(jobPlacement).setSparkJob(sparkJob).build

  // Submit an asynchronous request to execute the job.
  val request = jobControllerClient.submitJob(projectId, region, job)
  val jobId = request.getReference.getJobId
  System.out.println(String.format("Submitted job \"%s\"", jobId))




}
