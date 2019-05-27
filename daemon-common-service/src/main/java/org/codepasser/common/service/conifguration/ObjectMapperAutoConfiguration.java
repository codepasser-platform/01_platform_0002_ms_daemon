package org.codepasser.common.service.conifguration;

import static org.springframework.data.domain.Sort.Direction.valueOf;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

/**
 * ObjectMapperAutoConfiguration.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
@Configuration
@AutoConfigureAfter(JacksonAutoConfiguration.class)
public class ObjectMapperAutoConfiguration {

  private static final String JACKSON_MODULE_PAGEABLE = "PageableModule";

  @Autowired private ObjectMapper objectMapper;

  @PostConstruct
  public void init() {
    objectMapper.registerModule(new Jdk8Module());
    objectMapper.registerModule(getPageableModule());
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }

  private Module getPageableModule() {
    SimpleModule pageableModule =
        new SimpleModule(JACKSON_MODULE_PAGEABLE, new Version(1, 0, 0, null, null, null));
    pageableModule.addDeserializer(Pageable.class, new PageableDeserializer());
    return pageableModule;
  }

  private class PageableDeserializer extends JsonDeserializer<PageRequest> {

    private static final String PAGE_NUMBER = "page_number";
    private static final String PAGE_SIZE = "page_size";
    private static final String PAGE_SORT = "sort";
    private static final String SORT_DIRECTION = "direction";
    private static final String SORT_PROPERTY = "property";

    @Override
    public PageRequest deserialize(JsonParser jp, DeserializationContext ctx) throws IOException {
      ObjectNode node = jp.getCodec().readTree(jp);
      int page = node.get(PAGE_NUMBER).asInt();
      int size = node.get(PAGE_SIZE).asInt();
      JsonNode sortNode = node.get(PAGE_SORT);
      if (sortNode instanceof ArrayNode) {
        Sort sort = parseSort((ArrayNode) node.get(PAGE_SORT));
        return PageRequest.of(page, size, sort);
      }
      return PageRequest.of(page, size);
    }

    private Sort parseSort(ArrayNode node) {

      if (node == null) {
        return null;
      }

      List<Order> orders = Lists.newArrayList();
      node.forEach(jsonNode -> orders.add(parseOrder(jsonNode)));
      // @Deprecated
      // return new Sort(orders);
      return Sort.by(orders);
    }

    private Order parseOrder(JsonNode jsonNode) {
      Direction direction = valueOf(jsonNode.get(SORT_DIRECTION).asText());
      String property = jsonNode.get(SORT_PROPERTY).asText();
      return new Order(direction, property);
    }
  }
}
