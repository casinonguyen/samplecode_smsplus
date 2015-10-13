package service;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;

@Path("/")
public class SMSPlus {
	
	@GET
	@Path("/SmsplusCharging")
	@Produces(MediaType.APPLICATION_JSON)
	public Response charging(
			@DefaultValue("0")@QueryParam("access_key")String accKey,
			@DefaultValue("0")@QueryParam("amount")String amount,
			@DefaultValue("0")@QueryParam("command_code")String commandCode,
			@DefaultValue("0")@QueryParam("error_code")String errorCode,
			@DefaultValue("0")@QueryParam("error_message")String errorMessage,
			@QueryParam("mo_message")String moMessage,
			@QueryParam("msisdn")String phone,
			@QueryParam("request_id")String requestId,
			@QueryParam("request_time")String requestTime,
			@QueryParam("signature")String signature){
		
		JSONObject json = new JSONObject();
		String secret = ""; // Secret Key do 1pay cung cap. thay bang Secret Key cua ban
		String sig = generateSignature(accKey, amount, commandCode, errorCode, errorMessage, moMessage, phone, requestId, requestTime, secret);
		try {
			if (signature.equalsIgnoreCase(sig)) {
				json.put("status", 1);
				json.put("sms", "Send sms thanh cong");
			}else {
				json.put("status", 0);
				json.put("sms", "tin nhan sai cu phap");
			}
			json.put("text", "text");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.status(200).entity(json.toString()).build();
	}
	
	public static String hmac(String msg, String keyString, String algo) {
		String digest = "";
		try {
			if (keyString != null && keyString.length() > 0) {
				SecretKeySpec keySpec = new SecretKeySpec(
						keyString.getBytes("UTF-8"), algo);
				Mac mac = Mac.getInstance(algo);
				mac.init(keySpec);
				byte[] bytes = mac.doFinal(msg.getBytes("ASCII"));
				StringBuffer hash = new StringBuffer();
				for (int i = 0; i < bytes.length; i++) {
					String hex = Integer.toHexString(0xFF & bytes[i]);
					if (hex.length() == 1) {
						hash.append('0');
					}
					hash.append(hex);
				}
				digest = hash.toString();

			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return digest;
	}

	public String generateSignature(String access_key, String amount,
			String command_code, String error_code, String error_message,
			String mo_message, String phone, String request_id,
			String request_time, String secret) {
		String urlParameters = "";
		String signature = "";
		if (access_key != null && amount != null && command_code != null
				&& error_code != null && error_message != null
				&& mo_message != null && phone != null && request_id != null
				&& request_time != null && secret != null) {
			urlParameters = "access_key=%access_key%&amount=%amount%&command_code=%command_code%"
					+ "&error_code=%error_code%&error_message=%error_message%&mo_message=%mo_message%"
					+ "&msisdn=%msisdn%&request_id=%request_id%&request_time=%request_time%";
			urlParameters = urlParameters.replaceFirst("%access_key%",access_key);
			urlParameters = urlParameters.replaceFirst("%amount%", amount);
			urlParameters = urlParameters.replaceFirst("%command_code%",command_code);
			urlParameters = urlParameters.replaceFirst("%error_code%",error_code);
			urlParameters = urlParameters.replaceFirst("%error_message%",error_message);
			urlParameters = urlParameters.replaceFirst("%mo_message%",mo_message);
			urlParameters = urlParameters.replaceFirst("%msisdn%", phone);
			urlParameters = urlParameters.replaceFirst("%request_id%",request_id);
			urlParameters = urlParameters.replaceFirst("%request_time%",request_time);
			signature = hmac(urlParameters, secret, "HmacSHA256");
			System.out.println("Signature:" + signature);
		}
		return signature;
	}
	
	

}










