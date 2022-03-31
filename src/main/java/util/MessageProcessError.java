package util;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MessageProcessError extends RuntimeException {

    final static Logger LOG = LogManager.getLogger(MessageProcessError.class);

    public MessageProcessError() {
        super();
        LOG.error("no information recorded, please use other contructors.");
    }

    public MessageProcessError(String msg) {
        super(msg);
        LOG.error(msg);
    }

    public MessageProcessError(String msg, Throwable th) {
        super(msg, th);
        LOG.error(msg, th);
    }
}

