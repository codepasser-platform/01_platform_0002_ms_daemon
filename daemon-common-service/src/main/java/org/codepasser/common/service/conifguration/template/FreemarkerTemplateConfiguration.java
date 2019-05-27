package org.codepasser.common.service.conifguration.template;

import static freemarker.cache.NullCacheStorage.INSTANCE;

import freemarker.template.TemplateExceptionHandler;
import java.io.IOException;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FreemarkerTemplateConfiguration implements TemplateConfiguration {

  @Autowired private FreemarkerTemplateLoader freemarkerTemplateLoader;

  private freemarker.template.Configuration configuration;

  @PostConstruct
  private void init() {
    configuration =
        new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_23);
    configuration.setTemplateLoader(freemarkerTemplateLoader);
    configuration.setCacheStorage(INSTANCE);
    configuration.setDefaultEncoding("UTF-8");
    configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
    configuration.setLogTemplateExceptions(false);
    configuration.setClassForTemplateLoading(this.getClass(), "/templates");
  }

  @Override
  public FreemarkerTemplate findTemplate(String value) throws IOException {
    return new FreemarkerTemplate(
        configuration,
        value,
        (locale) ->
            freemarkerTemplateLoader.findTemplateSource(String.format("%s_%s", value, locale)));
  }
}
