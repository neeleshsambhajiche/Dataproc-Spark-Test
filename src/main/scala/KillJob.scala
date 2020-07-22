import SubmitSparkJob.{clusterName, jobControllerClient, jobId, projectId, region}
import com.google.cloud.dataproc.v1.{Job, JobControllerClient, JobControllerSettings, JobPlacement, SparkJob}

/**
 * @since 22/07/20
 */
object KillJob extends App {
  val projectId = "dev-dp-platform-148b"
  val bucketName = "dataplatform"
  val region = "us-east4"
  val clusterName = "cluster-test"

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

  Thread.sleep(5000)

  val request2 = jobControllerClient.cancelJob(projectId, region, jobId)
  val jobStatus = request2.getStatus.getState
  System.out.println(String.format("Job Status \"%s\"", jobStatus))
}
