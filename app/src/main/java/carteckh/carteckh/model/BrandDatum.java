package carteckh.carteckh.model;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class BrandDatum {

    @SerializedName("getBrandId ")
    @Expose
    private String getBrandId;
    @SerializedName("getBrandName")
    @Expose
    private String getBrandName;
    @SerializedName("roadSideNumber")
    @Expose
    private String roadSideNumber;
    @SerializedName("icon")
    @Expose
    private String icon;

    /**
     *
     * @return
     * The getBrandId
     */
    public String getGetBrandId() {
        return getBrandId;
    }

    /**
     *
     * @param getBrandId
     * The getBrandId
     */
    public void setGetBrandId(String getBrandId) {
        this.getBrandId = getBrandId;
    }

    /**
     *
     * @return
     * The getBrandName
     */
    public String getGetBrandName() {
        return getBrandName;
    }

    /**
     *
     * @param getBrandName
     * The getBrandName
     */
    public void setGetBrandName(String getBrandName) {
        this.getBrandName = getBrandName;
    }

    /**
     *
     * @return
     * The roadSideNumber
     */
    public String getRoadSideNumber() {
        return roadSideNumber;
    }

    /**
     *
     * @param roadSideNumber
     * The roadSideNumber
     */
    public void setRoadSideNumber(String roadSideNumber) {
        this.roadSideNumber = roadSideNumber;
    }

    /**
     *
     * @return
     * The icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     *
     * @param icon
     * The icon
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

}