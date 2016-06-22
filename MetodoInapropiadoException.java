package PuzzleException;

public class MetodoInapropiadoException extends puzzleException.PuzzleException{
    public String showMessage(){
        return "El método que se quiere utilizar no puede ser implementado"
                + " en este tipo de Puzzle";
    }
}
