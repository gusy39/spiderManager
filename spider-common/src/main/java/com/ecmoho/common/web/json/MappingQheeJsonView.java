package com.ecmoho.common.web.json;

import com.ecmoho.common.dto.AjaxResult;
import com.ecmoho.common.web.BaseController;
import com.ecmoho.common.web.esayui.GridList;
import com.ecmoho.common.web.esayui.GridPage;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * org.springframework.web.servlet.view.json.MappingJacksonJsonView
 * @author tad
 * @since 3.0
 */
public class MappingQheeJsonView extends AbstractView {
	/**
	 * Default content type. Overridable as bean property.
	 */
	public static final String DEFAULT_CONTENT_TYPE = "application/json";

	private JSONMapper objectMapper = new JSONMapper();

	private JsonEncoding encoding = JsonEncoding.UTF8;

	private boolean prefixJson = false;

	private Set<String> renderedAttributes;

	private boolean disableCaching = true;

	private boolean nullNotWrite = false;

	/**
	 * Construct a new {@code JacksonJsonView}, setting the content type to {@code application/json}.
	 */
	public MappingQheeJsonView() {
		setContentType(DEFAULT_CONTENT_TYPE);
	}

	/**
	 * Sets the {@code ObjectMapper} for this view. If not set, a default {@link com.fasterxml.jackson.databind.ObjectMapper#ObjectMapper() ObjectMapper}
	 * is used.
	 *
	 * <p>Setting a custom-configured {@code ObjectMapper} is one way to take further control of the JSON serialization
	 * process. For example, an extended {@link com.fasterxml.jackson.databind.ser.SerializerFactory} can be configured that provides custom serializers for
	 * specific types. The other option for refining the serialization process is to use Jackson's provided annotations on
	 * the types to be serialized, in which case a custom-configured ObjectMapper is unnecessary.
	 */
	public void setObjectMapper(JSONMapper objectMapper) {
		Assert.notNull(objectMapper, "'objectMapper' must not be null");
		this.objectMapper = objectMapper;
	}

	/**
	 * Sets the {@code JsonEncoding} for this converter. By default, {@linkplain com.fasterxml.jackson.core.JsonEncoding#UTF8 UTF-8} is used.
	 */
	public void setEncoding(JsonEncoding encoding) {
		Assert.notNull(encoding, "'encoding' must not be null");
		this.encoding = encoding;
	}

	/**
	 * Indicates whether the JSON output by this view should be prefixed with "{@code {} &&}". Default is false.
	 *
	 * <p> Prefixing the JSON string in this manner is used to help prevent JSON Hijacking. The prefix renders the string
	 * syntactically invalid as a script so that it cannot be hijacked. This prefix does not affect the evaluation of JSON,
	 * but if JSON validation is performed on the string, the prefix would need to be ignored.
	 */
	public void setPrefixJson(boolean prefixJson) {
		this.prefixJson = prefixJson;
	}

	/**
	 * Returns the attributes in the model that should be rendered by this view.
	 */
	public Set<String> getRenderedAttributes() {
		return renderedAttributes;
	}

	/**
	 * Sets the attributes in the model that should be rendered by this view. When set, all other model attributes will be
	 * ignored.
	 */
	public void setRenderedAttributes(Set<String> renderedAttributes) {
		this.renderedAttributes = renderedAttributes;
	}

	/**
	 * Disables caching of the generated JSON.
	 *
	 * <p>Default is {@code true}, which will prevent the client from caching the generated JSON.
	 */
	public void setDisableCaching(boolean disableCaching) {
		this.disableCaching = disableCaching;
		if (objectMapper != null) {
			SimpleModule simpleModule = new SimpleModule("myBigDecimalFormat");
			simpleModule.addSerializer(BigDecimal.class, new DecimalFormatSerialize());
			//进行注册
			objectMapper.registerModule(simpleModule);

			objectMapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
				@Override
				public void serialize(Object value, JsonGenerator jg, SerializerProvider sp) throws IOException, JsonProcessingException {
					jg.writeString("");
				}
			});
		}
	}

	public void setNullNotWrite(boolean nullNotWrite) {
		this.nullNotWrite = nullNotWrite;
		if (objectMapper != null && nullNotWrite) {
			objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		}
	}

	@Override
	protected void prepareResponse(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType(getContentType());
		response.setCharacterEncoding(encoding.getJavaName());
		if (disableCaching) {
			response.addHeader("Pragma", "no-cache");
			response.addHeader("Cache-Control", "no-cache, no-store, max-age=0");
			response.addDateHeader("Expires", 1L);
		}
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String callback =request.getParameter("callback");

		Object result = null;
		if (model == null) {
			result = BaseController.getAjaxError("json序列化错误!");
		}
		for (Map.Entry<String, Object> entry : model.entrySet()) {
			if (entry.getValue() instanceof AjaxResult) {
				AjaxResult temp = (AjaxResult)entry.getValue();
				result = temp;
				if (temp.getMessages() != null && temp.getMessages() instanceof CharSequence) {
					temp.setMessages(temp.getMessages().toString());
				}
				//easyUI 分页数据结构封装
				if(temp.getContent() !=null && temp.getContent() instanceof GridPage) {
					result = temp.getContent();
					break;
				}
				if(temp.getContent() !=null && temp.getContent() instanceof GridList) {
					result = ((GridList) temp.getContent()).getObj();
					break;
				}
				//多条错误消息
				if (temp.getMessages() !=null && temp.getMessages() instanceof List) {
//					List<String> errMsgList = new ArrayList<>();
					StringBuffer errors = new StringBuffer();
					List<FieldError> ls = (List<FieldError>) temp.getMessages();
					for (int i = 0; i < ls.size(); i++) {
//						errMsgList.add(ls.get(i).getDefaultMessage());
						errors.append(ls.get(i).getDefaultMessage()).append(",");
					}
//					temp.setMessages(errMsgList);
					temp.setMessages(errors.toString());
					break;
				}
				//表单验证错误消息封装
				/*Map<String,Object> errors = new HashMap<String,Object>();
				if (temp.getMessages() !=null && temp.getMessages() instanceof BindingResult) {
					Map<String,Object> formError = new HashMap<String,Object>();
					List<FieldError> ls=((BindingResult)temp.getMessages()).getFieldErrors();
					for (int i = 0; i < ls.size(); i++) {
						formError.put(ls.get(i).getField(),ls.get(i).getDefaultMessage());
					}
					errors.put(ls.get(0).getObjectName(), formError);
					temp.setMessages(errors);
					break;
				}
				if (temp.getMessages() !=null && temp.getMessages() instanceof BindingResult[]) {
					BindingResult[] bindingResults = (BindingResult[])temp.getMessages();
					for (int j = 0; j < bindingResults.length; j++) {
						Map<String,Object> formError = new HashMap<String,Object>();
						List<FieldError> ls=bindingResults[j].getFieldErrors();
						for (int i = 0; i < ls.size(); i++) {
							formError.put(ls.get(i).getField(),ls.get(i).getDefaultMessage());
						}
						errors.put(ls.get(0).getObjectName(), formError);
					}
					temp.setMessages(errors);
				}*/


				if (temp.getMessages() !=null && temp.getMessages() instanceof BindingResult) {
					StringBuffer errors = new StringBuffer();
					List<FieldError> ls=((BindingResult)temp.getMessages()).getFieldErrors();
					for (int i = 0; i < ls.size(); i++) {
						errors.append(ls.get(i).getDefaultMessage()).append(",");
					}
					temp.setMessages(errors.toString());
					break;
				}
				if (temp.getMessages() !=null && temp.getMessages() instanceof BindingResult[]) {
					StringBuffer errors = new StringBuffer();
					BindingResult[] bindingResults = (BindingResult[])temp.getMessages();
					for (int j = 0; j < bindingResults.length; j++) {
						List<FieldError> ls=bindingResults[j].getFieldErrors();
						for (int i = 0; i < ls.size(); i++) {
							errors.append(ls.get(i).getDefaultMessage()).append(",");
						}

					}
					temp.setMessages(errors.toString());
				}
				break;
			}

		}
		if (model == null) {
			result = BaseController.getAjaxError("json序列化错误!");
		}
		if (result == null) {
			try {
				result = model;
			} catch (Exception e) {
				result = BaseController.getAjaxError("json序列化错误!");
			}
		}
		ServletOutputStream os = response.getOutputStream();
		response.setHeader("P3P","CP='NOI'");
		JsonGenerator generator =objectMapper.getJsonFactory().createJsonGenerator(os, encoding);
		if (prefixJson) {
			generator.writeRaw("{} && ");
		}
		boolean isSuffixForObject = false;
		if(callback !=null && !"".equals(callback.trim())){
			generator.writeRaw(callback+"(");
			isSuffixForObject= true;

		}
		objectMapper.writeValue(generator, result,isSuffixForObject);
	}

	/**
	 * Filters out undesired attributes from the given model. The return value can be either another {@link Map}, or a
	 * single value object.
	 *
	 * <p>Default implementation removes {@link org.springframework.validation.BindingResult} instances and entries not included in the {@link
	 * #setRenderedAttributes(Set) renderedAttributes} property.
	 *
	 * @param model the model, as passed on to {@link #renderMergedOutputModel}
	 * @return the object to be rendered
	 */
	protected Object filterModel(Map<String, Object> model) {
		Map<String, Object> result = new HashMap<String, Object>(model.size());
		Set<String> renderedAttributes =
				!CollectionUtils.isEmpty(this.renderedAttributes) ? this.renderedAttributes : model.keySet();
		for (Map.Entry<String, Object> entry : model.entrySet()) {
			if (!(entry.getValue() instanceof BindingResult) && renderedAttributes.contains(entry.getKey())) {
				result.put(entry.getKey(), entry.getValue());
			}
		}
		return result;
	}
	
	protected Map<String,Object> filterBindingResultModel(Map<String, Object> model) {
		Map<String, Object> result = new HashMap<String, Object>(model.size());
		Set<String> renderedAttributes =!CollectionUtils.isEmpty(this.renderedAttributes) ? this.renderedAttributes : model.keySet();
		for (Map.Entry<String, Object> entry : model.entrySet()) {
			if ((entry.getValue() instanceof BindingResult) && renderedAttributes.contains(entry.getKey())) {
				result.put(entry.getKey(), entry.getValue());
			}
		}
		return result;
	}

}