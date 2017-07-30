package cloud.deshario.bloodbank.Models;

/**
 * Created by Deshario on 7/30/2017.
 */

public class Requester {

    public String requester_name;
    public String requester_bloodgroup;
    public String requester_reason;
    public String requester_location;
    public String requester_tel;

    public String getRequester_name() {
        return requester_name;
    }

    public void setRequester_name(String requester_name) {
        this.requester_name = requester_name;
    }

    public String getRequester_bloodgroup() {
        return requester_bloodgroup;
    }

    public void setRequester_bloodgroup(String requester_bloodgroup) {
        this.requester_bloodgroup = requester_bloodgroup;
    }

    public String getRequester_reason() {
        return requester_reason;
    }

    public void setRequester_reason(String requester_reason) {
        this.requester_reason = requester_reason;
    }

    public String getRequester_location() {
        return requester_location;
    }

    public void setRequester_location(String requester_location) {
        this.requester_location = requester_location;
    }

    public String getRequester_tel() {
        return requester_tel;
    }

    public void setRequester_tel(String requester_tel) {
        this.requester_tel = requester_tel;
    }
}