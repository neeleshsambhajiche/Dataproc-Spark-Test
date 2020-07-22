import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.iam.v1.Iam;
import com.google.api.services.iam.v1.IamScopes;
import com.google.api.services.iam.v1.model.ListRolesResponse;
import com.google.api.services.iam.v1.model.Role;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.ComputeEngineCredentials;
import com.google.auth.oauth2.GoogleCredentials;
import java.util.Collections;
import java.util.List;
import com.google.api.services.iam.v1.model.ListServiceAccountsResponse;
import com.google.api.services.iam.v1.model.ServiceAccount;

public class CheckServiceAccount {

    public static void main(String[] args) throws Exception {
        String projectId = "dev-dp-platform-148b";
        // Get credentials
//        GoogleCredentials credential =
//                GoogleCredentials.getApplicationDefault()
//                        .createScoped(Collections.singleton(IamScopes.CLOUD_PLATFORM));

        GoogleCredentials credential = ComputeEngineCredentials.create();
        System.out.println(ComputeEngineCredentials.getServiceAccountsUrl());

        // Create the Cloud IAM service object
//        Iam service =
//                new Iam.Builder(
//                        GoogleNetHttpTransport.newTrustedTransport(),
//                        JacksonFactory.getDefaultInstance(),
//                        new HttpCredentialsAdapter(credential))
//                        .setApplicationName("quickstart")
//                        .build();

        // Call the Cloud IAM Roles API
//        ListRolesResponse response = service.roles().list().execute();
//        List<Role> roles = response.getRoles();

        /*ListServiceAccountsResponse response =
                service.projects().serviceAccounts().list("projects/" + projectId).execute();
        List<ServiceAccount> serviceAccounts = response.getAccounts();

        for (ServiceAccount account : serviceAccounts) {
            System.out.println("Name: " + account.getName());
            System.out.println("Display Name: " + account.getDisplayName());
            System.out.println("Email: " + account.getEmail());
            System.out.println();
        }*/
    }
}
