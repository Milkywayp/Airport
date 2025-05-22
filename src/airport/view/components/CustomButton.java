/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.view.components;
import javax.swing.*;
import java.awt.*;
/**
 *
 * @author bbelt
 */
public class CustomButton extends JButton{
    public CustomButton(String text) {
        super(text);
        setFont(new Font("Arial", Font.BOLD, 14));
        setBackground(Color.CYAN);
    }
}
