package game;

public class Chronometre {
    private long begin;
    private long end;
    private long current;
    private final int limite;

    public Chronometre(int limite) {
        //intialisation
        this.limite = limite;
    }
    
    public void start(){
        begin = System.currentTimeMillis();
    }
 
    public void stop(){
        end = (long) limite;
    }
 
    public int getTime() {
        current = System.currentTimeMillis();
        int timeSpent;
        timeSpent = (int) ((current - begin)/1000.0);
        return timeSpent ;
    }
 
    public long getMilliseconds() {
        return end-begin;
    }
 
    public int getSeconds() {
        return (int) (getTime() / 1000.0);
    }
 
    public double getMinutes() {
        return (getTime() / 60000.0);
    }
 
    public double getHours() {
        return (getTime() / 3600000.0);
    }
    
    /**
    * Method to know if it remains time.
    */
    public boolean remainsTime() {
        current = System.currentTimeMillis();
        int timeSpent;
        timeSpent = (int) ((current - begin)/1000.0);
        return (timeSpent != limite);
    }
    
}
