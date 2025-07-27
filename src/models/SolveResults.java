package models;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList; // Asegúrate de que esta importación esté presente

public class SolveResults implements Serializable {
    private static final long serialVersionUID = 1L; // Recomendado para Serializable

    private List<Cell> path;
    private long timeTakenMillis;
    private boolean solved;
    private Exception exception; // ¡NUEVO CAMPO! Para capturar errores

    // Constructor para resultados exitosos
    public SolveResults(List<Cell> path, long timeTakenMillis, boolean solved) {
        this.path = path;
        this.timeTakenMillis = timeTakenMillis;
        this.solved = solved;
        this.exception = null; // No hay excepción en un resultado exitoso
    }

    // Constructor para resultados con error (por ejemplo, si doInBackground falla)
    public SolveResults(Exception exception, long timeTakenMillis) {
        this.path = new ArrayList<>(); // Vacío o null, dependiendo de cómo quieras manejarlo
        this.timeTakenMillis = timeTakenMillis; // El tiempo hasta que falló
        this.solved = false; // No resuelto debido a la excepción
        this.exception = exception; // Guarda la excepción
    }

    // Getters
    public List<Cell> getPath() {
        return path;
    }

    public long getTimeTakenMillis() {
        return timeTakenMillis;
    }

    public boolean isSolved() {
        return solved;
    }

    public Exception getException() { // ¡NUEVO MÉTODO!
        return exception;
    }

    @Override
    public String toString() {
        return "SolveResults{" +
               "pathLength=" + (path != null ? path.size() : 0) +
               ", timeTakenMillis=" + timeTakenMillis +
               ", solved=" + solved +
               ", exception=" + (exception != null ? exception.getMessage() : "none") +
               '}';
    }
}