package carteckh.carteckh.model;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class FindNewCar {

    @SerializedName("RoadSideAssistance")
    @Expose
    private RoadSideAssistance roadSideAssistance;

    /**
     *
     * @return
     * The roadSideAssistance
     */
    public RoadSideAssistance getRoadSideAssistance() {
        return roadSideAssistance;
    }

    /**
     *
     * @param roadSideAssistance
     * The RoadSideAssistance
     */
    public void setRoadSideAssistance(RoadSideAssistance roadSideAssistance) {
        this.roadSideAssistance = roadSideAssistance;
    }

}