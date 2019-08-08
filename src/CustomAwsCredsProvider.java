import java.net.URI;
import java.util.logging.Logger;
import java.util.logging.Level;
import org.apache.hadoop.conf.Configurable;
import org.apache.hadoop.conf.Configuration;

import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;

final class CustomAwsCredsProvider extends EnvironmentVariableCredentialsProvider implements Configurable{

    private static final Logger LOGGER = Logger.getLogger( CustomAwsCredsProvider.class.getName() );
    private Configuration configuration;
    private URI uri;

    public CustomAwsCredsProvider(URI uri, Configuration conf) {
        this.configuration = conf;
        this.uri = uri;
        LOGGER.log(Level.FINE, "URI = {0}", uri);
    }

    @Override
    public void setConf(Configuration conf) {
        this.configuration = conf;
    }

    @Override
    public Configuration getConf() {
        return configuration;
    }
}
