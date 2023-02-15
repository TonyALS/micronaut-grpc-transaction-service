package br.com.content4devs.controller.interceptors;

import br.com.content4devs.exceptions.AccessDeniedException;
import br.com.content4devs.exceptions.BaseBusinessException;
import br.com.content4devs.model.Role;
import br.com.content4devs.service.auth.token.ITokenService;
import br.com.content4devs.v1.transaction.TransactionServiceGrpc;
import io.grpc.*;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

@Singleton
public class GrpcServerInterceptor implements ServerInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(GrpcServerInterceptor.class);
    private static final Set<String> adminRoutes = new HashSet<>();
    private final ITokenService tokenService;

    public GrpcServerInterceptor(ITokenService tokenService) {
        this.tokenService = tokenService;
        adminRoutes.add(TransactionServiceGrpc.getServiceDescriptor().getName());
    }

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
            ServerCall<ReqT, RespT> call,
            Metadata headers,
            ServerCallHandler<ReqT, RespT> next
    ) {
        ServerCall.Listener<ReqT> listener = next.startCall(call, headers);
        try {
            if (adminRoutes.contains(call.getMethodDescriptor().getServiceName())) {
                Metadata.Key<String> key = Metadata.Key.of("token", Metadata.ASCII_STRING_MARSHALLER);
                String token = headers.get(key);
                Jws<Claims> claims = tokenService.parseJwt(token);
                if (!claims.getBody().get("role").equals(Role.ADMIN.name())) {
                    throw new AccessDeniedException("Insufficient permission");
                }
            }
        } catch (BaseBusinessException bex) {
            LOGGER.error(bex.getMessage(), bex);
            call.close(bex.errorCode().toStatus().withDescription(bex.getMessage()), headers);
        }
        return new CallListener<>(listener, call, headers);
    }
}
