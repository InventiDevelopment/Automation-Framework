package cz.inventi.qa.framework.core.allure;

import io.qameta.allure.attachment.DefaultAttachmentProcessor;
import io.qameta.allure.attachment.FreemarkerAttachmentRenderer;
import io.restassured.filter.FilterContext;
import io.restassured.filter.OrderedFilter;
import io.restassured.internal.NameAndValue;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Custom filter based on the original io.qameta.allure.restassured.AllureRestAssured
 * to add more information into the attachment.
 * @see io.qameta.allure.restassured.AllureRestAssured
 */
public class AllureRestAssured implements OrderedFilter {
    private final String requestTemplatePath;
    private final String responseTemplatePath;

    public AllureRestAssured(String requestTemplatePath, String responseTemplatePath) {
        this.requestTemplatePath = requestTemplatePath;
        this.responseTemplatePath = responseTemplatePath;
    }

    public AllureRestAssured() {
        this("http-request.ftl", "http-response.ftl");
    }

    @Override
    public Response filter(
            final FilterableRequestSpecification requestSpec,
            final FilterableResponseSpecification responseSpec,
            final FilterContext filterContext
    ) {
        new DefaultAttachmentProcessor().addAttachment(
                new HttpRequestAttachment(requestSpec),
                new FreemarkerAttachmentRenderer(requestTemplatePath)
        );
        final Response response = filterContext.next(requestSpec, responseSpec);
        new DefaultAttachmentProcessor().addAttachment(
                new HttpResponseAttachment(response),
                new FreemarkerAttachmentRenderer(responseTemplatePath)
        );
        return response;
    }

    /**
     * Convert Iterable type object to Map<String, String>.
     * @param items Iterable
     * @return Map
     */
    public static Map<String, String> toMapConverter(final Iterable<? extends NameAndValue> items) {
        return StreamSupport
                .stream(items.spliterator(), false)
                .collect(Collectors.toMap(NameAndValue::getName, NameAndValue::getValue));
    }

    /**
     * Convert Iterable type object to List<String> for cases where multiple same
     * keys are presented and therefore basic Map is not viable.
     * @param items Iterable
     * @return List
     */
    public static List<String> toListConverter(final Iterable<? extends NameAndValue> items) {
        List<String> namesWithValues = new ArrayList<>();
        items.forEach(iterable -> namesWithValues.add(iterable.getName() + ": " + iterable.getValue()));
        return namesWithValues;
    }

    @Override
    public int getOrder() {
        return Integer.MAX_VALUE;
    }
}