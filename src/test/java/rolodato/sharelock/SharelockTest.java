package rolodato.sharelock;

import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

public class SharelockTest
{
    @Test
    public void singleRecipient() throws IOException {
        Sharelock sharelock = new Sharelock();
        sharelock.create("hi", "alice@example.com");
    }

    @Test
    public void multipleRecipients() throws IOException {
        Sharelock sharelock = new Sharelock("https://sharelock.io/");
        sharelock.create("hi", Arrays.asList(
                "alice@example.com",
                "bob@example.com"
        ));
    }
}
