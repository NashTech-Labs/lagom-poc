
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RcclLoggerFactory implements RcclLoggerService {

    public RcclLoggerFactory(String className) {
        logger = LogManager.getLogger(className);
    }

    public void info(String message) {
        logger.info(message);

    }


}