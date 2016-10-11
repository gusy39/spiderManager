package com.ecmoho.common.web.json;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;

import java.io.Closeable;
import java.io.IOException;

public class JSONMapper extends ObjectMapper {
	
	/**
     * Helper method used when value to serialize is {@link Closeable} and its <code>close()</code>
     * method is to be called right after serialization has been called
     */
    private final void _writeCloseableValue(JsonGenerator jgen, Object value, SerializationConfig cfg,boolean isSuffix)
        throws IOException, JsonGenerationException, JsonMappingException
    {
        Closeable toClose = (Closeable) value;
        try {
            _serializerProvider(cfg).serializeValue(jgen, value);
            if (cfg.isEnabled(SerializationFeature.FLUSH_AFTER_WRITE_VALUE)) {
            	if(isSuffix){
            		jgen.writeRaw(")");
            	}
                jgen.flush();
            }
            Closeable tmpToClose = toClose;
            toClose = null;
            tmpToClose.close();
        } finally {
            if (toClose != null) {
                toClose.close();
            }
        }
    }

	/**
     * Overridable helper method used for constructing
     * {@link } to use for serialization.
     */
    protected DefaultSerializerProvider _serializerProvider(SerializationConfig config) {
        return _serializerProvider.createInstance(config, _serializerFactory);
    }
	/**
     * Method that can be used to serialize any Java value as
     * JSON output, using provided {@link com.fasterxml.jackson.core.JsonGenerator}.
     */
    public void writeValue(JsonGenerator jgen, Object value,boolean isSuffix)
        throws IOException, JsonGenerationException, JsonMappingException
    {
        SerializationConfig config = getSerializationConfig();
        // 10-Aug-2012, tatu: as per [Issue#12], must handle indentation:
        if (config.isEnabled(SerializationFeature.INDENT_OUTPUT)) {
            jgen.useDefaultPrettyPrinter();
        }
        if (config.isEnabled(SerializationFeature.CLOSE_CLOSEABLE) && (value instanceof Closeable)) {
            _writeCloseableValue(jgen, value, config,isSuffix);
        } else {
            _serializerProvider(config).serializeValue(jgen, value);
            if (config.isEnabled(SerializationFeature.FLUSH_AFTER_WRITE_VALUE)) {
            	if(isSuffix){
            		jgen.writeRaw(")");
            	}
            	
                jgen.flush();
            }
        }
    }

}