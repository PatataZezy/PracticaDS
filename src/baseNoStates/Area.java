package baseNoStates;

public abstract class Area {
    protected final String id;

    public Area(String id, Partition father) {
        this.id = id;

        if (father != null) {
            father.addArea(this);
        }
    }

    public String getId() {
        return this.id;
    }

    public abstract Door[] getDoorsGivingAccess();

    public abstract Area findAreaById(String id);

    public abstract Space[] getSpaces();
}