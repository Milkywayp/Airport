/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.util;

import java.io.InputStream;
import org.json.JSONArray;
import org.json.JSONTokener;

/**
 *
 * @author HP
 */
public class JsonLoader {
    public static JSONArray loadJsonArray(String fileName) {
        try (InputStream is = JsonLoader.class.getClassLoader().getResourceAsStream("json/" + fileName)) {
            if (is == null) {
                throw new RuntimeException("File not found: " + fileName);
            }
            JSONTokener tokener = new JSONTokener(is);
            return new JSONArray(tokener);
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONArray(); // empty on error
        }
    }
}
    

