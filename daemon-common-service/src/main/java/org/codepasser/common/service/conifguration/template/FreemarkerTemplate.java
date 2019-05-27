package org.codepasser.common.service.conifguration.template;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Locale;

public class FreemarkerTemplate {

  private Configuration configuration;
  private String name;
  private SourceResolver sourceResolver;
  private Template template;
  private Object source;

  public FreemarkerTemplate(
      Configuration configuration, String name, SourceResolver sourceResolver) {
    this.configuration = configuration;
    this.name = name;
    this.sourceResolver = sourceResolver;
  }

  public Object getSource() {
    return source;
  }

  public String process(Object model) throws IOException, TemplateException {
    StringWriter result = new StringWriter();
    template.process(model, result);
    return result.toString();
  }

  public void accept(Locale language) throws IOException {
    this.source = sourceResolver.resolve(language);
    this.template = configuration.getTemplate(name, language);
  }

  public interface SourceResolver {
    Object resolve(Locale locale) throws IOException;
  }
}
