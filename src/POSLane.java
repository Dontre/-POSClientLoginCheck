
public class POSLane {
	String laneId = "";
	boolean status = false;
	
	public POSLane(String laneId) {
		this.laneId = laneId;
	}
	
	public String getLaneId() {
		return laneId;
	}
	
	public boolean getStatus() {
		return status;
	}
	
	public void setLaneId(String laneId) {
		this.laneId = laneId;
	}
	
	public void setStatus(boolean status) {
		this.status = status;
	}

	public String toString() {
		return "Lane Id: " + this.laneId + " Lane Status: " + this.status + "\n";
	}
}
