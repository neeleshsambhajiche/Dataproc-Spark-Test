import SubmitSparkJob.{job, jobControllerClient, projectId, region}
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
 * @since 22/07/20
 */
object GetJobStatus extends App {
  val projectId = "dev-dp-platform-148b"
  val bucketName = "dataplatform"
  val region = "us-east4"
  val clusterName = "cluster-test"
  val jobFilePath = "gs://dataproc-examples/pyspark/hello-world/hello-world.py"
  val jobId = "c34de200-b6b4-4b43-9ab3-b87080fe3439"

  val myEndpoint = String.format("%s-dataproc.googleapis.com:443", region)

  // Configure the settings for the job controller client.
  val jobControllerSettings: JobControllerSettings =
    JobControllerSettings.newBuilder().setEndpoint(myEndpoint).build();
  val jobControllerClient: JobControllerClient =
    JobControllerClient.create(jobControllerSettings)

  val request = jobControllerClient.getJob(projectId, region, jobId)
  val jobStatus = request.getStatus.getState
  System.out.println(String.format("Job Status \"%s\"", jobStatus))
}
