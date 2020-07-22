import com.google.api.gax.longrunning.OperationFuture;
import com.google.cloud.dataproc.v1.*;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.protobuf.Empty;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import com.google.common.collect.ImmutableList;


/**
 * @since 21/07/20
 */
public class SubmitSparkJobJava {

public static void quickstart(String projectId, String region, String clusterName, String jobFilePath) throws IOException, InterruptedException {
    String myEndpoint = String.format("%s-dataproc.googleapis.com:443", region);

    // Configure the settings for the job controller client.
    JobControllerSettings jobControllerSettings =
            JobControllerSettings.newBuilder().setEndpoint(myEndpoint).build();
    JobControllerClient jobControllerClient =
            JobControllerClient.create(jobControllerSettings);

    // Configure the settings for our job.
    JobPlacement jobPlacement = JobPlacement.newBuilder().setClusterName(clusterName).build();
    SparkJob sparkJob = SparkJob.newBuilder().setMainClass("org.apache.spark.examples.SparkPi").addJarFileUris("gs://dataplatform/scala/spark-examples_2.12-2.4.5.jar").build();
    Job job = Job.newBuilder().setPlacement(jobPlacement).setSparkJob(sparkJob).build();

    // Submit an asynchronous request to execute the job.
    Job request = jobControllerClient.submitJob(projectId, region, job);
    String jobId = request.getReference().getJobId();
    System.out.println(String.format("Submitted job \"%s\"", jobId));
}

public static void main(String... args) throws IOException, InterruptedException {
        if (args.length != 4) {
            System.err.println(
                    "Insufficient number of parameters provided. Please make sure a "
                            + "PROJECT_ID, REGION, CLUSTER_NAME and JOB_FILE_PATH are provided, in this order.");
            return;
        }

        String projectId = args[0]; // project-id of project to create the cluster in
        String region = args[1]; // region to create the cluster
        String clusterName = args[2]; // name of the cluster
        String jobFilePath = args[3]; // location in GCS of the PySpark job

        quickstart(projectId, region, clusterName, jobFilePath);
    }
}
