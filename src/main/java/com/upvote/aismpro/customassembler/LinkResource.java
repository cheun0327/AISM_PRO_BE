package com.upvote.aismpro.customassembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import java.util.function.Function;

// EntityModel 생성시 Link 주입 클래스
public class LinkResource<T> extends EntityModel<T> {

    public static <T> EntityModel<T> of(WebMvcLinkBuilder builder, T model, Function<T, ?> resourceFunc) {
        return EntityModel.of(model, getSelfLink(builder, model, resourceFunc));
    }

    private static<T> Link getSelfLinkWithSlash(WebMvcLinkBuilder builder, T data, Function<T, ?> resourceFunc) {
        WebMvcLinkBuilder slash = builder.slash(resourceFunc.apply(data));
        return slash.withSelfRel();
    }

    private static<T> Link getSelfLink(WebMvcLinkBuilder builder, T data, Function<T, ?> resourceFunc) {
        WebMvcLinkBuilder slash = builder.slash(resourceFunc.apply(data));
        return slash.withSelfRel();
    }
}
