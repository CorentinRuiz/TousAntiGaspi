package edu.poly.tousantigaspi.object;

import android.os.Parcel;
import android.os.Parcelable;

import java.time.LocalDate;

public class CodeScannerProduct extends Product implements Parcelable {

    public CodeScannerProduct(String id, String name, String dateRemaining, Integer quantity) {
        super(id,name, dateRemaining,quantity);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(super.getId());
        dest.writeString(super.getName());
        dest.writeString(super.getDateRemaining());
        dest.writeValue(super.getQuantity());
    }

    public void readFromParcel(Parcel source) {
        super.setId(source.readString());
        super.setName(source.readString());
        super.setDateRemaining(source.readString());
        super.setQuantity((Integer) source.readValue(Integer.class.getClassLoader()));
    }

    protected CodeScannerProduct(Parcel in) {
        super(in.readString(),in.readString(),in.readString(),(Integer) in.readValue(Integer.class.getClassLoader()));
    }

    public static final Parcelable.Creator<CodeScannerProduct> CREATOR = new Parcelable.Creator<CodeScannerProduct>() {
        @Override
        public CodeScannerProduct createFromParcel(Parcel source) {
            return new CodeScannerProduct(source);
        }

        @Override
        public CodeScannerProduct[] newArray(int size) {
            return new CodeScannerProduct[size];
        }
    };
}
