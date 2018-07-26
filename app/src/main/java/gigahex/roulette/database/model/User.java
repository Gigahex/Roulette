package gigahex.roulette.database.model;

public class User {
    private long id;
    private String name;
    private int points;

    public User(long id, String name, int points){
        this.id = id;
        this.name = name;
        this.points = points;
    }
    public long getId() {
        return id;
    }
    public void setId(long id){this.id = id;}
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return this.name + ": " + this.points;
    }
}
