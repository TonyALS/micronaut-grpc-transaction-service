package br.com.content4devs.controller.interceptors;

import br.com.content4devs.exceptions.BaseBusinessException;
import io.grpc.ForwardingServerCallListener;
import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.Status;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class CallListener<ReqT, RespT> extends ForwardingServerCallListener.SimpleForwardingServerCallListener<ReqT> {

    private final ServerCall<ReqT, RespT> serverCall;
    private final Metadata metadata;

    private static final Logger LOGGER = LoggerFactory.getLogger(CallListener.class);

    private static final String INTERNAL_SERVER_ERROR = "Internal Server Error";

    protected CallListener(ServerCall.Listener<ReqT> delegate, ServerCall<ReqT, RespT> serverCall, Metadata metadata) {
        super(delegate);
        this.serverCall = serverCall;
        this.metadata = metadata;
    }

    @Override
    public void onHalfClose() {
        try {
            super.onHalfClose();
        } catch (BaseBusinessException bex) {
            LOGGER.error(bex.getMessage(), bex);
            serverCall.close(bex.errorCode().toStatus().withDescription(bex.getMessage()), metadata);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            if (serverCall.isReady()) {
                serverCall.close(Status.INTERNAL.withDescription(INTERNAL_SERVER_ERROR), metadata);
            }
        }
    }

    @Override
    public void onReady() {
        try {
            super.onReady();
        } catch (BaseBusinessException bex) {
            LOGGER.error(bex.getMessage(), bex);
            serverCall.close(bex.errorCode().toStatus().withDescription(bex.getMessage()), metadata);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            if (serverCall.isReady()) {
                serverCall.close(Status.INTERNAL.withDescription(INTERNAL_SERVER_ERROR), metadata);
            }
        }
    }

    @Override
    public void onMessage(ReqT message) {
        try {
            super.onMessage(message);
        } catch (BaseBusinessException bex) {
            LOGGER.error(bex.getMessage(), bex);
            serverCall.close(bex.errorCode().toStatus().withDescription(bex.getMessage()), metadata);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            if (serverCall.isReady()) {
                serverCall.close(Status.INTERNAL.withDescription(INTERNAL_SERVER_ERROR), metadata);
            }
        }
    }
}
