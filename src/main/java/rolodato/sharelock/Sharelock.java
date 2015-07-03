package rolodato.sharelock;

import com.google.gson.JsonObject;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class Sharelock {
    public Sharelock() {
        this.instanceUrl = Sharelock.publicUrl;
    }

    public Sharelock(String url) {
        this.instanceUrl = url;
    }

    public static final String publicUrl = "https://sharelock.io/";

    public final String instanceUrl;

    private final HttpClient httpClient = HttpClients.createDefault();

    public String create(String secret, String... recipients) throws IOException {
        return this.create(secret, Arrays.asList(recipients));
    }

    public String create(String secret, List<String> recipients) throws IOException {
        HttpPost post = createPost(Sharelock.createBody(secret, recipients));
        HttpResponse postResponse = httpClient.execute(post);
        return parseResponse(postResponse);
    }

    public static JsonObject createBody(String secret, List<String> recipients) {
        JsonObject json = new JsonObject();
        json.addProperty("d", secret);
        json.addProperty("a", String.join(",", recipients));
        return json;
    }

    private HttpPost createPost(JsonObject json) {
        HttpPost post = new HttpPost(instanceUrl + "/create");
        String body = json.toString();
        StringEntity entity = new StringEntity(body, "UTF8");
        post.setEntity(entity);
        post.setHeader("Content-type", "application/json");
        return post;
    }

    private String parseResponse(HttpResponse response) throws IOException {
        InputStream resp = response.getEntity().getContent();
        String respText = IOUtils.toString(resp, "UTF8");
        int code = response.getStatusLine().getStatusCode();
        if (code == 200) {
            return this.instanceUrl + respText;
        } else {
            throw new IOException(respText);
        }
    }

}
