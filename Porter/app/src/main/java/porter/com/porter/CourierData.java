package porter.com.porter;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by prateek on 8/30/2015.
 */
public class CourierData implements Parcelable{

    String name;
    String imageUrl;
    String date;
    String type;
    String weight;
    String phone;
    String price;
    String quantity;
    String color;
    String link;

    String latitude;
    String longitude;

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    String printString(){
        return new String("Name: "+this.name + " " +
                "Color :"+this.color + " " +
                "Price :"+this.price+" "+
                "ImageUrl :"+this.imageUrl+" "+
                "Type :"+this.type+" "+
                "date :"+this.date
                +"Phone :"+this.phone);
    }

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(name);
        parcel.writeString(imageUrl);
        parcel.writeString(date);
        parcel.writeString(type);
        parcel.writeString(weight);
        parcel.writeString(phone);
        parcel.writeString(price);
        parcel.writeString(quantity);
        parcel.writeString(color);
        parcel.writeString(link);
        parcel.writeString(latitude);
        parcel.writeString(longitude);
    }

    public static final Parcelable.Creator<CourierData> CREATOR = new Creator<CourierData>() {
        public CourierData createFromParcel(Parcel source) {
            CourierData mCourierData = new CourierData();
            mCourierData.name=source.readString();
            mCourierData.imageUrl=source.readString();
            mCourierData.date=source.readString();
            mCourierData.type=source.readString();
            mCourierData.weight=source.readString();
            mCourierData.phone=source.readString();
            mCourierData.price=source.readString();
            mCourierData.quantity=source.readString();
            mCourierData.color=source.readString();
            mCourierData.link=source.readString();
            mCourierData.latitude=source.readString();
            mCourierData.longitude =source.readString();
            return mCourierData;
        }
        public CourierData[] newArray(int size) {
            return new CourierData[size];
        }
    };
}
