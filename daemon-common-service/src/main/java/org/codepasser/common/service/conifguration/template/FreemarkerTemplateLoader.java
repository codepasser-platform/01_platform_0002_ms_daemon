package org.codepasser.common.service.conifguration.template;

import freemarker.cache.TemplateLoader;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Date;
import java.util.Locale;

import static org.codepasser.common.utils.Locales.fromValue;

@Component
public class FreemarkerTemplateLoader implements TemplateLoader {

  @Override
  public Object findTemplateSource(String name) throws IOException {
    String[] nameInfo = name.split("_", 2);
    return getTemplateContent(nameInfo[0], fromValue(nameInfo[1]));
  }

  @Override
  public long getLastModified(Object templateSource) {
    TemplateContent templateContent = (TemplateContent) templateSource;
    Date time = templateContent.getLastModifyTime();
    return time == null ? -1 : time.getTime();
  }

  @Override
  public Reader getReader(Object templateSource, String encoding) throws IOException {
    TemplateContent content = (TemplateContent) templateSource;
    return new StringReader(content.getContent());
  }

  @Override
  public void closeTemplateSource(Object templateSource) throws IOException {}

  private TemplateContent getTemplateContent(String name, Locale local) {
    TemplateContent content = new TemplateContent();
    content.setName(name);
    content.setLanguage(local);
    // TODO template cache
    return content;
  }
}
