package game.Visualiser;

public interface ConsoleVisualiser extends Visualiser {
    default void write(String text) {
        System.out.print(text);
    }

    default void writeErrorInfo(String text) {
        System.err.print(text);
    }
}