/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.view.utils;

/**
 *
 * @author bbelt
 */
public class Validator {
    public static boolean isValidAirportId(String id) {
        return id != null && id.matches("[A-Z]{3}");
    }

    public static boolean isValidCoordinates(double lat, double lon) {
        return lat >= -90 && lat <= 90 && lon >= -180 && lon <= 180;
    }
}
