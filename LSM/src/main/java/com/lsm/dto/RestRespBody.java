package com.lsm.dto;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.lsm.dto.RestRespBody.RestRespBodySerializer;

@JsonSerialize(using = RestRespBodySerializer.class)
public class RestRespBody {

	private boolean success;
	private String outputMessage;
	private Object data;
	private boolean isHome;

	public RestRespBody(boolean isHome) {
		this.isHome = isHome;
	}

	public RestRespBody(boolean success, String outputMessage) {
		this.success = success;
		this.outputMessage = outputMessage;
	}

	public RestRespBody(boolean success, String outputMessage, Object data) {
		this.success = success;
		this.outputMessage = outputMessage;
		this.data = data;
	}

	public boolean getIsHome() {
		return isHome;
	}

	public void setIsHome(boolean isHome) {
		this.isHome = isHome;
	}

	public boolean isSuccess() {
		return success;
	}

	public String getOutputMessage() {
		return outputMessage;
	}

	public Object getData() {
		return data;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public void setOutputMessage(String outputMessage) {
		this.outputMessage = outputMessage;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public static class RestRespBodySerializer extends StdSerializer<RestRespBody> {

		private static final long serialVersionUID = 4784062956469538573L;

		public RestRespBodySerializer() {
			super(RestRespBody.class);
		}

		@Override
		public void serialize(RestRespBody body, JsonGenerator gen, SerializerProvider provider) throws IOException {
			gen.writeStartObject();
			provider.defaultSerializeField("IsSuccess", body.isSuccess(), gen);
			provider.defaultSerializeField("OutputMessage", body.getOutputMessage(), gen);
			provider.defaultSerializeField("Data", body.getData(), gen);
			provider.defaultSerializeField("IsHome", body.getIsHome(), gen);
			gen.writeEndObject();
		}
	}
}