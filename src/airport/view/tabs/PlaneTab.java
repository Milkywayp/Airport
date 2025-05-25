package airport.view.tabs;

import airport.controller.PlaneController;
import airport.core.Response;
import airport.core.StatusCode;
import airport.model.Plane;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PlaneTab extends JPanel {

    private PlaneController controller;

    private JTable planeTable;
    private DefaultTableModel planeTableModel;

    public PlaneTab(PlaneController controller) {
        this.controller = controller;
        initComponents();
        loadPlanes();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Definimos columnas acorde a Plane
        String[] columns = {
            "ID", "Marca", "Modelo", "Capacidad Máx.", "Aerolínea", "Cantidad de Vuelos"
        };

        planeTableModel = new DefaultTableModel(columns, 0) {
            // Hacemos la tabla no editable
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        planeTable = new JTable(planeTableModel);
        planeTable.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(planeTable);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void loadPlanes() {
        planeTableModel.setRowCount(0);

        Response<List<Plane>> response = controller.getAllPlanes();

        if (response.getStatusCode() == StatusCode.SUCCESS) {
            List<Plane> planes = response.getData();

            for (Plane p : planes) {
                planeTableModel.addRow(new Object[]{
                    p.getId(),
                    p.getBrand(),
                    p.getModel(),
                    p.getMaxCapacity(),
                    p.getAirline(),
                    p.getNumFlights()
                });
            }

            JOptionPane.showMessageDialog(this,
                "Lista de aviones cargada correctamente.",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE);

        } else {
            JOptionPane.showMessageDialog(this,
                "Error al cargar aviones: " + response.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

}


