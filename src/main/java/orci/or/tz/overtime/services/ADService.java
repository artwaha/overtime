

package orci.or.tz.overtime.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.*;
import orci.or.tz.overtime.dto.user.ADRespDto;
import orci.or.tz.overtime.dto.user.ADResponseDto;
import orci.or.tz.overtime.dto.user.LoginRequest;
import orci.or.tz.overtime.exceptions.OperationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;

@Service
public class ADService {



    private final String postEndpoint = "http://192.168.1.6:8080/auth/realms/master/protocol/openid-connect/token";

    public ADRespDto getADUser(LoginRequest loginRequest) throws IOException, OperationFailedException {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "grant_type=password&client_id=orci-uaa&username=" + loginRequest.getUsername() + "&password=" + loginRequest.getPassword());

        Request httpRequest = new Request.Builder()
                .url(postEndpoint)
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();

        Response response = null; // Initialize the response outside the try block

        try {
            response = client.newCall(httpRequest).execute();

            if (!response.isSuccessful()) {
                throw new IOException("Unexpected response code: " + response.code());
            }
            System.out.println("Ma code =>" + response.code() );

            if(response.code() == 200) {


                ObjectMapper objectMapper = new ObjectMapper();
                ADResponseDto adResponseDto = objectMapper.readValue(response.body().string(), ADResponseDto.class);
                String payloadChunk = adResponseDto.getAccess_token().split("\\.")[1];

                Base64.Decoder decoder = Base64.getUrlDecoder();
                String payload = new String(decoder.decode(payloadChunk));

                JsonNode payloadJson = objectMapper.readTree(payload);
                String name = payloadJson.get("name").asText();


                ADRespDto dto = new ADRespDto();
                dto.setCode(200);
                dto.setUserName(loginRequest.getUsername());
                return dto;
            }
            else{
                ADRespDto dto = new ADRespDto();
                dto.setCode(401);
                dto.setUserName(loginRequest.getUsername());
                return dto;
            }
        } finally {
            if (response != null) {
                response.body();
            }
        }
    }

}
