package science.inc.com.news;

import java.util.LinkedHashMap;
import java.util.LinkedList;

/**
 * Created by prateek.g2 on 27-Aug-15.
 */
public class NewsData {

    private int CategoryId;
    private String CategoryName;
    private String ImageUrl;
    private LinkedHashMap<String,String> Subcategories;
    private LinkedList<String> subChildDescription=new LinkedList<String>();

    public LinkedList<String> getSubChildDescription()
    {
        return subChildDescription;
    }

    public void setSubChildDescription(LinkedList<String> subChildDescription) {
        this.subChildDescription = subChildDescription;
    }

    public int getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(int categoryId) {
        CategoryId = categoryId;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public String getImageUrl()
    {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl)
    {
        ImageUrl = imageUrl;
    }

    public LinkedHashMap<String, String> getSubcategories() {
        return Subcategories;
    }

    public void setSubcategories(LinkedHashMap<String, String> subcategories) {
        Subcategories = subcategories;
    }






}
