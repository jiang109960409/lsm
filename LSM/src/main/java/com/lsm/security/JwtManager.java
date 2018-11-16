package com.lsm.security;

import java.io.IOException;
import java.util.Base64;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtManager {

	@Autowired
	private ObjectMapper		om;
	@Value("${security.jwt.secret-key}")
	private String				jwtSecret;
	private SecretKey			secretKey;

	@PostConstruct
	public void init() {
		byte[] encodedKey = Base64.getEncoder().encode(jwtSecret.getBytes());
		secretKey = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
	}

	public String createToken(long id, String name) {
		TokenPayload payload = new TokenPayload(id, name);
		String subject;
		try {
			subject = om.writeValueAsString(payload);
		} catch (JsonProcessingException e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}
		//签名算法
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		//获取密钥
		//系统当前时间
		Date nowDate = new Date();
		JwtBuilder builder = Jwts.builder().setIssuedAt(nowDate).//设置创建时间
				setSubject(subject)//设置主题"个人信息"
				.signWith(signatureAlgorithm, secretKey);//第三段密钥
		//添加Token过期时间，12小时
		Date expDate = new Date(nowDate.getTime() + 1000L * 60 * 60 * 12);
		//设置过期时间
		builder.setExpiration(expDate);
		//生成Token
		return builder.compact();
	}

	/**解析token字符串*/
	public Claims parseToken(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
	}

	public TokenPayload deserialize(String payload) {
		try {
			return om.readValue(payload, TokenPayload.class);
		} catch (IOException e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}
	}

	public String getJwtSecret() {
		return jwtSecret;
	}

	public void setJwtSecret(String jwtSecret) {
		this.jwtSecret = jwtSecret;
	}
}
