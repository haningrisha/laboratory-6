package project.university.console;

public class ParameterToMuchException extends Exception{
    public ParameterToMuchException(String string){
        super(string);
        System.err.println(string);
    }
}
