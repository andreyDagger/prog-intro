package game_original.Visualiser;

public abstract class ConsoleVisualiser implements Visualiser {
    public void write(String text) {
        System.out.print(text);
    }

    public void writeErrorInfo(String text) {
        System.err.print(text);
    }
}