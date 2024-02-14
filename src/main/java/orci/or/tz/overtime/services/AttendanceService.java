package orci.or.tz.overtime.services;

import orci.or.tz.overtime.dto.attendance.AttendanceDto;
import orci.or.tz.overtime.utilities.GenericResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;

@Service
public class AttendanceService {

    @Value("${orci.attendance.url}")
    private String attendanceEndpoint;


    public Integer GetAttendanceByUser(LocalDate dt, Long userId) {

        Integer result = 0;

        String fullUrl = attendanceEndpoint+"endDate=" + dt + "&page=0&size=10&startDate=" + dt + "&userId=" + userId;
        try {

            URL url = new URL(fullUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setDoOutput(true);


            int responseCode = con.getResponseCode();
            System.out.println("GET Response Code :: " + responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK) { // success
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // print result
                System.out.println(response);

                JSONObject json2 = new JSONObject(response.toString());

                JSONArray AttendanceArray = json2.getJSONArray("data");
                Integer count = AttendanceArray.length();
                System.out.println("Records Found :: " + count);
                result = count;

            } else {
                System.out.println("GET request did not work.");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());

        }


        return result;

    }


}
