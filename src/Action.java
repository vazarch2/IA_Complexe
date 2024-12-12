public class Action {
	
	private String typeAction;
	
	private Coordinate coordinate;
	
	public Action(String typeAction, Coordinate coordinate) {
		this.typeAction = typeAction;
		this.coordinate = coordinate;
	}

    public String getTypeAction() {
        return typeAction;
    }

    public void setTypeAction(String typeAction) {
        this.typeAction = typeAction;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
}
