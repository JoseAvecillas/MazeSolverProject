package solver;

import models.Cell;

/**
 * Interfaz para permitir a los algoritmos publicar actualizaciones de progreso.
 * Esto evita problemas de visibilidad con los métodos generados de SwingWorker.
 */
public interface ProgressPublisher {
    void publish(Cell cell);
    void publish(Cell... cells);
}