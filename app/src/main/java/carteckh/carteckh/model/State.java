package carteckh.carteckh.model;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class State {

    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("state_list")
    @Expose
    private List<StateList> stateList = new ArrayList<StateList>();

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
     * The stateList
     */
    public List<StateList> getStateList() {
        return stateList;
    }

    /**
     *
     * @param stateList
     * The state_list
     */
    public void setStateList(List<StateList> stateList) {
        this.stateList = stateList;
    }

}