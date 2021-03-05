package tw.momocraft.toolplus.utils;

import java.util.List;

public class VoidToolMap {

    private String groupName;
    private List<String> locList;
    private List<String> types;
    private List<String> ignoreTypes;


    public String getGroupName() {
        return groupName;
    }

    public List<String> getLocList() {
        return locList;
    }

    public List<String> getTypes() {
        return types;
    }

    public List<String> getIgnoreTypes() {
        return ignoreTypes;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setLocList(List<String> locList) {
        this.locList = locList;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public void setIgnoreTypes(List<String> ignoreTypes) {
        this.ignoreTypes = ignoreTypes;
    }
}
