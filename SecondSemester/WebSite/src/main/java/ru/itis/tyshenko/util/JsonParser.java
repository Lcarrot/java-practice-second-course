package ru.itis.tyshenko.util;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;

@Component
public class JsonParser {

    public Optional<String> getPropertyFromInputStream(InputStream inputStream, String propertyName) {
        String response;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            response = reader.readLine();
            JSONObject object = (JSONObject) new JSONParser().parse(response);
            return Optional.ofNullable(object.get(propertyName).toString());
        } catch (IOException | ParseException | ClassCastException e) {
            return Optional.empty();
        }
    }
}
