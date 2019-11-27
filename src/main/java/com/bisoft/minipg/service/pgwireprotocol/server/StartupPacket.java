package com.bisoft.minipg.service.pgwireprotocol.server;

import java.util.Arrays;

import com.bisoft.minipg.service.util.ByteUtil;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class StartupPacket extends AbstractWireProtocolPacket {
	public static final Logger logger = LoggerFactory.getLogger(StartupPacket.class);
	String remoteHost;
	int remotePort;
	Object msg;
	String connectionString;

	public String getConnectionString() {
		return connectionString;
	}

	public void setConnectionString(String connectionString) {
		this.connectionString = connectionString;
	}

	public Object getMsg() {
		return msg;
	}

	public void setMsg(Object message) {
		this.msg = message;
	}

	public String getRemoteHost() {
		return remoteHost;
	}

	public void setRemoteHost(String remoteHost) {
		this.remoteHost = remoteHost;
	}

	public int getRemotePort() {
		return remotePort;
	}

	public void setRemotePort(int remotePort) {
		this.remotePort = remotePort;
	}

	@Override
	public String toString() {
		return remoteHost + ":" + remotePort;
	}

	@Override
	public WireProtocolPacket decode(byte[] buffer) {

		log.trace(
				"this is a startup pack... that includes: ...b....user.postgres.database.northwind.client_encoding.UTF8.DateStyle.ISO.extra_float_digits."
						+ "=======================");
		int packetLengt = ByteUtil.fromByteArray(buffer);

		String params = new String(Arrays.copyOfRange(buffer, 12, packetLengt - 2));
		log.trace("length:" + params);
		setConnectionString(params);
		return this;
	}

	@Override
	public byte[] response() {
		return PgConstants.R_AUTHENTICATION_MD5_PASSWORD;
	}

	public static boolean packetMatches(byte[] buffer) {
		return buffer.length > 8 && buffer[5] == 3 && buffer[7] == 0; // protocol 3.0
	}
}