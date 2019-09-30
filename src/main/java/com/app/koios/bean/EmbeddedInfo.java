package com.app.koios.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.ALWAYS;
import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_ABSENT;
import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.fasterxml.jackson.databind.SerializationFeature.FAIL_ON_EMPTY_BEANS;

/**
 * @author unalpolat
 */
public interface EmbeddedInfo {

	Logger LOGGER = LoggerFactory.getLogger(EmbeddedInfo.class);

	ObjectMapper MAPPER = new ObjectMapper().configure(FAIL_ON_UNKNOWN_PROPERTIES, false)
																					.configure(FAIL_ON_EMPTY_BEANS, false)
																					.setDefaultPropertyInclusion(JsonInclude.Value.construct(ALWAYS, NON_NULL))
																					.setSerializationInclusion(NON_ABSENT)
																					.registerModules(new ParameterNamesModule(), new AfterburnerModule());

	String EMPTY_JSON = "{}";

	static String serialize(EmbeddedInfo embeddedInfo) {
		try {
			if (Objects.isNull(embeddedInfo)) {
				return null;
			}
			String json = MAPPER.writeValueAsString(embeddedInfo);
			if (EMPTY_JSON.equals(json)) {
				json = null;
			}
			return json;
		} catch (Exception ex) {
			LOGGER.error("Can not serialize embedded info", ex);
			return null;
		}
	}

	static <T extends EmbeddedInfo> T deserialize(Class<T> clazz, String json) {
		if (json == null) {
			return null;
		}
		try {
			return MAPPER.readValue(json, clazz);
		} catch (Exception ex) {
			LOGGER.error("Can not deserialize embedded info json", ex);
			return null;
		}
	}

	default String serialize() {
		return EmbeddedInfo.serialize(this);
	}
}
