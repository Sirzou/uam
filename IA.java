package ia;
import puzzle.Puzzle;
import puzzleException.PuzzleException;

public class IA {
    public static void main(String[] args) throws PuzzleException {
        Puzzle p;
        p = new Puzzle(2,1,3,2,0,0,3,1,2);
        System.out.println(p);
        System.out.println("Camino corto");
        p.deterministicoCW();
        p = new Puzzle(2,1,3,2,0,0,3,1,2);
        System.out.println(p);
        System.out.println("Camino largo");
        p.deterministicoCCW();
    }
}