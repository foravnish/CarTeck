package carteckh.carteckh.model;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class RoadSideAssistance {

    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("brand_data")
    @Expose
    private List<BrandDatum> brandData = new ArrayList<BrandDatum>();

    /**
     *
     * @return
     * The success
     */
    public Integer getSuccess() {
        return success;
    }

    /**
     *
     * @param success
     * The success
     */
    public void setSuccess(Integer success) {
        this.success = success;
    }

    /**
     *
     * @return
     * The brandData
     */
    public List<BrandDatum> getBrandData() {
        return brandData;
    }

    /**
     *
     * @param brandData
     * The brand_data
     */
    public void setBrandData(List<BrandDatum> brandData) {
        this.brandData = brandData;
    }

}