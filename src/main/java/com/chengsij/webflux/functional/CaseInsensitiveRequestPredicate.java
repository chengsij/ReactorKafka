package com.chengsij.webflux.functional;

import org.springframework.http.server.PathContainer;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.support.ServerRequestWrapper;

import java.net.URI;

public class CaseInsensitiveRequestPredicate implements RequestPredicate {

    private final RequestPredicate target;

    public CaseInsensitiveRequestPredicate(RequestPredicate target) {
        this.target = target;
    }

    @Override
    public boolean test(ServerRequest request) {
        return this.target.test(new LowerCaseUriServerRequestWrapper(request));
    }

    @Override
    public String toString() {
        return this.target.toString();
    }
}


class LowerCaseUriServerRequestWrapper extends ServerRequestWrapper {

    LowerCaseUriServerRequestWrapper(ServerRequest delegate) {
        super(delegate);
    }

    @Override
    public URI uri() {
        // the meat of the predicate. ServerReuquestWrapper is not necessary
        return URI.create(super.uri().toString().toLowerCase());
    }

    @Override
    public String path() {
        return uri().getRawPath();
    }

    @Override
    public PathContainer pathContainer() {
        return PathContainer.parsePath(path());
    }
}