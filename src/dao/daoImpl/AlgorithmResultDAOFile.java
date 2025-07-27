package dao.daoImpl;

import dao.AlgorithmResultDAO;
import models.AlgorithmResult;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AlgorithmResultDAOFile implements AlgorithmResultDAO {
    private static final String FILE_NAME = "results.dat";

    @Override
    public void saveResult(AlgorithmResult result) {
        List<AlgorithmResult> results = getAllResults(); // Cargar resultados existentes
        results.add(result); // Añadir el nuevo
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(results);
        } catch (IOException e) {
            System.err.println("Error saving results: " + e.getMessage());
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<AlgorithmResult> getAllResults() {
        File file = new File(FILE_NAME);
        if (!file.exists() || file.length() == 0) { // Si el archivo no existe o está vacío
            return new ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (List<AlgorithmResult>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading results: " + e.getMessage());
            return new ArrayList<>(); // Devolver lista vacía en caso de error
        }
    }

    @Override
    public void clearAllResults() {
        try {
            new File(FILE_NAME).delete(); // Simplemente borra el archivo
            System.out.println("All results cleared.");
        } catch (SecurityException e) {
            System.err.println("Error deleting results file: " + e.getMessage());
        }
    }
}