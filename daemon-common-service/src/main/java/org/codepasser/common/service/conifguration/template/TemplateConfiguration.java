package org.codepasser.common.service.conifguration.template;

import java.io.IOException;

public interface TemplateConfiguration {
  FreemarkerTemplate findTemplate(String value) throws IOException;
}
