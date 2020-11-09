package kz.edu.astanait.interfaces;

import kz.edu.astanait.models.User;

import java.util.Date;
import java.util.List;

public interface IECN {
    public void setId(int id);
    public int getId();

    public void setName(String name);
    public String getName();

    public void setOwner(User owner);
    public User getOwner();

    public void setModerators(List<User> moderators);
    public List<User> getModerators();

    public void setDescription(String description);
    public String getDescription();

    public void setImg_url(String img_url);
    public String getImg_url();

    public void setCreated(Date created_date);
    public Date getCreated();
}
