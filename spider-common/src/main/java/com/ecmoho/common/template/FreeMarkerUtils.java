package com.ecmoho.common.template;


import com.ecmoho.common.exception.BeanNullException;
import com.ecmoho.context.SpringContextHolder;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FreeMarkerUtils {

    private static final Logger logger = LoggerFactory.getLogger(FreeMarkerUtils.class);

    private static FreeMarkerConfigurer getFreeMarkerConfigurer() {
        FreeMarkerConfigurer freeMarkerConfigurer = (FreeMarkerConfigurer) SpringContextHolder.getBean("freemarkerConfig");
        if (freeMarkerConfigurer == null) {
            throw new BeanNullException("模板引擎配置未注入!");
        }
        return freeMarkerConfigurer;
    }


    private static Configuration getConfiguration() {
        return getFreeMarkerConfigurer().getConfiguration();
    }

    public static String getContent(String templateName, Map<String, Object> model) {
        try {
            Template t = getConfiguration().getTemplate(templateName);
            return FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
        } catch (Exception ex) {
            logger.info(ExceptionUtils.getStackTrace(ex));
        }
        return "";
    }

    public static String template2String(String templateContent,
                                         Map<String, Object> map, boolean isNeedFilter) {
        if (StringUtils.isBlank(templateContent)) {
            return null;
        }
        if (map == null) {
            map = new HashMap<String, Object>();
        }
        Map<String, Object> newMap = new HashMap<String, Object>();

        Set<String> keySet = map.keySet();
        if (keySet != null && keySet.size() > 0) {
            for (String key : keySet) {
                Object o = map.get(key);
                if (o != null) {
                    if (o instanceof String) {
                        String value = o.toString();
                        if (value != null)
                            value = value.trim();
                        if (isNeedFilter) {
                            value = StringEscapeUtils.escapeXml(value);
                        }
                        newMap.put(key, value);
                    } else {
                        newMap.put(key, o);
                    }
                }
            }
        }
        Template t = null;
        try {
            t = new Template("", new StringReader(templateContent), getConfiguration());
            StringWriter writer = new StringWriter();
            t.process(newMap, writer);
            return writer.toString();
        } catch (IOException e) {
            logger.error("TemplateUtil -> template2String IOException.");
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (TemplateException e) {
            logger.error("TemplateUtil -> template2String TemplateException.");
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (newMap != null) {
                newMap.clear();
            }
        }

    }
}
