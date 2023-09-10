package academy.prog;

import java.util.HashSet;
import java.util.Set;

public class ChatRoom {

    private String name;
    private String adminName;
    private Set<String> members;

    public ChatRoom(String name, String adminName) {
        this.name = name;
        this.adminName = adminName;
        this.members = new HashSet<>();
        members.add(adminName);
    }

    public ChatRoom(String name, String adminName, Set<String> members) {
        this.name = name;
        this.adminName = adminName;
        this.members = members;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public Set<String> getMembers() {
        return members;
    }

    public void setMembers(Set<String> members) {
        this.members = members;
    }

    public void addMember(String member){
        members.add(member);
    }

    public void removeMember(String member){
        members.remove(member);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ChatRoom ");
        sb.append("name= '").append("#").append(name).append('\'');
        sb.append(", Admin= '").append("@").append(adminName).append('\'');
        sb.append(", Members= ").append(members);
        sb.append('}');
        return sb.toString();
    }
}
